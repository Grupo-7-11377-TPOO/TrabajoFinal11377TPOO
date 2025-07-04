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
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JTextField txtCantidad;
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
		setTitle("Ventana de ingreso de productos a inventario");
		setBounds(100, 100, 539, 342);
		{
			getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
			{
				lblNewLabel = new JLabel("Id del Producto:");
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
				lblNewLabel_7 = new JLabel("Precio por Unidad:");
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
				lblNewLabel_6 = new JLabel("Cantidad:");
				lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_6);
			}
			{
				txtCantidad = new JTextField();
				txtCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(txtCantidad);
				txtCantidad.setColumns(10);
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
        // Validaciones
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
            txtPrecio.getText().trim().isEmpty() || txtCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        double precio;
        int stock;

        try {
            id = Integer.parseInt(txtCodigo.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "Precio no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stock = Integer.parseInt(txtCantidad.getText().trim());
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stock debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();

        Producto producto = new Producto();
        producto.setCodigo(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);

        controlador.agregarProducto(producto);

    }
}
