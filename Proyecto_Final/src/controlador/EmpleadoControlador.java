package controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Empleados;
import conexion.ConexionBD;
import java.sql.*;

public class EmpleadoControlador {
	public List<Empleados> listarEmpleados() {
        List<Empleados> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleados e = new Empleados();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setTelefono(rs.getInt("telefono"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public void agregarEmpleado(Empleados empleado) {
    	 String sql = "INSERT INTO Empleados (idEmpleado, nombre, apellido, telefono) VALUES (?, ?, ?, ?)";
    	    try (Connection conn = ConexionBD.getConexion();
    	         PreparedStatement stmt = conn.prepareStatement(sql)) {

    	        stmt.setInt(1, empleado.getIdEmpleado());
    	        stmt.setString(2, empleado.getNombre());
    	        stmt.setString(3, empleado.getApellido());
    	        stmt.setInt(4, empleado.getTelefono());

    	        stmt.executeUpdate();
    	        JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente.");
    	    } catch (SQLIntegrityConstraintViolationException ex) {
    	        // Esto captura específicamente el error de clave primaria duplicada
    	        JOptionPane.showMessageDialog(null, "Error: el ID del empleado ya existe en la base de datos.");
    	    } catch (SQLException ex) {
    	        // Otros errores de SQL
    	        ex.printStackTrace();
    	        JOptionPane.showMessageDialog(null, "Error al agregar empleado: " + ex.getMessage());
    	    }
    }

    public void eliminarEmpleado(Empleados empleado) {
        String sql = "DELETE FROM Empleados WHERE idEmpleado = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empleado.getIdEmpleado());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarEmpleado(Empleados empleado) {
        String sql = "UPDATE Empleados SET nombre = ?, apellido = ?, telefono = ? WHERE idEmpleado = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setInt(3, empleado.getTelefono());
            stmt.setInt(4, empleado.getIdEmpleado());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
