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

// esta clase gestiona toda la logica de una partida, como crearla, guardarla, cargarla
// y procesar los turnos de los jugadores. es el cerebro del juego.
public class GestorPartida implements Serializable {
    // variable que guarda informacion sobre serialversionuid
    private static final long serialVersionUID = 1L;
    // variable que guarda informacion sobre partida
    private Partida partida; // objeto que contiene el estado de la partida
    // variable que guarda informacion sobre gestortablero
    private GestorTablero gestorTablero; // encargado de la logica de las casillas
    // variable que guarda informacion sobre gestorjugador
    private GestorJugador gestorJugador; // encargado de la logica de movimiento
    // variable que guarda informacion sobre random
    private Random random;
    // variable que guarda informacion sobre conexionbd
    private Connection conexionBD; // conexion activa a la base de datos

    // metodo encargado de la funcion gestorpartida recibiendo parametros: ninguno
    public GestorPartida() {
        this.gestorTablero = new GestorTablero();
        this.gestorJugador = new GestorJugador();
        this.random = new Random();
    }

    // crea una partida nueva con los jugadores y colores indicados
    public void nuevaPartida(ArrayList<String> nombres, ArrayList<String> coloresSeleccionados, boolean conFoca) {
        // variable que guarda informacion sobre listajugadores
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size() && i < 4; i++) {
            // variable que guarda informacion sobre color
            String color = i < coloresSeleccionados.size() ? coloresSeleccionados.get(i) : "Azul";
            
            // REGISTRO AUTOMÁTICO EN BD: Aseguramos que el usuario existe antes de iniciar
            String nombreFinal = asegurarUsuarioEnBD(nombres.get(i));
            
            // variable que guarda informacion sobre jugador
            PinguinoJugador jugador = new PinguinoJugador(nombreFinal, color, 0);
            // objetos iniciales para cada jugador
            jugador.getInv().añadirItem(new ItemConcreto("Pez", 1));
            jugador.getInv().añadirItem(new ItemConcreto("Nieve", 1));
            listaJugadores.add(jugador);
        }

        // Añadir la Foca automática como NPC solo si el jugador lo elige
        if (conFoca) {
            // variable que guarda informacion sobre focanpc
            Foca focaNPC = new Foca(0, "Foca", "gris");
            listaJugadores.add(focaNPC);
        }

