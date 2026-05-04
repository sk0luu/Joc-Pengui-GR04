package jocpinguiFinal.Controlador;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import jocpinguiFinal.Model.Agujero;
import jocpinguiFinal.Model.Casilla;
import jocpinguiFinal.Model.Dado;
import jocpinguiFinal.Model.Inventario;
import jocpinguiFinal.Model.Item;
import jocpinguiFinal.Model.ItemConcreto;
import jocpinguiFinal.Model.Jugador;
import jocpinguiFinal.Model.Foca;
import jocpinguiFinal.Model.Oso;
import jocpinguiFinal.Model.Partida;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.PinguinoJugador;
import jocpinguiFinal.Model.SueloQuebradizo;
import jocpinguiFinal.Model.Tablero;
import jocpinguiFinal.Model.Trineo;

public class GestorPartida implements Serializable {
    private static final long serialVersionUID = 1L;
    private Partida partida; // objeto que contiene el estado de la partida
    private GestorTablero gestorTablero; // encargado de la logica de las casillas
    private GestorJugador gestorJugador; // encargado de la logica de movimiento
    private Random random;
    private Connection conexionBD; // conexion activa a la base de datos

    public GestorPartida() {
        this.gestorTablero = new GestorTablero();
        this.gestorJugador = new GestorJugador();
        this.random = new Random();
    }

    // crea una partida nueva con los jugadores y colores indicados
    public void nuevaPartida(ArrayList<String> nombres, ArrayList<String> coloresSeleccionados, boolean conFoca) {
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size() && i < 4; i++) {
            String color = i < coloresSeleccionados.size() ? coloresSeleccionados.get(i) : "Azul";
            PinguinoJugador jugador = new PinguinoJugador(nombres.get(i), color, 0);
            // objetos iniciales para cada jugador
            jugador.getInv().añadirItem(new ItemConcreto("Pez", 1));
            jugador.getInv().añadirItem(new ItemConcreto("Nieve", 1));
            listaJugadores.add(jugador);
        }

        // Añadir la Foca automática como NPC solo si el jugador lo elige
        if (conFoca) {
            Foca focaNPC = new Foca(0, "Foca", "gris");
            listaJugadores.add(focaNPC);
        }

