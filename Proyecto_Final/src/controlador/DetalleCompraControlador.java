package controlador;
import conexion.ConexionBD;
import Modelo.DetalleCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class DetalleCompraControlador {
	public List<DetalleCompra> listarDetalleCompras() {
        List<DetalleCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM DetalleCompra";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetalleCompra d = new DetalleCompra();
                d.setIdDetalle(rs.getInt("idDetalle"));
                d.setIdProducto(rs.getInt("idProducto"));
                d.setIdProovedor(rs.getInt("idProovedor"));
                d.setCantidad(rs.getInt("Cantidad"));
                d.setPrecioCompra(rs.getDouble("PrecioCompra"));
                d.setFechaCompra(rs.getDate("fecha_compra"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void agregarDetalleCompra(DetalleCompra d) {
        String sql = "INSERT INTO DetalleCompra (idProducto, idProovedor, Cantidad, PrecioCompra, fecha_compra) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getIdProducto());
            stmt.setInt(2, d.getIdProovedor());
            stmt.setInt(3, d.getCantidad());
            stmt.setDouble(4, d.getPrecioCompra());
            stmt.setDate(5, d.getFechaCompra());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Detalle de compra registrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de compra: " + e.getMessage());
        }
    }
}
