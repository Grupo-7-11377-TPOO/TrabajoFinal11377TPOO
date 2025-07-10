package controlador;

import Modelo.DetalleVenta;
import conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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

        String sql = "INSERT INTO DetalleVenta (idProducto, idEmpleado, cantidad, precio_unitario, fecha_venta) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setInt(1, detalle.getIdProducto());
        	stmt.setInt(2, detalle.getIdEmpleado());
        	stmt.setInt(3, detalle.getCantidad());
        	stmt.setDouble(4, detalle.getPrecioUnitario());
        	stmt.setDate(5, detalle.getFechaVenta());

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
    	DetalleVenta detalle = buscarPorId(idDetalle);
        if (detalle == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el detalle de venta a eliminar.");
            return;
        }

        // Revertir el stock
        if (!ajustarStock(detalle.getIdProducto(), detalle.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error al restaurar stock antes de eliminar.");
            return;
        }
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
    	if (!existeProducto(detalle.getIdProducto())) {
            JOptionPane.showMessageDialog(null, "Error: El producto con ID " + detalle.getIdProducto() + " no existe.");
            return;
        }
        if (!existeEmpleado(detalle.getIdEmpleado())) {
            JOptionPane.showMessageDialog(null, "Error: El empleado con ID " + detalle.getIdEmpleado() + " no existe.");
            return;
        }
    	// Paso 1: Obtener el detalle original antes de modificarlo
        DetalleVenta original = buscarPorId(detalle.getIdDetalle());

        if (original == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el detalle original.");
            return;
        }

        // Paso 2: Revertir el stock con la cantidad anterior
        if (!ajustarStock(detalle.getIdProducto(), original.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error al restaurar stock anterior.");
            return;
        }

        // Paso 3: Verificar si hay stock suficiente para nueva cantidad
        if (!descontarStock(detalle.getIdProducto(), detalle.getCantidad())) {
            // Si falla, devolver el stock original para no perder inventario
            ajustarStock(detalle.getIdProducto(), -original.getCantidad());
            JOptionPane.showMessageDialog(null, "Error: Stock insuficiente para el nuevo valor.");
            return;
        }
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
    private boolean ajustarStock(int idProducto, int cantidad) {
        String sql = "UPDATE Productos SET Stock = Stock + ? WHERE idProducto = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public double obtenerVentaTotalDiaria() {
        String sql = "SELECT SUM(cantidad * precio_unitario) AS total FROM DetalleVenta WHERE DATE(fecha_venta) = CURDATE()";
        return obtenerTotalDesdeSQL(sql);
    }

    public double obtenerVentaTotalMensual(int mes) {
        String sql = "SELECT SUM(cantidad * precio_unitario) AS total FROM DetalleVenta WHERE MONTH(fecha_venta) = ? AND YEAR(fecha_venta) = YEAR(CURDATE())";
        return obtenerTotalDesdeSQL(sql, mes);
    }

    public double obtenerVentaTotalAnual(int anio) {
        String sql = "SELECT SUM(cantidad * precio_unitario) AS total FROM DetalleVenta WHERE YEAR(fecha_venta) = ?";
        return obtenerTotalDesdeSQL(sql, anio);
    }

    private double obtenerTotalDesdeSQL(String sql, int... parametro) {
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (parametro.length > 0) {
                stmt.setInt(1, parametro[0]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public DefaultTableModel obtenerModeloEmpleados() {
        DefaultTableModel modelo = new DefaultTableModel(new String[] { "ID", "Nombre", "Apellido", "Teléfono" }, 0);
        String sql = "SELECT idEmpleado, nombre, apellido, telefono FROM Empleados";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getInt("idEmpleado"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("telefono")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelo;
    }
    public DefaultTableModel obtenerModeloProductos() {
        DefaultTableModel modelo = new DefaultTableModel(new String[] { "ID", "Nombre", "Precio", "Stock" }, 0);
        String sql = "SELECT idProducto, Nombre, Precio, Stock FROM Productos";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getInt("idProducto"),
                    rs.getString("Nombre"),
                    rs.getDouble("Precio"),
                    rs.getInt("Stock")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelo;
    }
}
