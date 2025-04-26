package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ProductoControlador;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.Producto;
import java.util.List;

public class GUITablaProductos extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private ProductoControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITablaProductos frame = new GUITablaProductos(new ProductoControlador());
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
	public GUITablaProductos(ProductoControlador controlador) {
		this.controlador = controlador;
		setTitle("Visualizando");
		setBounds(100, 100, 539, 342);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				btnNewButton = new JButton("Editar");
				btnNewButton.addActionListener(this);
				panel.add(btnNewButton);
			}
			{
				btnNewButton_1 = new JButton("Eliminar");
				btnNewButton_1.addActionListener(this);
				panel.add(btnNewButton_1);
			}
			{
				btnNewButton_2 = new JButton("Actualizar");
				btnNewButton_2.addActionListener(this);
				panel.add(btnNewButton_2);
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
		if (e.getSource() == btnNewButton_2) {
			do_btnNewButton_2_actionPerformed(e);
		}
		if (e.getSource() == btnNewButton_1) {
			do_btnNewButton_1_actionPerformed(e);
		}
		if (e.getSource() == btnNewButton) {
			do_btnNewButton_actionPerformed(e);
		}
	}
	//Editar
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		
	}
	//Eliminar
	protected void do_btnNewButton_1_actionPerformed(ActionEvent e) {
		
	}
	//Actualizar
	protected void do_btnNewButton_2_actionPerformed(ActionEvent e) {
		cargarDatosEnTabla();
	}
	private void cargarDatosEnTabla() {
		List<Producto> productos = controlador.listarProductos();

		String[] columnas = { "ID", "Nombre", "Precio" ,"Tipo","Stock"};
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		for (Producto p : productos) {
			Object[] fila = { p.getCodigo(), p.getNombre(), p.getPrecio(), p.getTipo(), p.getStock()};
			modelo.addRow(fila);
		}

		table.setModel(modelo);
	}
}