        this.partida = new Partida(new Tablero(), listaJugadores);
    }

    public void nuevaPartida(ArrayList<String> nombres, ArrayList<String> coloresSeleccionados) {
        nuevaPartida(nombres, coloresSeleccionados, true); // por defecto con foca (compatibilidad)
    }

    // compatibilidad con llamadas anteriores
    public void nuevaPartida(ArrayList<String> nombres) {
        ArrayList<String> colores = new ArrayList<>();
        colores.add("Azul");
        colores.add("Rojo");
        colores.add("Verde");
        colores.add("Amarillo");

        nuevaPartida(nombres, colores);
    }

    public int tiraDado(Jugador j, Dado dadoOpcional) {
        if (dadoOpcional != null) {
            return dadoOpcional.tirar();
        }
        Dado d = new Dado();
        return d.tirar();
    }

    private int ultimoTiro;

    public int getUltimoTiro() {
        return ultimoTiro;
    }

    // ejecuta el turno completo del jugador actual
    public void ejecutarTurnoCompleto() {
        if (partida != null && !partida.isFinalizado()) {
            Jugador j = partida.getJugador().get(partida.getJugadorActual());
            // si esta congelado pierde el turno
            if (j.estaCongelado()) {
                j.pasaTurnoCongelado();
                gestorJugador.jugadorFinalizaTurno(j);
                siguienteTurno();
                return;
            }
            procesarTurnoJugador(j);
            siguienteTurno();
        }
    }

    public void procesarTurnoJugador(Jugador j) {
        // Conservado para lógica no animada: ejecutar movimiento completo.
        int pasos = tiraDado(j, null);
        this.ultimoTiro = pasos;
        gestorJugador.jugadorSeMueve(j, pasos, partida.getTablero());

        if (j instanceof Jugador) {
            int posActual = j.getPosicion();
            ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
            if (posActual >= 0 && posActual < casillas.size()) {
                Casilla c = casillas.get(posActual);
                gestorTablero.ejecutaCasilla(partida, j, c);
            }
        }

        actualizarEstadoTablero();
        gestorTablero.comprobarFinTurno(partida);
        gestorJugador.jugadorFinalizaTurno(j);
        siguienteTurno();
    }

    public String moverJugadorUnPaso(Jugador j) {
        gestorJugador.jugadorSeMueve(j, 1, partida.getTablero());
        return "Moviendo " + j.getNom() + " a " + j.getPosicion();
    }

    public String aplicarCasillaActual(Jugador j) {
        if (!(j instanceof Pinguino) || partida == null || partida.getTablero() == null) {
            return "";
        }

        int posActual = j.getPosicion();
        ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
        if (posActual >= 0 && posActual < casillas.size()) {
            Casilla c = casillas.get(posActual);
            String clase = c.getClass().getSimpleName();
            c.realizarAccion(partida, j);
            if (c instanceof Oso) {
                return "¡El oso ha atacado a " + j.getNom() + " y retrocedió 3 casillas!";
            } else if (c instanceof Trineo) {
                return "¡" + j.getNom() + " ha encontrado un trineo y avanza 4 casillas!";
            } else if (c instanceof Agujero) {
                if (j.getPosicion() == 0) {
                    return "¡" + j.getNom() + " ha caído en el primer agujero y vuelve a la salida!";
                } else {
                    return "¡" + j.getNom() + " ha caído en un agujero y retrocedió al anterior (casilla " + j.getPosicion() + ")!";
                }
            } else if (c instanceof SueloQuebradizo) {
                return "Suelo quebradizo: posibilidad de derrumbe activada.";
            } else {
                return "";
            }
        }

        return "";
    }

    public void completarTurno(Jugador j) {
        actualizarEstadoTablero();
        gestorTablero.comprobarFinTurno(partida);
        if (!partida.isFinalizado()) {
            gestorJugador.jugadorFinalizaTurno(j);
            siguienteTurno();
        }
    }

    public void actualizarEstadoTablero() {
        if (partida != null && partida.getTablero() != null) {
            partida.getTablero().actualizarTablero(partida.getJugador());
        }
    }

    public void siguienteTurno() {
        if (partida != null) {
            int total = partida.getJugador().size();
            if (total > 0) {
                int siguiente = (partida.getJugadorActual() + 1) % total;
                partida.setJugadorActual(siguiente);
            }
        }
    }

    public Partida getPartida() {
        return partida;
    }

    public void setConexionBD(Connection conexion) {
        this.conexionBD = conexion;
    }

    // guarda la partida en la base de datos (blob y tablas relacionales)
    public boolean guardarPartidaBD(String nombrePartida, String usuario) {
        if (partida == null || conexionBD == null) {
            return false;
        }

        try {
            conexionBD.setAutoCommit(false);

            // serializa la partida y la cifra para guardarla como blob
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(partida);
            oos.close();
            byte[] datosPartida = aplicarCifrado(baos.toByteArray());

            // inserta en la tabla partidas (el blob)
            String sqlBlob = "INSERT INTO PARTIDAS (nombre, usuario, datos, fecha_creacion) VALUES (?, ?, ?, SYSDATE)";
            PreparedStatement psBlob = conexionBD.prepareStatement(sqlBlob);
            psBlob.setString(1, nombrePartida);
            psBlob.setString(2, usuario);
            psBlob.setBytes(3, datosPartida);
            psBlob.executeUpdate();
            psBlob.close();

            // 2. Guardar en tabla relacional PARTIDA
            String sqlPartida = "INSERT INTO PARTIDA (NUM_TURNOS, JUGADOR_ACTUAL, FECHA) VALUES (?, ?, SYSDATE)";
            PreparedStatement psPartida = conexionBD.prepareStatement(sqlPartida);
            psPartida.setInt(1, 0); // Si quieres un registro de turno podrías ponerlo
            psPartida.setInt(2, partida.getJugadorActual());
            psPartida.executeUpdate();
            psPartida.close();

            int idPartida = -1;
            // Evitar getGeneratedKeys() en Oracle porque el driver ojdbc a veces no lo soporta bien o devuelve el ROWID internamente 
            PreparedStatement psMax = conexionBD.prepareStatement("SELECT MAX(ID_PARTIDA) FROM PARTIDA");
            ResultSet rsMax = psMax.executeQuery();
            if (rsMax.next()) {
                idPartida = rsMax.getInt(1);
            }
            rsMax.close();
            psMax.close();

            // 3. Guardar detalles del jugador en JUGADOR_PARTIDA
            if (idPartida != -1) {
                String sqlVerificarUsr = "SELECT COUNT(*) FROM USUARIO WHERE NICKNAME = ?";
                PreparedStatement psChkUsr = conexionBD.prepareStatement(sqlVerificarUsr);

                String sqlCrearUsr = "INSERT INTO USUARIO (NICKNAME, CONTRASENA) VALUES (?, 'invitado')";
                PreparedStatement psCrearUsr = conexionBD.prepareStatement(sqlCrearUsr);

                String sqlJugador = "INSERT INTO JUGADOR_PARTIDA (ID_PARTIDA, NICKNAME, POSICION, COLOR, INVENTARIO) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psJugador = conexionBD.prepareStatement(sqlJugador);

                for (Jugador jug : partida.getJugador()) {
                    if (jug instanceof Pinguino) {
                        Pinguino p = (Pinguino) jug;
                        String nick = p.getNom() == null ? "Jugador_Desconocido" : p.getNom().trim();

                        // verifica si el usuario existe para evitar errores de clave foranea
                        psChkUsr.setString(1, nick);
                        ResultSet rsChk = psChkUsr.executeQuery();
                        rsChk.next();
                        if (rsChk.getInt(1) == 0) {
                            psCrearUsr.setString(1, nick);
                            psCrearUsr.executeUpdate();
                        }
                        rsChk.close();

                        // guarda el resumen del inventario en texto
                        StringBuilder invTexto = new StringBuilder();
                        for (Item item : p.getInv().getItems()) {
                            if (item.getCantidad() > 0) {
                                invTexto.append(item.getNombre()).append(" x").append(item.getCantidad()).append("; ");
                            }
                        }

                        psJugador.setInt(1, idPartida);
                        psJugador.setString(2, nick);
                        psJugador.setInt(3, p.getPosicion());
                        psJugador.setString(4, p.getColor() != null ? p.getColor() : "Desconocido");
                        
                        String invAux = invTexto.toString();
                        if (invAux.isEmpty()) invAux = "Vacío";
                        psJugador.setString(5, invAux);
                        psJugador.executeUpdate();
                    }
                }
                psChkUsr.close();
                psCrearUsr.close();
                psJugador.close();
            }

            conexionBD.commit();
            conexionBD.setAutoCommit(true);

            System.out.println("Partida guardada en BB.DD (BLOB y Tablas Relacionales): " + nombrePartida);
            return true;
        } catch (SQLException e) {
            System.err.println("--- ERROR SQL GRAVE AL GUARDAR PARTIDA ---");
            e.printStackTrace();
            try {
                if (conexionBD != null)
                    conexionBD.rollback();
            } catch (SQLException ex) {
            }
            return false;
        } catch (IOException e) {
            System.err.println("Error al serializar partida: " + e.getMessage());
            return false;
        }
    }

    // carga una partida desde el blob de la base de datos
    public boolean cargarPartidaBD(int idPartida) {
        if (conexionBD == null) {
            return false;
        }

        try {
            String sql = "SELECT datos FROM PARTIDAS WHERE id = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, idPartida);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // descifra y deserializa el objeto partida
                byte[] datosPartida = aplicarCifrado(rs.getBytes("datos"));
                ByteArrayInputStream bais = new ByteArrayInputStream(datosPartida);
                ObjectInputStream ois = new ObjectInputStream(bais);
                this.partida = (Partida) ois.readObject();
                ois.close();
                rs.close(); ps.close();
                return true;
            } else {
                rs.close(); ps.close();
                return false;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public ArrayList<String[]> listarPartidasBD(String usuario) {
        ArrayList<String[]> partidas = new ArrayList<>();
        if (conexionBD == null) {
            return partidas;
        }

        try {
            String sql = "SELECT id, nombre, fecha_creacion FROM PARTIDAS WHERE usuario = ? ORDER BY fecha_creacion DESC";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] partida = {
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nombre"),
                        rs.getString("fecha_creacion")
                };
                partidas.add(partida);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al listar partidas: " + e.getMessage());
        }

        return partidas;
    }

    public boolean guardarPartida(File archivo) {
        if (partida == null) {
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(partida);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cargarPartida(File archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Partida cargada = (Partida) ois.readObject();
            this.partida = cargada;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String mostrarInventarioActual() {
        if (partida == null) {
            return "No hay partida activa.";
        }
        Jugador actual = partida.getJugador().get(partida.getJugadorActual());
        if (!(actual instanceof Pinguino)) {
            return "Jugador actual no es un pinguino.";
        }
        Inventario inv = ((Pinguino) actual).getInv();
        if (inv.getItems().isEmpty()) {
            return "Inventario vacío.";
        }
        StringBuilder sb = new StringBuilder();
        for (Item it : inv.getItems()) {
            sb.append(it.getNombre()).append(" x").append(it.getCantidad()).append("\n");
        }
        return sb.toString();
    }

    public boolean usarItemActual(String nombreItem) {
        if (partida == null) {
            return false;
        }
        Jugador actual = partida.getJugador().get(partida.getJugadorActual());
        if (!(actual instanceof Pinguino)) {
            return false;
        }
        Pinguino p = (Pinguino) actual;
        Inventario inv = p.getInv();
        for (Item item : inv.getItems()) {
            if (item.getNombre().equalsIgnoreCase(nombreItem) && item.getCantidad() > 0) {
                item.setCantidad(item.getCantidad() - 1);
                if (item.getCantidad() <= 0) {
                    inv.eliminarItem(item);
                }

                switch (item.getNombre().toLowerCase()) {
                    case "pez":
                        p.moverPosicion(1);
                        return true;
                    case "nieve": {
                        int total = partida.getJugador().size();
                        if (total > 1) {
                            int siguiente = (partida.getJugadorActual() + 1) % total;
                            Jugador objetivo = partida.getJugador().get(siguiente);
                            objetivo.setTurnosCongelado(1);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Aplica un cifrado simple XOR a los datos para cumplir con el requisito de encriptación.
     */
    private byte[] aplicarCifrado(byte[] data) {
        byte[] key = "PINGUINO_KEY_2024".getBytes();
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}