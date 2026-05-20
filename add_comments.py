import os
import re

comments = {
    "Lanzador.java": "esta clase se encarga de inicializar y lanzar componentes basicos del juego.",
    "GestorJugador.java": "gestiona toda la logica relacionada con los jugadores (turnos, movimientos, estados).",
    "Main.java": "punto de entrada principal del programa. inicia la aplicacion.",
    "GestorTablero.java": "controla la logica del tablero y la ejecucion de eventos en las casillas.",
    "Final.java": "casilla de meta. el jugador que llega a esta casilla gana la partida.",
    "Dado.java": "simula el lanzamiento de un dado para generar los pasos que debe avanzar un jugador.",
    "Trineo.java": "casilla con el evento del trineo, que hace avanzar al jugador varias posiciones extra.",
    "Pinguino.java": "clase padre o especifica para los pinguinos que se mueven por el tablero.",
    "SueloQuebradizo.java": "casilla de suelo quebradizo, introduce un riesgo al pasar o caer sobre ella.",
    "Partida.java": "guarda todo el estado de la partida actual: turno, jugadores y configuracion del tablero.",
    "Casilla.java": "clase base de la que heredan todos los tipos de casillas del tablero.",
    "Evento.java": "clase base o manejadora para los eventos especiales del tablero.",
    "Agujero.java": "casilla de agujero que penaliza al jugador haciendolo retroceder o perder su avance.",
    "Usuario.java": "representa la entidad del usuario en la base de datos (credenciales, victorias, etc).",
    "Pez.java": "item especial que da ventajas al pinguino, como avanzar posiciones adicionales.",
    "ItemConcreto.java": "implementacion concreta de un item que puede guardarse en el inventario.",
    "Tablero.java": "estructura que contiene la lista de casillas y define el camino del juego.",
    "PinguinoJugador.java": "representa un pinguino controlado directamente por una persona.",
    "Normal.java": "casilla estandar del tablero, no tiene ningun efecto o evento especial.",
    "Inventario.java": "bolsa o mochila virtual donde los jugadores guardan items (peces, bolas de nieve).",
    "Jugador.java": "clase base para cualquier entidad que participe (pinguinos, foca).",
    "Foca.java": "entidad que puede actuar como npc persiguiendo a los pinguinos en el tablero.",
    "Item.java": "clase abstracta o interfaz que define las propiedades basicas de los objetos recolectables.",
    "BolaDeNieve.java": "item o evento que sirve para ralentizar o congelar el turno de un rival.",
    "Oso.java": "evento de oso polar, penaliza al jugador empujandolo hacia atras.",
    "PantallaJuego.java": "vista principal donde se dibuja el tablero, los pinguinos y se interactua durante el juego.",
    "Aplicacion.java": "gestor principal de la interfaz grafica, cambia entre los distintos paneles o pantallas.",
    "menu.java": "panel antiguo o alternativo del menu principal.",
    "PantallaMenu.java": "vista del menu inicial donde se puede elegir nueva partida o cargar.",
    "PantallaPartida.java": "pantalla donde se configuran los jugadores y colores antes de empezar.",
    "PantallaCarga.java": "vista donde se muestran y seleccionan las partidas guardadas en la bbdd.",
    "AppState.java": "almacena informacion o estado global para la interfaz visual."
}

base_dir = "/Users/gerardlechosa/Documents/Github/Joc-Pengui-GR04/jocpinguiFinal/src/jocpinguiFinal"

for root, dirs, files in os.walk(base_dir):
    for file in files:
        if file in comments and file.endswith(".java"):
            filepath = os.path.join(root, file)
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
            
            # check if it already has a comment
            if comments[file] in content:
                continue
            
            comment_text = f"/**\n * {comments[file]}\n */\n"
            
            # insert before public class or class or abstract class or interface
            # regex to find the start of the class
            new_content = re.sub(r'((?:public\s+|abstract\s+)*(?:class|interface)\s+\w+)', comment_text + r'\1', content, count=1)
            
            if new_content != content:
                with open(filepath, "w", encoding="utf-8") as f:
                    f.write(new_content)
                print(f"Added comment to {file}")

