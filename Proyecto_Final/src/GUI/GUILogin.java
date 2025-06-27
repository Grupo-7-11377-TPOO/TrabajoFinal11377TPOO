package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.Empleados;
import Modelo.Login;
import controlador.ControladorLogin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUILogin extends JDialog implements ActionListener {
	private boolean loginExitoso = false;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtIdIdentificacion;
	private JTextField txtContraseña;
	private JComboBox cmbDueñoEmpleado;
	private JButton btnCambiarContraseña;
	private JButton btnIngresar;
	private JButton btnAgregarUsuario;
	private Empleados usuarioActual;
	public boolean isLoginExitoso() {
        return loginExitoso;
    }
	public Empleados getUsuarioActual() {
	    return usuarioActual;
	} 
	/**
	 * Create the dialog.
	 */
	public GUILogin(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Usuario");
		setBounds(100, 100, 410, 237);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblNewLabel = new JLabel("Usuario:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(54, 35, 95, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			lblNewLabel_1 = new JLabel("Rol:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(54, 73, 95, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("Contraseña:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(54, 119, 95, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtIdIdentificacion = new JTextField();
			txtIdIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtIdIdentificacion.setBounds(159, 29, 115, 20);
			contentPanel.add(txtIdIdentificacion);
			txtIdIdentificacion.setColumns(10);
		}
		{
			txtContraseña = new JTextField();
			txtContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtContraseña.setBounds(159, 113, 115, 20);
			contentPanel.add(txtContraseña);
			txtContraseña.setColumns(10);
		}
		{
			cmbDueñoEmpleado = new JComboBox();
			cmbDueñoEmpleado.setModel(new DefaultComboBoxModel(new String[] {"Dueño", "Empleado"}));
			cmbDueñoEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			cmbDueñoEmpleado.setBounds(159, 66, 115, 22);
			contentPanel.add(cmbDueñoEmpleado);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnIngresar = new JButton("Ingresar");
				btnIngresar.addActionListener(this);
				btnIngresar.setActionCommand("OK");
				buttonPane.add(btnIngresar);
				getRootPane().setDefaultButton(btnIngresar);
			}
			{
				btnAgregarUsuario = new JButton("Agregar Usuario");
				btnAgregarUsuario.addActionListener(this);
				btnAgregarUsuario.setActionCommand("Cancel");
				buttonPane.add(btnAgregarUsuario);
			}
			{
				btnCambiarContraseña = new JButton("Cambiar contraseña");
				btnCambiarContraseña.addActionListener(this);
				buttonPane.add(btnCambiarContraseña);
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCambiarContraseña) {
			do_btnCambiarContraseña_actionPerformed(e);
		}
		if (e.getSource() == btnAgregarUsuario) {
			do_btnAgregarUsuario_actionPerformed(e);
		}
		if (e.getSource() == btnIngresar) {
			do_btnIngresar_actionPerformed(e);
		}
	}
	protected void do_btnIngresar_actionPerformed(java.awt.event.ActionEvent evt) {
		String id = txtIdIdentificacion.getText().trim();
		String contrasena = txtContraseña.getText().trim();
		String rol = cmbDueñoEmpleado.getSelectedItem().toString();

		if (id.isEmpty() || contrasena.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
			return;
		}

		ControladorLogin controlador = new ControladorLogin();
		if (controlador.validarLogin(id, contrasena, rol)) {
			JOptionPane.showMessageDialog(this, "Bienvenido " + rol + ".");
			loginExitoso = true;
			dispose();  // Solo cierra el login, no abre la ventana principal
		} else {
			JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
		}
	}
	protected void do_btnAgregarUsuario_actionPerformed(ActionEvent e) {
		String id = JOptionPane.showInputDialog(this, "Ingrese ID del nuevo usuario:");
		String contrasena = JOptionPane.showInputDialog(this, "Ingrese la contraseña:");
		String[] roles = {"Dueño", "Empleado"};
		String rol = (String) JOptionPane.showInputDialog(this, "Seleccione el rol:",
		        "Rol", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

		if (id == null || contrasena == null || rol == null ||
		    id.isEmpty() || contrasena.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
			return;
		}

		try {
			Login nuevoLogin = new Login();
			nuevoLogin.setIdIdentificacion(Integer.parseInt(id));
			nuevoLogin.setContrasena(contrasena);
			nuevoLogin.setRol(rol);

			ControladorLogin controlador = new ControladorLogin();
			controlador.agregarLogin(nuevoLogin);
			JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
		}
	}
	protected void do_btnCambiarContraseña_actionPerformed(ActionEvent e) {
		String id = JOptionPane.showInputDialog(this, "Ingrese su ID:");
		String actual = JOptionPane.showInputDialog(this, "Ingrese su contraseña actual:");
		String nueva = JOptionPane.showInputDialog(this, "Ingrese su nueva contraseña:");
		String rol = cmbDueñoEmpleado.getSelectedItem().toString();

		if (id == null || actual == null || nueva == null ||
		    id.isEmpty() || actual.isEmpty() || nueva.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
			return;
		}

		ControladorLogin controlador = new ControladorLogin();
		if (controlador.validarLogin(id, actual, rol)) {
			Login login = new Login();
			login.setIdIdentificacion(Integer.parseInt(id));
			login.setRol(rol);
			login.setContrasena(nueva);
			controlador.actualizarLogin(login);
			JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
		} else {
			JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
		}
	}
}
