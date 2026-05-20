package jocpinguiFinal.Model;

/**
 * representa la entidad del usuario en la base de datos (credenciales, victorias, etc).
 */
public class Usuario implements java.io.Serializable {
	// variable que guarda informacion sobre serialversionuid
	private static final long serialVersionUID = 1L;
	// variable que guarda informacion sobre usuario
	private String usuario;
	// variable que guarda informacion sobre contraseña
	private String contraseña;
	
	// metodo encargado de la funcion usuario recibiendo parametros: String usuario, String contraseña
	public Usuario(String usuario, String contraseña) {
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	
	// metodo que devuelve el valor de usuario
	public String getUsuario() {
		return usuario;
	}
	
	// metodo que actualiza o establece el valor de usuario
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	// metodo que devuelve el valor de contraseña
	public String getContraseña() {
		return contraseña;
	}
	
	// metodo que actualiza o establece el valor de contraseña
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	@Override
	// metodo encargado de la funcion tostring recibiendo parametros: ninguno
	public String toString() {
		return "Usuario: " + usuario;
	}
}
