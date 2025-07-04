package controlador;

import Modelo.DetalleVenta;
import conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DetalleVentaControlador {
	
	public List<DetalleVenta> listarDetalles() {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM DetalleVenta";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetalleVenta d = new DetalleVenta();
                d.setIdDetalle(rs.getInt("idDetalle"));
                d.setIdProducto(rs.getInt("idProducto"));
                d.setIdEmpleado(rs.getInt("idEmpleado"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                d.setFechaVenta(rs.getDate("fecha_venta"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void agregarDetalle(DetalleVenta detalle) {
    	if (!existeProducto(detalle.getIdProducto())) {
            JOptionPane.showMessageDialog(null, "Error: El producto con ID " + detalle.getIdProducto() + " no existe.");
            return;
        }
        if (!existeEmpleado(detalle.getIdEmpleado())) {
            JOptionPane.showMessageDialog(null, "Error: El empleado con ID " + detalle.getIdEmpleado() + " no existe.");
            return;
        }

        if (!descontarStock(detalle.getIdProducto(), detalle.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error: Stock insuficiente para el producto con ID " + detalle.getIdProducto());
            return;
        }

        String sql = "INSERT INTO DetalleVenta (idDetalle, idProducto, idEmpleado, cantidad, precio_unitario, fecha_venta) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdDetalle());
            stmt.setInt(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getIdEmpleado());
            stmt.setInt(4, detalle.getCantidad());
            stmt.setDouble(5, detalle.getPrecioUnitario());
            stmt.setDate(6, detalle.getFechaVenta());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Detalle de venta agregado y stock actualizado correctamente.");
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Error: El ID del detalle ya existe.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar detalle: " + e.getMessage());
        }
    }

    public void eliminarDetalle(int idDetalle) {
        String sql = "DELETE FROM DetalleVenta WHERE idDetalle = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarDetalle(DetalleVenta detalle) {
    	
    	String sql = "UPDATE DetalleVenta SET idProducto = ?, idEmpleado = ?, cantidad = ?, precio_unitario = ?, fecha_venta = ? WHERE idDetalle = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdProducto());
            stmt.setInt(2, detalle.getIdEmpleado());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getPrecioUnitario());
            stmt.setDate(5, detalle.getFechaVenta());
            stmt.setInt(6, detalle.getIdDetalle());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean existeProducto(int idProducto) {
        String sql = "SELECT 1 FROM Productos WHERE idProducto = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // true si encontró el producto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeEmpleado(int idEmpleado) {
        String sql = "SELECT 1 FROM Empleados WHERE idEmpleado = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // true si encontró el empleado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public DetalleVenta buscarPorId(int id) {
        String sql = "SELECT * FROM DetalleVenta WHERE idDetalle = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DetalleVenta d = new DetalleVenta();
                    d.setIdDetalle(rs.getInt("idDetalle"));
                    d.setIdProducto(rs.getInt("idProducto"));
                    d.setIdEmpleado(rs.getInt("idEmpleado"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    d.setFechaVenta(rs.getDate("fecha_venta"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private boolean descontarStock(int idProducto, int cantidadVendida) {
        String sql = "UPDATE Productos SET Stock = Stock - ? WHERE idProducto = ? AND Stock >= ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadVendida);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadVendida);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public double obtenerPrecioProducto(int idProducto) throws SQLException {
        String sql = "SELECT Precio FROM Productos WHERE idProducto = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Precio");
                } else {
                    throw new SQLException("Producto no encontrado.");
                }
            }
        }
    }
}
