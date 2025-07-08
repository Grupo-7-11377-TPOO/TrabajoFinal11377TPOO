package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import controlador.DetalleVentaControlador;
import Modelo.DetalleVenta;

public class GUITablaDetalleVenta extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnActualizar;
	private JTable table; 
	private DetalleVentaControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetalleVentaControlador controlador = new DetalleVentaControlador();
	                GUITablaDetalleVenta frame = new GUITablaDetalleVenta(controlador);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUITablaDetalleVenta(DetalleVentaControlador controlador) {
		this.controlador = controlador;
		setTitle("Historial de ventas");
		setBounds(100, 100, 539, 430);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				btnEditar = new JButton("Editar");
				btnEditar.addActionListener(this);
				panel.add(btnEditar);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(this);
				panel.add(btnEliminar);
			}
			{
				btnActualizar = new JButton("Actualizar");
				btnActualizar.addActionListener(this);
				panel.add(btnActualizar);
			}
		}
		{
			scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		cargarDatosEnTabla();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnActualizar) {
			do_btnActualizar_actionPerformed(e);
		}
		if (e.getSource() == btnEliminar) {
			do_btnEliminar_actionPerformed(e);
		}
		if (e.getSource() == btnEditar) {
			do_btnEditar_actionPerformed(e);
		}
	}

	private void cargarDatosEnTabla() {
        List<DetalleVenta> detalles = controlador.listarDetalles();
        String[] columnas = { "ID", "Producto ID", "Empleado ID", "Cantidad", "Precio Unitario", "Fecha Venta" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (DetalleVenta d : detalles) {
            Object[] fila = {
                d.getIdDetalle(),
                d.getIdProducto(),
                d.getIdEmpleado(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getFechaVenta()
            };
            modelo.addRow(fila);
        }
        table.setModel(modelo);
    }
	protected void do_btnEditar_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
	    if (fila != -1) {
	        int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
	        DetalleVenta detalle = controlador.buscarPorId(id);
	        if (detalle != null) {
	            try {
	                String nuevaCantidadStr = JOptionPane.showInputDialog(this, "Nueva cantidad:", detalle.getCantidad());
	                String nuevoPrecioStr = JOptionPane.showInputDialog(this, "Nuevo precio unitario:", detalle.getPrecioUnitario());
	                String nuevoIdProductoStr = JOptionPane.showInputDialog(this, "Nuevo ID de Producto:", detalle.getIdProducto());
	                String nuevoIdEmpleadoStr = JOptionPane.showInputDialog(this, "Nuevo ID de Empleado:", detalle.getIdEmpleado());

	                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
	                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
	                int nuevoIdProducto = Integer.parseInt(nuevoIdProductoStr);
	                int nuevoIdEmpleado = Integer.parseInt(nuevoIdEmpleadoStr);

	                // Validación de cantidad y precio
	                if (nuevaCantidad <= 0) {
	                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
	                    return;
	                }
	                if (nuevoPrecio <= 0) {
	                    JOptionPane.showMessageDialog(this, "El precio unitario debe ser mayor que cero.");
	                    return;
	                }

	                // Validación de existencia de producto y empleado
	                if (!controlador.existeProducto(nuevoIdProducto)) {
	                    JOptionPane.showMessageDialog(this, "Error: El ID de producto no existe.");
	                    return;
	                }
	                if (!controlador.existeEmpleado(nuevoIdEmpleado)) {
	                    JOptionPane.showMessageDialog(this, "Error: El ID de empleado no existe.");
	                    return;
	                }

	                // Actualizar detalle
	                detalle.setCantidad(nuevaCantidad);
	                detalle.setPrecioUnitario(nuevoPrecio);
	                detalle.setIdProducto(nuevoIdProducto);
	                detalle.setIdEmpleado(nuevoIdEmpleado);

	                controlador.actualizarDetalle(detalle);
	                JOptionPane.showMessageDialog(this, "Detalle actualizado correctamente.");
	                cargarDatosEnTabla();

	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "Ingrese valores válidos para cantidad, precio o IDs.");
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Seleccione un detalle para editar.");
	    }
	}
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
        if (fila != -1) {
            int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
            DetalleVenta detalle = controlador.buscarPorId(id);
            if (detalle != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar el detalle?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controlador.eliminarDetalle(id);
                    JOptionPane.showMessageDialog(this, "Detalle eliminado.");
                    cargarDatosEnTabla();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un detalle para eliminar.");
        }
	}
	protected void do_btnActualizar_actionPerformed(ActionEvent e) {
		cargarDatosEnTabla();
	}
}
