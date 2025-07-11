package controlador;
import conexion.ConexionBD;
import Modelo.Proovedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProovedorControlador {
	public List<Proovedor> listarProovedores() {
        List<Proovedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proovedor";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Proovedor p = new Proovedor();
                p.setIdProovedor(rs.getInt("idProovedor"));
                p.setNombre(rs.getString("Nombre"));
                p.setRuc(rs.getString("RUC"));
                p.setTelefono(rs.getString("Telefono"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return lista;
    }

    public void agregarProovedor(Proovedor p) {
        String sql = "INSERT INTO Proovedor (idProovedor, Nombre, RUC, Telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdProovedor());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getRuc());
            stmt.setString(4, p.getTelefono());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente.");
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Error: El ID del proveedor ya existe.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar proveedor: " + e.getMessage());
        }
    }

    public void eliminarProovedor(int id) {
        String sql = "DELETE FROM Proovedor WHERE idProovedor = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProovedor(Proovedor p) {
        String sql = "UPDATE Proovedor SET Nombre = ?, RUC = ?, Telefono = ? WHERE idProovedor = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getRuc());
            stmt.setString(3, p.getTelefono());
            stmt.setInt(4, p.getIdProovedor());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
