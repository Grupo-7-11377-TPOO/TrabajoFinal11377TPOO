package controlador;

import conexion.ConexionBD;
import Modelo.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ControladorLogin {
	
	public boolean validarLogin(String id, String contrasena, String rol) {
        String sql = "SELECT * FROM Login WHERE idIdentificacion = ? AND contrasena = ? AND rol = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, contrasena);
            stmt.setString(3, rol);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true si encuentra un registro válido
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar login: " + e.getMessage());
        }
        return false;
    }
	
	public List<Login> listarLogins() {
        List<Login> lista = new ArrayList<>();
        String sql = "SELECT * FROM Login";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Login login = new Login();
                login.setIdIdentificacion(rs.getInt("idIdentificacion"));
                login.setRol(rs.getString("rol"));
                login.setContrasena(rs.getString("contrasena"));
                lista.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void agregarLogin(Login login) {
        String sql = "INSERT INTO Login (idIdentificacion, rol, contrasena) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, login.getIdIdentificacion());
            stmt.setString(2, login.getRol());
            stmt.setString(3, login.getContrasena());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Error: el ID ya está registrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        }
    }

    public void eliminarLogin(int idIdentificacion) {
        String sql = "DELETE FROM Login WHERE idIdentificacion = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIdentificacion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarLogin(Login login) {
        String sql = "UPDATE Login SET rol = ?, contrasena = ? WHERE idIdentificacion = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login.getRol());
            stmt.setString(2, login.getContrasena());
            stmt.setInt(3, login.getIdIdentificacion());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Login buscarLoginPorId(int id) {
        String sql = "SELECT * FROM Login WHERE idIdentificacion = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setIdIdentificacion(rs.getInt("idIdentificacion"));
                    login.setRol(rs.getString("rol"));
                    login.setContrasena(rs.getString("contrasena"));
                    return login;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
