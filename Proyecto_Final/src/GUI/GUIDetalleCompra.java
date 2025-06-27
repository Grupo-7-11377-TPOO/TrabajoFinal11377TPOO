package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.DetalleCompra;
import controlador.DetalleCompraControlador;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GUIDetalleCompra extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btnAgregarCompra;
	private JButton btnEditarCompra;
	private JButton btnListaProovedores;
	private DetalleCompraControlador controlador;
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
		this.controlador = new DetalleCompraControlador();
		setBounds(100, 100, 539, 342);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnAgregarCompra = new JButton("Agregar Compra");
		btnAgregarCompra.addActionListener(this);
		panel.add(btnAgregarCompra);
		
		btnEditarCompra = new JButton("Editar");
		btnEditarCompra.addActionListener(this);
		panel.add(btnEditarCompra);
		
		btnListaProovedores = new JButton("Lista Proovedores");
		btnListaProovedores.addActionListener(this);
		panel.add(btnListaProovedores);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
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
	protected void do_btnAgregarCompra_actionPerformed(ActionEvent e) {
		try {
			String idProductoStr = JOptionPane.showInputDialog(this, "ID Producto:");
			String idProovedorStr = JOptionPane.showInputDialog(this, "ID Proovedor:");
			String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad:");
			String precioStr = JOptionPane.showInputDialog(this, "Precio de Compra:");

			if (idProductoStr == null || idProovedorStr == null || cantidadStr == null || precioStr == null) return;

			int idProducto = Integer.parseInt(idProductoStr);
			int idProovedor = Integer.parseInt(idProovedorStr);
			int cantidad = Integer.parseInt(cantidadStr);
			double precio = Double.parseDouble(precioStr);

			DetalleCompra d = new DetalleCompra();
			d.setIdProducto(idProducto);
			d.setIdProovedor(idProovedor);
			d.setCantidad(cantidad);
			d.setPrecioCompra(precio);
			d.setFechaCompra(Date.valueOf(LocalDate.now()));

			controlador.agregarDetalleCompra(d);
			JOptionPane.showMessageDialog(this, "Detalle de compra agregado.");
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

				d.setIdProducto(Integer.parseInt(idProductoStr));
				d.setIdProovedor(Integer.parseInt(idProovedorStr));
				d.setCantidad(Integer.parseInt(cantidadStr));
				d.setPrecioCompra(Double.parseDouble(precioStr));
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
	//Este boton debe redireccionar al GUIProovedor al hacer click
	protected void do_btnListaProovedores_actionPerformed(ActionEvent e) {
		GUIProovedores ventana = new GUIProovedores(new controlador.ProovedorControlador());
		getParent().add(ventana); // asegura que se agrega al mismo desktopPane
		ventana.setVisible(true);
	}
}
