package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JTextField;

import Modelo.Producto;
import controlador.ProductoControlador;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class GUIFormulario extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ProductoControlador controlador;
	private JButton btnSalir;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JTextField txtStock;
	private JComboBox cmbTipo;
	private JButton btnGuardar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoControlador controlador = new ProductoControlador();
					GUIFormulario frame = new GUIFormulario(controlador);
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
	
	public GUIFormulario(ProductoControlador controlador) {
		this.controlador = controlador;
		setTitle("Abierto");
		setBounds(100, 100, 539, 342);
		{
			getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
			{
				lblNewLabel = new JLabel("Código del producto:");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel);
			}
			{
				txtCodigo = new JTextField();
				txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(txtCodigo);
				txtCodigo.setColumns(10);
			}
			{
				lblNewLabel_3 = new JLabel("Nombre del producto:");
				lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_3);
			}
			{
				txtNombre = new JTextField();
				txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(txtNombre);
				txtNombre.setColumns(10);
			}
			{
				lblNewLabel_5 = new JLabel("Tipo de Licor:");
				lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_5);
			}
			{
				cmbTipo = new JComboBox();
				cmbTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
				cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"Ron", "Vodka", "Whisky", "Cerveza de trigo"}));
				getContentPane().add(cmbTipo);
			}
			{
				lblNewLabel_7 = new JLabel("Precio:");
				lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_7);
			}
			{
				txtPrecio = new JTextField();
				txtPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(txtPrecio);
				txtPrecio.setColumns(10);
			}
			{
				lblNewLabel_6 = new JLabel("Stock disponible:");
				lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_6);
			}
			{
				txtStock = new JTextField();
				txtStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(txtStock);
				txtStock.setColumns(10);
			}
		}
		btnSalir = new JButton("Cancelar");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalir.addActionListener(this);
		{
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(this);
			btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));
			getContentPane().add(btnGuardar);
		}
		getContentPane().add(btnSalir);

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGuardar) {
			do_btnGuardar_actionPerformed(e);
		}
		if (e.getSource() == btnSalir) {
			do_btnSalir_actionPerformed(e);
		}
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
		dispose();
	}
	protected void do_btnGuardar_actionPerformed(ActionEvent e) {
		String codigo = txtCodigo.getText().trim();
		String nombre = txtNombre.getText().trim();
		String tipo = (String) cmbTipo.getSelectedItem();
		try {
			double precio = Double.parseDouble(txtPrecio.getText().trim());
			int stock = Integer.parseInt(txtStock.getText().trim());

			Producto producto = new Producto(codigo, nombre, tipo, precio, stock);

			controlador.agregarProducto(producto);

			JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");

			txtCodigo.setText("");
			txtNombre.setText("");
			txtPrecio.setText("");
			txtStock.setText("");
			cmbTipo.setSelectedIndex(0);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
		}
    }
}
