package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.DetalleVenta;
import controlador.DetalleVentaControlador;
import java.sql.Date;

public class GUIDetalleVenta extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JTextField txtIdDetalle;
	private JLabel lblNewLabel_1;
	private JTextField txtIdProducto;
	private JLabel lblNewLabel_2;
	private JTextField txtIdEmpleado;
	private JLabel lblNewLabel_3;
	private JTextField txtCantidad;
	private JLabel lblNewLabel_4;
	private JTextField txtPrecioUnitario;
	private JButton btnAgregar;
	private JButton btnSalir;
	private JLabel lblNewLabel_5;
	private JTextField txtFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDetalleVenta frame = new GUIDetalleVenta();
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
	public GUIDetalleVenta() {
		setBounds(100, 100, 539, 342);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		{
			lblNewLabel = new JLabel("idVenta:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel);
		}
		{
			txtIdDetalle = new JTextField();
			txtIdDetalle.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtIdDetalle);
			txtIdDetalle.setColumns(10);
		}
		{
			lblNewLabel_1 = new JLabel("idProducto:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_1);
		}
		{
			txtIdProducto = new JTextField();
			txtIdProducto.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtIdProducto);
			txtIdProducto.setColumns(10);
		}
		{
			lblNewLabel_2 = new JLabel("idEmpleado:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_2);
		}
		{
			txtIdEmpleado = new JTextField();
			txtIdEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtIdEmpleado);
			txtIdEmpleado.setColumns(10);
		}
		{
			lblNewLabel_3 = new JLabel("Cantidad:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_3);
		}
		{
			txtCantidad = new JTextField();
			txtCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtCantidad);
			txtCantidad.setColumns(10);
		}
		{
			lblNewLabel_4 = new JLabel("Precio Unitario:");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_4);
		}
		{
			txtPrecioUnitario = new JTextField();
			txtPrecioUnitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtPrecioUnitario);
			txtPrecioUnitario.setColumns(10);
		}
		{
			lblNewLabel_5 = new JLabel("Fecha:");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_5);
		}
		{
			txtFecha = new JTextField();
			txtFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtFecha);
			txtFecha.setColumns(10);
		}
		{
			btnAgregar = new JButton("Agregar");
			btnAgregar.addActionListener(this);
			btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 14));
			getContentPane().add(btnAgregar);
		}
		{
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(this);
			btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
			getContentPane().add(btnSalir);
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalir) {
			do_btnSalir_actionPerformed(e);
		}
		if (e.getSource() == btnAgregar) {
			do_btnAgregar_actionPerformed(e);
		}
	}
	protected void do_btnAgregar_actionPerformed(ActionEvent e) {
		try {
	        // Validar campos vacíos
	        if (txtIdDetalle.getText().isEmpty() || txtIdProducto.getText().isEmpty() || txtIdEmpleado.getText().isEmpty()
	                || txtCantidad.getText().isEmpty() || txtPrecioUnitario.getText().isEmpty() || txtFecha.getText().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        int idDetalle = Integer.parseInt(txtIdDetalle.getText());
	        int idProducto = Integer.parseInt(txtIdProducto.getText());
	        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
	        int cantidad = Integer.parseInt(txtCantidad.getText());
	        double precio = Double.parseDouble(txtPrecioUnitario.getText());

	        // Validar valores negativos
	        if (idDetalle < 0 || idProducto < 0 || idEmpleado < 0 || cantidad < 0 || precio < 0) {
	            JOptionPane.showMessageDialog(this, "No se permiten valores negativos.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        Date fecha = Date.valueOf(txtFecha.getText()); // formato yyyy-MM-dd

	        DetalleVenta detalle = new DetalleVenta();
	        detalle.setIdDetalle(idDetalle);
	        detalle.setIdProducto(idProducto);
	        detalle.setIdEmpleado(idEmpleado);
	        detalle.setCantidad(cantidad);
	        detalle.setPrecioUnitario(precio);
	        detalle.setFechaVenta(fecha);

	        DetalleVentaControlador controlador = new DetalleVentaControlador();
	        controlador.agregarDetalle(detalle);

	        JOptionPane.showMessageDialog(this, "Detalle de venta agregado exitosamente.");
	        limpiarCampos();
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Verifica que los campos numéricos (ID, cantidad, precio) sean válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    } catch (IllegalArgumentException ex) {
	        JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd.", "Error de fecha", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    }
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
		dispose();
	}
	private void limpiarCampos() {
        txtIdDetalle.setText("");
        txtIdProducto.setText("");
        txtIdEmpleado.setText("");
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        txtFecha.setText("");
    }
}
