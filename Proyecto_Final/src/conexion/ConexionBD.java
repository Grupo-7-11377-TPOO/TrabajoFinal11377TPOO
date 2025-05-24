package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	private static final String URL = "jdbc:mysql://localhost:3306/Negocio";
    private static final String USER = "root"; // reemplaza si tu usuario es distinto
    private static final String PASS = "Rufus2022()=";     // pon tu contraseña si tienes

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