        this.partida = new Partida(new Tablero(), listaJugadores);
    }

    // metodo encargado de la funcion nuevapartida recibiendo parametros: ArrayList<String> nombres, ArrayList<String> coloresSeleccionados
    public void nuevaPartida(ArrayList<String> nombres, ArrayList<String> coloresSeleccionados) {
        nuevaPartida(nombres, coloresSeleccionados, true); // por defecto con foca (compatibilidad)
    }

    // compatibilidad con llamadas anteriores
    public void nuevaPartida(ArrayList<String> nombres) {
        // variable que guarda informacion sobre colores
        ArrayList<String> colores = new ArrayList<>();
        colores.add("Azul");
        colores.add("Rojo");
        colores.add("Verde");
        colores.add("Amarillo");

        nuevaPartida(nombres, colores);
    }

    // metodo encargado de la funcion tiradado recibiendo parametros: Jugador j, Dado dadoOpcional
    public int tiraDado(Jugador j, Dado dadoOpcional) {
        if (dadoOpcional != null) {
            return dadoOpcional.tirar();
        }
        // variable que guarda informacion sobre d
        Dado d = new Dado();
        return d.tirar();
    }

    // variable que guarda informacion sobre ultimotiro
    private int ultimoTiro;

    // metodo que devuelve el valor de ultimotiro
    public int getUltimoTiro() {
        return ultimoTiro;
    }

    // ejecuta el turno completo del jugador actual
    public void ejecutarTurnoCompleto() {
        if (partida != null && !partida.isFinalizado()) {
            // variable que guarda informacion sobre j
            Jugador j = partida.getJugador().get(partida.getJugadorActual());
            // si esta congelado pierde el turno
            if (j.estaCongelado()) {
                j.pasaTurnoCongelado();
                gestorJugador.jugadorFinalizaTurno(j);
                siguienteTurno();
            } else {
                procesarTurnoJugador(j);
                siguienteTurno();
            }
        }
    }

    // metodo encargado de la funcion procesarturnojugador recibiendo parametros: Jugador j
    public void procesarTurnoJugador(Jugador j) {
        // Conservado para lógica no animada: ejecutar movimiento completo.
        int pasos = tiraDado(j, null);
        this.ultimoTiro = pasos;
        gestorJugador.jugadorSeMueve(j, pasos, partida.getTablero());

        if (j instanceof Jugador) {
            // variable que guarda informacion sobre posactual
            int posActual = j.getPosicion();
            // variable que guarda informacion sobre casillas
            ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
            if (posActual >= 0 && posActual < casillas.size()) {
                // variable que guarda informacion sobre c
                Casilla c = casillas.get(posActual);
                gestorTablero.ejecutaCasilla(partida, j, c);
            }
        }

        actualizarEstadoTablero();
        gestorTablero.comprobarFinTurno(partida);
        gestorJugador.jugadorFinalizaTurno(j);
        siguienteTurno();
    }

    // metodo encargado de la funcion moverjugadorunpaso recibiendo parametros: Jugador j
    public String moverJugadorUnPaso(Jugador j) {
        gestorJugador.jugadorSeMueve(j, 1, partida.getTablero());
        return "Moviendo " + j.getNom() + " a " + j.getPosicion();
    }

    // metodo encargado de la funcion aplicarcasillaactual recibiendo parametros: Jugador j
    public String aplicarCasillaActual(Jugador j) {
        if (!(j instanceof Pinguino) || partida == null || partida.getTablero() == null) {
            return "";
        }

        // variable que guarda informacion sobre posactual
        int posActual = j.getPosicion();
        // variable que guarda informacion sobre casillas
        ArrayList<Casilla> casillas = partida.getTablero().getCasillas();
        if (posActual >= 0 && posActual < casillas.size()) {
            // variable que guarda informacion sobre c
            Casilla c = casillas.get(posActual);
            // variable que guarda informacion sobre clase
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

    // metodo encargado de la funcion completarturno recibiendo parametros: Jugador j
    public void completarTurno(Jugador j) {
        actualizarEstadoTablero();
        gestorTablero.comprobarFinTurno(partida);
        if (!partida.isFinalizado()) {
            gestorJugador.jugadorFinalizaTurno(j);
            siguienteTurno();
        }
    }

    // metodo encargado de la funcion actualizarestadotablero recibiendo parametros: ninguno
    public void actualizarEstadoTablero() {
        if (partida != null && partida.getTablero() != null) {
            partida.getTablero().actualizarTablero(partida.getJugador());
        }
    }

    // metodo encargado de la funcion siguienteturno recibiendo parametros: ninguno
    public void siguienteTurno() {
        if (partida != null) {
            partida.setTurnos(partida.getTurnos() + 1);
            // variable que guarda informacion sobre total
            int total = partida.getJugador().size();
            if (total > 0) {
                // variable que guarda informacion sobre siguiente
                int siguiente = (partida.getJugadorActual() + 1) % total;
                partida.setJugadorActual(siguiente);
            }
        }
    }

    // metodo que devuelve el valor de partida
    public Partida getPartida() {
        return partida;
    }

    // metodo que actualiza o establece el valor de conexionbd
    public void setConexionBD(Connection conexion) {
        this.conexionBD = conexion;
    }

    /**
     * Asegura que un usuario existe en la tabla USUARIO. 
     * Si no existe, lo crea con una contraseña por defecto.
     * Retorna el nombre exacto de la base de datos (por si ya existía con otra capitalización).
     */
    public String asegurarUsuarioEnBD(String nickname) {
        if (conexionBD == null || nickname == null || nickname.trim().isEmpty() || nickname.equalsIgnoreCase("Foca")) {
            return (nickname != null) ? nickname.trim() : "Invitado";
        }

        // variable que guarda informacion sobre nicklimpio
        String nickLimpio = nickname.trim();
        try {
            // Verificar si existe (insensible a mayúsculas)
            String sqlChk = "SELECT NICKNAME FROM USUARIO WHERE UPPER(NICKNAME) = UPPER(?)";
            try (PreparedStatement psChk = conexionBD.prepareStatement(sqlChk)) {
                psChk.setString(1, nickLimpio);
                try (ResultSet rsChk = psChk.executeQuery()) {
                    if (rsChk.next()) {
                        // Ya existe, retornamos el nombre tal cual está en la BD
                        return rsChk.getString("NICKNAME");
                    } else {
                        // No existe, lo insertamos
                        String sqlIns = "INSERT INTO USUARIO (NICKNAME, CONTRASENA, VICTORIAS) VALUES (?, 'invitado', 0)";
                        try (PreparedStatement psIns = conexionBD.prepareStatement(sqlIns)) {
                            psIns.setString(1, nickLimpio);
                            psIns.executeUpdate();
                            System.out.println("[BD] Usuario registrado automáticamente al inicio: " + nickLimpio);
                        }
                        return nickLimpio;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[BD] Error al asegurar usuario " + nickLimpio + ": " + e.getMessage());
            return nickLimpio;
        }
    }

    // guarda la partida en la base de datos (se guarda como blob en partidas y con datos en tablas relacionadas)
    // aqui es donde se recogen los datos del juego actual y se insertan en la bbdd
    public boolean guardarPartidaBD(String nombrePartida, String usuario) {
        if (partida == null || conexionBD == null) {
            return false;
        }

        // Limpiamos el nombre de usuario para evitar errores de espacios
        final String usuarioLimpio = (usuario != null) ? usuario.trim() : "Invitado";

        try {
            conexionBD.setAutoCommit(false);

            // serializa la partida y la cifra para guardarla como blob
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // variable que guarda informacion sobre oos
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(partida);
            oos.close();
            // variable que guarda informacion sobre datospartida
            byte[] datosPartida = aplicarCifrado(baos.toByteArray());


            // 0. ASEGURAR QUE EL USUARIO EXISTE ANTES DE INSERTAR LA PARTIDA
            String usuarioParaInsertar = asegurarUsuarioEnBD(usuarioLimpio);

            // 1. Inserta en la tabla partidas (el blob)
            String sqlBlob = "INSERT INTO PARTIDAS (nombre, usuario, datos, fecha_creacion) VALUES (?, ?, ?, SYSDATE)";
            // Usamos "ID" en mayúsculas explícitamente para Oracle
            PreparedStatement psBlob = conexionBD.prepareStatement(sqlBlob, new String[]{"ID"});
            // Desactivamos el trigger temporalmente para evitar doble conteo de victorias.
            // Las victorias se gestionan explícitamente desde registrarVictoria() en PantallaJuego.
            try {
                conexionBD.prepareStatement("ALTER TRIGGER TRG_INCREMENTAR_VICTORIAS DISABLE").execute();
            } catch (Exception trigEx) {
                // Si el trigger no existe, no hay problema
                System.out.println("[BD] Trigger TRG_INCREMENTAR_VICTORIAS no encontrado (ignorando): " + trigEx.getMessage());
            }

            psBlob.setString(1, nombrePartida);
            psBlob.setString(2, usuarioParaInsertar);
            psBlob.setBytes(3, datosPartida);
            psBlob.executeUpdate();

            // variable que guarda informacion sobre idpartida
            int idPartida = -1;
            // variable que guarda informacion sobre rskeys
            ResultSet rsKeys = psBlob.getGeneratedKeys();
            if (rsKeys.next()) {
                idPartida = rsKeys.getInt(1);
            }
            rsKeys.close();
            psBlob.close();

            // Volvemos a habilitar el trigger tras el INSERT
            try {
                conexionBD.prepareStatement("ALTER TRIGGER TRG_INCREMENTAR_VICTORIAS ENABLE").execute();
            } catch (Exception trigEx) {
                System.out.println("[BD] No se pudo re-habilitar TRG_INCREMENTAR_VICTORIAS: " + trigEx.getMessage());
            }

            // 2. Guardar en tabla relacional PARTIDA usando el mismo ID
            if (idPartida != -1) {
                // variable que guarda informacion sobre sqlpartida
                String sqlPartida = "INSERT INTO PARTIDA (ID_PARTIDA, NUM_TURNOS, JUGADOR_ACTUAL, FECHA) VALUES (?, ?, ?, SYSDATE)";
                // variable que guarda informacion sobre pspartida
                PreparedStatement psPartida = conexionBD.prepareStatement(sqlPartida);
                psPartida.setInt(1, idPartida);
                psPartida.setInt(2, partida.getTurnos());
                psPartida.setInt(3, partida.getJugadorActual());
                psPartida.executeUpdate();
                psPartida.close();
            } else {
                // Fallback por si getGeneratedKeys falló, aunque no debería en Oracle moderno
                String sqlPartida = "INSERT INTO PARTIDA (NUM_TURNOS, JUGADOR_ACTUAL, FECHA) VALUES (?, ?, SYSDATE)";
                // variable que guarda informacion sobre pspartida
                PreparedStatement psPartida = conexionBD.prepareStatement(sqlPartida);
                psPartida.setInt(1, partida.getTurnos());
                psPartida.setInt(2, partida.getJugadorActual());
                psPartida.executeUpdate();
                psPartida.close();

                // variable que guarda informacion sobre psmax
                PreparedStatement psMax = conexionBD.prepareStatement("SELECT MAX(ID_PARTIDA) FROM PARTIDA");
                // variable que guarda informacion sobre rsmax
                ResultSet rsMax = psMax.executeQuery();
                if (rsMax.next()) {
                    idPartida = rsMax.getInt(1);
                }
                rsMax.close();
                psMax.close();
            }

            // 3. Guardar detalles del jugador en JUGADOR_PARTIDA
            if (idPartida != -1) {
                // variable que guarda informacion sobre sqljugador
                String sqlJugador = "INSERT INTO JUGADOR_PARTIDA (ID_PARTIDA, NICKNAME, POSICION, COLOR, INVENTARIO) VALUES (?, ?, ?, ?, ?)";
                // variable que guarda informacion sobre psjugador
                PreparedStatement psJugador = conexionBD.prepareStatement(sqlJugador);

                for (Jugador jug : partida.getJugador()) {
                    if (jug instanceof Pinguino) {
                        // variable que guarda informacion sobre p
                        Pinguino p = (Pinguino) jug;
                        // variable que guarda informacion sobre nick
                        String nick = p.getNom() == null ? "Jugador_Desconocido" : p.getNom().trim();

                        // Aseguramos que el pinguino existe en la tabla USUARIO
                        String nickOficial = asegurarUsuarioEnBD(nick);

                        // guarda el resumen del inventario en texto
                        StringBuilder invTexto = new StringBuilder();
                        for (Item item : p.getInv().getItems()) {
                            if (item.getCantidad() > 0) {
                                invTexto.append(item.getNombre()).append(" x").append(item.getCantidad()).append("; ");
                            }
                        }

                        psJugador.setInt(1, idPartida);
                        psJugador.setString(2, nickOficial);
                        psJugador.setInt(3, p.getPosicion());
                        psJugador.setString(4, p.getColor() != null ? p.getColor() : "Desconocido");
                        
                        // variable que guarda informacion sobre invaux
                        String invAux = invTexto.toString();
                        if (invAux.isEmpty()) invAux = "Vacío";
                        psJugador.setString(5, invAux);
                        psJugador.executeUpdate();
                    }
                }
                psJugador.close();
            }

            conexionBD.commit();
            conexionBD.setAutoCommit(true);

            System.out.println("Partida guardada con éxito en BLOB y tablas relacionales.");
            return true;

        } catch (SQLException e) {
            System.err.println("--- ERROR DE BASE DE DATOS AL GUARDAR ---");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Código Error Oracle: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            
            try {
                if (conexionBD != null) {
                    System.out.println("Ejecutando rollback...");
                    conexionBD.rollback();
                    conexionBD.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error general al serializar/guardar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // en este metodo es donde se seleccionan los datos de una partida guardada
    // de la bbdd y se insertan al juego. es decir, como se carga la partida.
    public boolean cargarPartidaBD(int idPartida) {
        // comprobamos si hay conexion activa a la bbdd
        if (conexionBD == null) {
            return false;
        }

        try {
            // variable que guarda informacion sobre sql
            String sql = "SELECT datos FROM PARTIDAS WHERE id = ?";
            // variable que guarda informacion sobre ps
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, idPartida);
            // aqui ejecutamos la consulta y recuperamos los resultados
            ResultSet rs = ps.executeQuery();

            // si encontramos un resultado (una fila), entramos aqui
            if (rs.next()) {
                // obtenemos los bytes encriptados de la columna 'datos' de la bbdd
                byte[] datosPartida = aplicarCifrado(rs.getBytes("datos"));
                
                // preparamos los bytes para ser leidos como un objeto java (deserializacion)
                ByteArrayInputStream bais = new ByteArrayInputStream(datosPartida);
                // variable que guarda informacion sobre ois
                ObjectInputStream ois = new ObjectInputStream(bais);
                
                // aqui es donde verdaderamente se inserta la partida recuperada al juego
                // asignamos el objeto partida leido a la variable global 'partida' de esta clase
                this.partida = (Partida) ois.readObject();
                
                // cerramos los flujos para no dejar memoria abierta
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

    // metodo encargado de la funcion listarpartidasbd recibiendo parametros: String usuario
    public ArrayList<String[]> listarPartidasBD(String usuario) {
        // variable que guarda informacion sobre partidas
        ArrayList<String[]> partidas = new ArrayList<>();
        if (conexionBD == null) {
            return partidas;
        }

        try {
            // variable que guarda informacion sobre sql
            String sql = "SELECT id, nombre, fecha_creacion FROM PARTIDAS WHERE UPPER(usuario) = UPPER(?) ORDER BY fecha_creacion DESC";
            // variable que guarda informacion sobre ps
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, usuario);
            // variable que guarda informacion sobre rs
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

    // metodo encargado de la funcion guardarpartida recibiendo parametros: File archivo
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

    // metodo encargado de la funcion cargarpartida recibiendo parametros: File archivo
    public boolean cargarPartida(File archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            // variable que guarda informacion sobre cargada
            Partida cargada = (Partida) ois.readObject();
            this.partida = cargada;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // metodo encargado de la funcion mostrarinventarioactual recibiendo parametros: ninguno
    public String mostrarInventarioActual() {
        if (partida == null) {
            return "No hay partida activa.";
        }
        // variable que guarda informacion sobre actual
        Jugador actual = partida.getJugador().get(partida.getJugadorActual());
        if (!(actual instanceof Pinguino)) {
            return "Jugador actual no es un pinguino.";
        }
        // variable que guarda informacion sobre inv
        Inventario inv = ((Pinguino) actual).getInv();
        if (inv.getItems().isEmpty()) {
            return "Inventario vacío.";
        }
        // variable que guarda informacion sobre sb
        StringBuilder sb = new StringBuilder();
        for (Item it : inv.getItems()) {
            sb.append(it.getNombre()).append(" x").append(it.getCantidad()).append("\n");
        }
        return sb.toString();
    }

    // metodo encargado de la funcion usaritemactual recibiendo parametros: String nombreItem
    public boolean usarItemActual(String nombreItem) {
        if (partida == null) {
            return false;
        }
        // variable que guarda informacion sobre actual
        Jugador actual = partida.getJugador().get(partida.getJugadorActual());
        if (!(actual instanceof Pinguino)) {
            return false;
        }
        // variable que guarda informacion sobre p
        Pinguino p = (Pinguino) actual;
        // variable que guarda informacion sobre inv
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
                        // variable que guarda informacion sobre total
                        int total = partida.getJugador().size();
                        if (total > 1) {
                            // variable que guarda informacion sobre siguiente
                            int siguiente = (partida.getJugadorActual() + 1) % total;
                            // variable que guarda informacion sobre objetivo
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
        // variable que guarda informacion sobre key
        byte[] key = "PINGUINO_KEY_2024".getBytes();
        // variable que guarda informacion sobre result
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}