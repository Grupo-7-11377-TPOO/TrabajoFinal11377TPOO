package Modelo;

public class Login {
	
	private int idIdentificacion;
    private String rol;          // Puede ser 'Dueño' o 'Empleado'
    private String contrasena;
	
    public int getIdIdentificacion() {
		return idIdentificacion;
	}
	public void setIdIdentificacion(int idIdentificacion) {
		this.idIdentificacion = idIdentificacion;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
    
}
