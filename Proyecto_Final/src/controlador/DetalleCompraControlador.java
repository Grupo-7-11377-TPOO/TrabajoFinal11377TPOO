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
        if (!existeProducto(d.getIdProducto())) {
            JOptionPane.showMessageDialog(null, "Error: El producto con ID " + d.getIdProducto() + " no existe.");
            return;
        }
        if (!existeProovedor(d.getIdProovedor())) {
            JOptionPane.showMessageDialog(null, "Error: El proveedor con ID " + d.getIdProovedor() + " no existe.");
            return;
        }

        if (!aumentarStock(d.getIdProducto(), d.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error: No se pudo aumentar el stock para el producto " + d.getIdProducto());
            return;
        }

        String sql = "INSERT INTO DetalleCompra (idProducto, idProovedor, Cantidad, PrecioCompra, fecha_compra) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getIdProducto());
            stmt.setInt(2, d.getIdProovedor());
            stmt.setInt(3, d.getCantidad());
            stmt.setDouble(4, d.getPrecioCompra());
            stmt.setDate(5, d.getFechaCompra());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Detalle de compra registrado y stock actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de compra: " + e.getMessage());
        }
    }

    public void actualizarDetalleCompra(DetalleCompra detalle) {
    	if (!existeProducto(detalle.getIdProducto())) {
            JOptionPane.showMessageDialog(null, "Error: El producto con ID " + detalle.getIdProducto() + " no existe.");
            return;
        }
        if (!existeProovedor(detalle.getIdProovedor())) {
            JOptionPane.showMessageDialog(null, "Error: El proveedor con ID " + detalle.getIdProovedor() + " no existe.");
            return;
        }
    	DetalleCompra original = buscarPorId(detalle.getIdDetalle());

        if (original == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el detalle original.");
            return;
        }
        // Paso 2: Restar la cantidad anterior del stock
        if (!ajustarStock(detalle.getIdProducto(), -original.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error al restar stock anterior.");
            return;
        }

        // Paso 3: Sumar la nueva cantidad al stock
        if (!ajustarStock(detalle.getIdProducto(), detalle.getCantidad())) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock con nueva cantidad.");
            return;
        }
    	
    	String sql = "UPDATE DetalleCompra SET idProducto = ?, idProovedor = ?, Cantidad = ?, PrecioCompra = ?, fecha_compra = ? WHERE idDetalle = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdProducto());
            stmt.setInt(2, detalle.getIdProovedor());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getPrecioCompra());
            stmt.setDate(5, detalle.getFechaCompra());
            stmt.setInt(6, detalle.getIdDetalle());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Detalle de compra actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar detalle de compra: " + e.getMessage());
        }
    }

    public boolean existeProducto(int idProducto) {
        String sql = "SELECT 1 FROM Productos WHERE idProducto = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeProovedor(int idProovedor) {
        String sql = "SELECT 1 FROM Proovedor WHERE idProovedor = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProovedor);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean aumentarStock(int idProducto, int cantidadComprada) {
        String sql = "UPDATE Productos SET Stock = Stock + ? WHERE idProducto = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadComprada);
            stmt.setInt(2, idProducto);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean ajustarStock(int idProducto, int cantidad) {
        String sql = "UPDATE Productos SET Stock = Stock + ? WHERE idProducto = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public DetalleCompra buscarPorId(int id) {
        String sql = "SELECT * FROM DetalleCompra WHERE idDetalle = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DetalleCompra d = new DetalleCompra();
                    d.setIdDetalle(rs.getInt("idDetalle"));
                    d.setIdProducto(rs.getInt("idProducto"));
                    d.setIdProovedor(rs.getInt("idProovedor"));
                    d.setCantidad(rs.getInt("Cantidad"));
                    d.setPrecioCompra(rs.getDouble("PrecioCompra"));
                    d.setFechaCompra(rs.getDate("fecha_compra"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void eliminarDetalleCompra(int idDetalle) {
        String sql = "DELETE FROM DetalleCompra WHERE idDetalle = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double obtenerTotalComprasPorFecha(String tipo, int valor) {
    	String sql = "";
    	if (tipo.equals("MES")) {
    		sql = "SELECT SUM(PrecioCompra) AS total FROM DetalleCompra WHERE MONTH(fecha_compra) = ?";
    	} else if (tipo.equals("ANIO")) {
    		sql = "SELECT SUM(PrecioCompra) AS total FROM DetalleCompra WHERE YEAR(fecha_compra) = ?";
    	} else {
    		return 0;
    	}

    	try (Connection conn = ConexionBD.getConexion();
    	     PreparedStatement stmt = conn.prepareStatement(sql)) {

    		stmt.setInt(1, valor);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			return rs.getDouble("total");
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
}
