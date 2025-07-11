package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.DetalleCompra;
import Modelo.Producto;
import controlador.DetalleCompraControlador;
import controlador.ProductoControlador;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GUIDetalleCompra extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DetalleCompraControlador controlador;
	private JDesktopPane desktopPane;
	private final JPanel panel = new JPanel();
	private JButton btnAgregarCompra;
	private JButton btnEditarCompra;
	private JButton btnListaProovedores;
	private JLabel lblNewLabel;
	private JComboBox cmbFECHAS;
	private JTextField txtresultadoCompra;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDetalleCompra frame = new GUIDetalleCompra();
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
	public GUIDetalleCompra() {
		setTitle("Ventana de reabastecimiento de inventario");
		this.controlador = new DetalleCompraControlador();
		setBounds(100, 100, 539, 430);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		desktopPane = new JDesktopPane();
		scrollPane.setColumnHeaderView(desktopPane);
		
		btnAgregarCompra = new JButton("Añadir");
		btnAgregarCompra.addActionListener(this);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.add(btnAgregarCompra);
		
		btnEditarCompra = new JButton("Editar");
		btnEditarCompra.addActionListener(this);
		panel.add(btnEditarCompra);
		
		btnListaProovedores = new JButton("Lista Proovedores");
		btnListaProovedores.addActionListener(this);
		panel.add(btnListaProovedores);
		
		lblNewLabel = new JLabel("Total");
		panel.add(lblNewLabel);
		
		cmbFECHAS = new JComboBox();
		cmbFECHAS.setModel(new DefaultComboBoxModel(new String[] {"Mensual:", "Anual:"}));
		panel.add(cmbFECHAS);
		cmbFECHAS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					calcularTotalCompra();
					}
				}
			});
		txtresultadoCompra = new JTextField();
		txtresultadoCompra.setText("-Resultado-");
		txtresultadoCompra.setEditable(false);
		txtresultadoCompra.setColumns(10);
		panel.add(txtresultadoCompra);
		cargarTabla();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnListaProovedores) {
			do_btnListaProovedores_actionPerformed(e);
		}
		if (e.getSource() == btnEditarCompra) {
			do_btnEditarCompra_actionPerformed(e);
		}
		if (e.getSource() == btnAgregarCompra) {
			do_btnAgregarCompra_actionPerformed(e);
		}
		
	}
	private void cargarTabla() {
		List<DetalleCompra> lista = controlador.listarDetalleCompras();
		String[] columnas = { "ID Detalle", "ID Producto", "ID Proovedor", "Cantidad", "Precio Compra", "Fecha Compra" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		for (DetalleCompra d : lista) {
			Object[] fila = {
				d.getIdDetalle(),
				d.getIdProducto(),
				d.getIdProovedor(),
				d.getCantidad(),
				d.getPrecioCompra(),
				d.getFechaCompra()
			};
			modelo.addRow(fila);
		}
		table.setModel(modelo);
	}
	private void calcularTotalCompra() {
		String opcion = cmbFECHAS.getSelectedItem().toString();
		double total = 0;

		try {
			if (opcion.equals("Mensual:")) {
				String mesStr = JOptionPane.showInputDialog(this, "Ingrese el número del mes (1-12):");
				if (mesStr == null || !mesStr.matches("\\d+")) return;
				int mes = Integer.parseInt(mesStr);

				if (mes < 1 || mes > 12) {
					JOptionPane.showMessageDialog(this, "Mes inválido.");
					return;
				}

				total = controlador.obtenerTotalComprasPorFecha("MES", mes);

			} else if (opcion.equals("Anual:")) {
				String anioStr = JOptionPane.showInputDialog(this, "Ingrese el año:");
				if (anioStr == null || !anioStr.matches("\\d+")) return;
				int anio = Integer.parseInt(anioStr);
				total = controlador.obtenerTotalComprasPorFecha("ANIO", anio);
			}

			txtresultadoCompra.setText(String.format("S/ %.2f", total));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al calcular el total: " + ex.getMessage());
		}
	}
	private void centrarInternalFrame(JInternalFrame frame) {
	    int x = (desktopPane.getWidth() - frame.getWidth()) / 2;
	    int y = (desktopPane.getHeight() - frame.getHeight()) / 2;
	    frame.setLocation(x, y);
	}
	protected void do_btnAgregarCompra_actionPerformed(ActionEvent e) {
		try {
	        // Tabla de productos
	        JTable tablaProductos = new JTable(controlador.obtenerModeloProductos());
	        JScrollPane scrollProd = new JScrollPane(tablaProductos);
	        scrollProd.setPreferredSize(new Dimension(400, 200));
	        int opcionProd = JOptionPane.showConfirmDialog(this, scrollProd, "Selecciona un producto", JOptionPane.OK_CANCEL_OPTION);
	        if (opcionProd != JOptionPane.OK_OPTION || tablaProductos.getSelectedRow() == -1) return;
	        int idProducto = (int) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0);

	        // Tabla de proveedores
	        JTable tablaProveedores = new JTable(controlador.obtenerModeloProovedores());
	        JScrollPane scrollProv = new JScrollPane(tablaProveedores);
	        scrollProv.setPreferredSize(new Dimension(400, 200));
	        int opcionProv = JOptionPane.showConfirmDialog(this, scrollProv, "Selecciona un proveedor", JOptionPane.OK_CANCEL_OPTION);
	        if (opcionProv != JOptionPane.OK_OPTION || tablaProveedores.getSelectedRow() == -1) return;
	        int idProovedor = (int) tablaProveedores.getValueAt(tablaProveedores.getSelectedRow(), 0);

	        // Resto de datos
	        String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad:");
	        String precioStr = JOptionPane.showInputDialog(this, "Precio de Compra:");

	        if (cantidadStr == null || precioStr == null) return;
	        if (!cantidadStr.matches("\\d+")) {
	            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero positivo.");
	            return;
	        }
	        if (!precioStr.matches("^\\d+(\\.\\d+)?$")) {
	            JOptionPane.showMessageDialog(this, "El precio debe ser un número decimal positivo.");
	            return;
	        }

	        int cantidad = Integer.parseInt(cantidadStr);
	        double precio = Double.parseDouble(precioStr);

	        if (cantidad <= 0 || precio <= 0) {
	            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser mayores que cero.");
	            return;
	        }

	        // Crear y registrar detalle
	        DetalleCompra d = new DetalleCompra();
	        d.setIdProducto(idProducto);
	        d.setIdProovedor(idProovedor);
	        d.setCantidad(cantidad);
	        d.setPrecioCompra(precio);
	        d.setFechaCompra(Date.valueOf(LocalDate.now()));

	        controlador.agregarDetalleCompra(d);
	        cargarTabla();
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	    }
	}
	protected void do_btnEditarCompra_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
		if (fila != -1) {
			try {
				int idDetalle = Integer.parseInt(table.getValueAt(fila, 0).toString());
				DetalleCompra d = new DetalleCompra();
				d.setIdDetalle(idDetalle);

				String idProductoStr = JOptionPane.showInputDialog(this, "Nuevo ID Producto:", table.getValueAt(fila, 1));
				String idProovedorStr = JOptionPane.showInputDialog(this, "Nuevo ID Proovedor:", table.getValueAt(fila, 2));
				String cantidadStr = JOptionPane.showInputDialog(this, "Nueva Cantidad:", table.getValueAt(fila, 3));
				String precioStr = JOptionPane.showInputDialog(this, "Nuevo Precio:", table.getValueAt(fila, 4));

				if (idProductoStr == null || idProovedorStr == null || cantidadStr == null || precioStr == null) return;
				// Validación de números enteros positivos
				if (!idProductoStr.matches("\\d+") || !idProovedorStr.matches("\\d+") || !cantidadStr.matches("\\d+")) {
					JOptionPane.showMessageDialog(this, "ID Producto, ID Proveedor y Cantidad deben ser números enteros positivos.");
					return;
				}
				// Validación de número decimal positivo
				if (!precioStr.matches("^\\d+(\\.\\d+)?$")) {
					JOptionPane.showMessageDialog(this, "El precio debe ser un número decimal positivo.");
					return;
				}
				int idProducto = Integer.parseInt(idProductoStr);
				int idProovedor = Integer.parseInt(idProovedorStr);
				int cantidad = Integer.parseInt(cantidadStr);
				double precio = Double.parseDouble(precioStr);
				if (cantidad <= 0 || precio <= 0) {
					JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser mayores que cero.");
					return;
				}
				d.setIdProducto(idProducto);
				d.setIdProovedor(idProovedor);
				d.setCantidad(cantidad);
				d.setPrecioCompra(precio);
				d.setFechaCompra(Date.valueOf(LocalDate.now())); // actualiza con la fecha actual
				
				controlador.actualizarDetalleCompra(d);
				JOptionPane.showMessageDialog(this, "Detalle actualizado correctamente.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(this, "Seleccione una fila para editar.");
		}
	}
	protected void do_btnListaProovedores_actionPerformed(ActionEvent e) {
		GUIProovedores ventana = new GUIProovedores(new controlador.ProovedorControlador());
		getParent().add(ventana); // asegura que se agrega al mismo desktopPane
		ventana.setVisible(true);
		//centrarInternalFrame(ventana);
	}
}
