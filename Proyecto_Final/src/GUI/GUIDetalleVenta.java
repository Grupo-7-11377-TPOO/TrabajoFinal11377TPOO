package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.DetalleVenta;
import Modelo.Empleados;
import Modelo.Producto;
import controlador.DetalleVentaControlador;
import controlador.EmpleadoControlador;
import controlador.ProductoControlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

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
	private DetalleVentaControlador controlador = new DetalleVentaControlador();
	private ProductoControlador controlador1;
	private EmpleadoControlador controlador2;
	private JScrollPane tableProductos;
	private JScrollPane tableEmpleados;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDetalleVenta frame = new GUIDetalleVenta(new ProductoControlador(), new EmpleadoControlador());
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
	public GUIDetalleVenta(ProductoControlador controlador, EmpleadoControlador controlador2) {
		this.controlador1 = controlador;
		this.controlador2 = controlador2;
		setTitle("Ventana de venta");
		setBounds(100, 100, 539, 430);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		{
			lblNewLabel = new JLabel("idVenta:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel);
		}
		{
			txtIdDetalle = new JTextField();
			txtIdDetalle.setEditable(false);
			txtIdDetalle.setText("Se auto incrementa");
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
			txtIdProducto.addFocusListener(new java.awt.event.FocusAdapter() {
			    @Override
			    public void focusLost(java.awt.event.FocusEvent e) {
			        autocompletarPrecio();
			    }
			});
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
			txtFecha.setText(String.valueOf(LocalDate.now()));
			txtFecha.setEditable(false);
		}
		{
			btnAgregar = new JButton("Agregar");
			btnAgregar.addActionListener(this);
			{
				tableProductos = new JScrollPane();
				getContentPane().add(tableProductos);
				{
					table = new JTable();
					tableProductos.setViewportView(table);
				}
				cargarDatosEnTabla();
			}
			{
				tableEmpleados = new JScrollPane();
				getContentPane().add(tableEmpleados);
				{
					table_1 = new JTable();
					tableEmpleados.setViewportView(table_1);
				}
				cargarDatosEnTabla2();
			}
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
	        if (txtIdDetalle.getText().isEmpty() || txtIdProducto.getText().isEmpty()
	                || txtIdEmpleado.getText().isEmpty() || txtCantidad.getText().isEmpty()
	                || txtPrecioUnitario.getText().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        int idProducto = Integer.parseInt(txtIdProducto.getText());
	        int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
	        int cantidad = Integer.parseInt(txtCantidad.getText());
	        double precio = Double.parseDouble(txtPrecioUnitario.getText());

	        if (!controlador.existeProducto(idProducto)) {
	            JOptionPane.showMessageDialog(this, "Error: El ID de producto no existe.");
	            return;
	        }

	        if (!controlador.existeEmpleado(idEmpleado)) {
	            JOptionPane.showMessageDialog(this, "Error: El ID de empleado no existe.");
	            return;
	        }

	        if (idProducto < 0 || idEmpleado < 0 || cantidad < 0 || precio < 0) {
	            JOptionPane.showMessageDialog(this, "No se permiten valores negativos.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        // Asignar fecha actual
	        Date fecha = new Date(System.currentTimeMillis());

	        DetalleVenta detalle = new DetalleVenta();
	        detalle.setIdProducto(idProducto);
	        detalle.setIdEmpleado(idEmpleado);
	        detalle.setCantidad(cantidad);
	        detalle.setPrecioUnitario(precio);
	        detalle.setFechaVenta(fecha);

	        controlador.agregarDetalle(detalle);
	        limpiarCampos();
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Verifica que los campos numéricos (ID, cantidad, precio) sean válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    }
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
		dispose();
	}
	private void limpiarCampos() {
        txtIdProducto.setText("");
        txtIdEmpleado.setText("");
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        txtFecha.setText("");
    }
	private void autocompletarPrecio() {
	    try {
	        int idProducto = Integer.parseInt(txtIdProducto.getText());
	        double precio = controlador.obtenerPrecioProducto(idProducto);  // Este método va en tu controlador
	        txtPrecioUnitario.setText(String.valueOf(precio));
	    } catch (NumberFormatException ex) {
	        //JOptionPane.showMessageDialog(this, "El ID del producto debe ser numérico.");
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error al obtener el precio: " + ex.getMessage());
	    }
	}
	private void cargarDatosEnTabla() {
		List<Producto> productos = controlador1.listarProductos();

		String[] columnas = { "ID", "Nombre"};
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		for (Producto p : productos) {
			Object[] fila = { p.getCodigo(), p.getNombre()};
			modelo.addRow(fila); 
		}

		table.setModel(modelo);
	}
	private void cargarDatosEnTabla2() {
		List<Empleados> empleados = controlador2.listarEmpleados();

		String[] columnas = { "ID", "Nombre"};
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

		for (Empleados e : empleados) {
			Object[] fila = { e.getIdEmpleado(), e.getNombre()};
			modelo.addRow(fila); 
		}

		table_1.setModel(modelo);
	}
}
