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
import jocpinguiFinal.Model.Oso;
import jocpinguiFinal.Model.Partida;
import jocpinguiFinal.Model.Pinguino;
import jocpinguiFinal.Model.PinguinoJugador;
import jocpinguiFinal.Model.SueloQuebradizo;
import jocpinguiFinal.Model.Tablero;
import jocpinguiFinal.Model.Trineo;

public class GestorPartida implements Serializable {
    private static final long serialVersionUID = 1L;
    private Partida partida;
    private GestorTablero gestorTablero;
    private GestorJugador gestorJugador;
    private Random random;
    private Connection conexionBD;

    public GestorPartida() {
        this.gestorTablero = new GestorTablero();
        this.gestorJugador = new GestorJugador();
        this.random = new Random();
    }

    public void nuevaPartida(ArrayList<String> nombres, ArrayList<String> coloresSeleccionados) {
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size() && i < 4; i++) {
            String color = i < coloresSeleccionados.size() ? coloresSeleccionados.get(i) : "Azul";
            PinguinoJugador jugador = new PinguinoJugador(nombres.get(i), color, 0);
            // Asignar objetos iniciales por defecto
            jugador.getInv().añadirItem(new ItemConcreto("Pez", 1));
            jugador.getInv().añadirItem(new ItemConcreto("Hielo", 1));
            jugador.getInv().añadirItem(new ItemConcreto("Escudo", 1));
            listaJugadores.add(jugador);
        }

        this.partida = new Partida(new Tablero(), listaJugadores);
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

    public void ejecutarTurnoCompleto() {
        if (partida != null && !partida.isFinalizado()) {
            Jugador j = partida.getJugador().get(partida.getJugadorActual());
            if (j.estaCongelado()) {
                System.out.println("El jugador " + j.getNom() + " está congelado y pierde este turno. Turnos restantes: " + j.getTurnosCongelado());
                j.pasaTurnoCongelado();
                if (!j.estaCongelado()) {
                    System.out.println("El jugador " + j.getNom() + " ya no está congelado.");
                }
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

        if (j instanceof Pinguino) {
            int posActual = j.getPosicion();
            ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
            if (posActual >= 0 && posActual < casillas.size()) {
                Casilla c = casillas.get(posActual);
                gestorTablero.ejecutaCasilla(partida, (Pinguino) j, c);
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
                return "¡" + j.getNom() + " ha caído en un agujero y vuelve a la salida!";
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

    public boolean guardarPartidaBD(String nombrePartida, String usuario) {
        if (partida == null || conexionBD == null) {
            System.out.println("Error: Partida o conexión nula");
            return false;
        }

        try {
            // Serializar la partida a bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(partida);
            oos.close();
            byte[] datosPartida = baos.toByteArray();

            // Insertar en la BB.DD
            String sql = "INSERT INTO PARTIDAS (nombre, usuario, datos, fecha_creacion) VALUES (?, ?, ?, SYSDATE)";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, nombrePartida);
            ps.setString(2, usuario);
            ps.setBytes(3, datosPartida);
            ps.executeUpdate();
            ps.close();

            System.out.println("Partida guardada en BB.DD: " + nombrePartida);
            return true;
        } catch (SQLException e) {
            System.out.println("Error SQL al guardar partida: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error al serializar partida: " + e.getMessage());
            return false;
        }
    }

    public boolean cargarPartidaBD(int idPartida) {
        if (conexionBD == null) {
            System.out.println("Error: Conexión a BB.DD nula");
            return false;
        }

        try {
            String sql = "SELECT datos FROM PARTIDAS WHERE id = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, idPartida);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] datosPartida = rs.getBytes("datos");
                ByteArrayInputStream bais = new ByteArrayInputStream(datosPartida);
                ObjectInputStream ois = new ObjectInputStream(bais);
                this.partida = (Partida) ois.readObject();
                ois.close();

                rs.close();
                ps.close();

                System.out.println("Partida cargada desde BB.DD");
                return true;
            } else {
                System.out.println("Partida no encontrada con ID: " + idPartida);
                rs.close();
                ps.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al cargar partida: " + e.getMessage());
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar partida: " + e.getMessage());
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
                    case "hielo": {
                        int total = partida.getJugador().size();
                        if (total > 1) {
                            int siguiente = (partida.getJugadorActual() + 1) % total;
                            Jugador objetivo = partida.getJugador().get(siguiente);
                            objetivo.setTurnosCongelado(1);
                            System.out.println("El jugador " + objetivo.getNom() + " ha sido congelado por 1 turno.");
                        }
                        return true;
                    }
                    case "escudo":
                        System.out.println("Escudo usado: protección activada por 1 turno (sin efecto activo). ");
                        return true;
                }
            }
        }
        return false;
    }
}