package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.Empleados;
import controlador.EmpleadoControlador;
import javax.swing.JOptionPane;

public class GUIEmpleados extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private EmpleadoControlador controlador;
	private JLabel lblNewLabel;
	private JTextField txtIdEmpleado;
	private JLabel lblNewLabel_1;
	private JTextField txtNombre;
	private JLabel lblNewLabel_2;
	private JTextField txtApellido;
	private JLabel lblNewLabel_3;
	private JTextField txtTelefono;
	private JButton btnGuardar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpleadoControlador controlador = new EmpleadoControlador();
					GUIEmpleados frame = new GUIEmpleados(controlador);
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
	public GUIEmpleados(EmpleadoControlador controlador) {
		this.controlador = controlador;
		setTitle("Ventana de ingreso de empleados");
		setBounds(100, 100, 539, 430);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		{
			lblNewLabel = new JLabel("Id Empleado:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel);
		}
		{
			txtIdEmpleado = new JTextField();
			txtIdEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtIdEmpleado);
			txtIdEmpleado.setColumns(10);
		}
		{
			lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_1);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			lblNewLabel_2 = new JLabel("Apellido:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_2);
		}
		{
			txtApellido = new JTextField();
			txtApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtApellido);
			txtApellido.setColumns(10);
		}
		{
			lblNewLabel_3 = new JLabel("Telefono:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(lblNewLabel_3);
		}
		{
			txtTelefono = new JTextField();
			txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(txtTelefono);
			txtTelefono.setColumns(10);
		}
		{
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(this);
			btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(btnGuardar);
		}
		{
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(this);
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			getContentPane().add(btnCancelar);
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			do_btnCancelar_actionPerformed(e);
		}
		if (e.getSource() == btnGuardar) {
			do_btnGuardar_actionPerformed(e);
		}
	}
	protected void do_btnGuardar_actionPerformed(ActionEvent e) {
		try {
	        String idStr = txtIdEmpleado.getText().trim();
	        String nombre = txtNombre.getText().trim();
	        String apellido = txtApellido.getText().trim();
	        String telefonoStr = txtTelefono.getText().trim();

	        if (idStr.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefonoStr.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
	            return;
	        }

	        int id = Integer.parseInt(idStr);
	        if (id <= 0) {
	            JOptionPane.showMessageDialog(this, "El ID debe ser un número positivo.");
	            return;
	        }

	        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.");
	            return;
	        }

	        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	            JOptionPane.showMessageDialog(this, "El apellido solo debe contener letras.");
	            return;
	        }

	        if (!telefonoStr.matches("\\d{6,15}")) {
	            JOptionPane.showMessageDialog(this, "El teléfono debe contener solo dígitos (entre 6 y 15 dígitos).");
	            return;
	        }

	        int telefono = Integer.parseInt(telefonoStr);

	        Empleados emp = new Empleados();
	        emp.setIdEmpleado(id);
	        emp.setNombre(nombre);
	        emp.setApellido(apellido);
	        emp.setTelefono(telefono);

	        controlador.agregarEmpleado(emp);
	        JOptionPane.showMessageDialog(this, "Empleado registrado correctamente.");
	        limpiarCampos();

	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "El ID y teléfono deben ser números válidos.");
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
	    }
	}
	protected void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();
	}
	private void limpiarCampos() {
		txtIdEmpleado.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtTelefono.setText("");
	}
}
