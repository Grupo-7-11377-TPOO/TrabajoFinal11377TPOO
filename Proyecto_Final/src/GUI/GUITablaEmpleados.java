package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import Modelo.Empleados;
import controlador.EmpleadoControlador;

public class GUITablaEmpleados extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private EmpleadoControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpleadoControlador controlador = new EmpleadoControlador();
					GUITablaEmpleados frame = new GUITablaEmpleados(controlador);
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
	public GUITablaEmpleados(EmpleadoControlador controlador) {
		this.controlador = controlador;
		setTitle("Visualizando");
		setBounds(100, 100, 539, 342);
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
		cargarDatosTabla();

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
	private void cargarDatosTabla() {
		List<Empleados> lista = controlador.listarEmpleados();
		String[] columnas = { "ID", "Nombre", "Apellido", "Teléfono" };
		Object[][] datos = new Object[lista.size()][4];

		for (int i = 0; i < lista.size(); i++) {
			Empleados e = lista.get(i);
			datos[i][0] = e.getIdEmpleado();
			datos[i][1] = e.getNombre();
			datos[i][2] = e.getApellido();
			datos[i][3] = e.getTelefono();
		}
		DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
			public boolean isCellEditable(int row, int column) {
				return column != 0; // No editable el ID
			}
		};
		table.setModel(modelo);
	}
	//Editar mediante un boton presionado en la tabla
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado a editar.");
			return;
		}

		try {
			int id = (int) table.getValueAt(fila, 0);
			String nombre = (String) table.getValueAt(fila, 1);
			String apellido = (String) table.getValueAt(fila, 2);
			int telefono = Integer.parseInt(table.getValueAt(fila, 3).toString());

			Empleados emp = new Empleados();
			emp.setIdEmpleado(id);
			emp.setNombre(nombre);
			emp.setApellido(apellido);
			emp.setTelefono(telefono);

			controlador.actualizarEmpleado(emp);
			JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
			cargarDatosTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage());
		}
	}
	//Eliminar mediante un boton presionado en la tabla
	protected void do_btnNewButton_1_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado a eliminar.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;

		int id = (int) table.getValueAt(fila, 0);
		Empleados emp = new Empleados();
		emp.setIdEmpleado(id);
		controlador.eliminarEmpleado(emp);

		cargarDatosTabla();
	}
	//Actualizar mediante un boton presionado en la tabla, para ver los cambios de otras personas.
	protected void do_btnNewButton_2_actionPerformed(ActionEvent e) {
		cargarDatosTabla();
	}
}
