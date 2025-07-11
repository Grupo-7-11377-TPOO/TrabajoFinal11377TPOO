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
		setTitle("Informacion de empleados");
		setBounds(100, 100, 539, 430);
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
	private Empleados buscarEmpleadoPorId(int id) {
	    for (Empleados e : controlador.listarEmpleados()) {
	        if (e.getIdEmpleado() == id) {
	            return e;
	        }
	    }
	    return null;
	}
	//Editar mediante un boton presionado en la tabla
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		int filaSeleccionada = table.getSelectedRow();
	    if (filaSeleccionada != -1) {
	        int id = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
	        Empleados empleado = buscarEmpleadoPorId(id);
	        if (empleado != null) {
	            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", empleado.getNombre());
	            String nuevoApellido = JOptionPane.showInputDialog(this, "Nuevo apellido:", empleado.getApellido());
	            String nuevoTelefonoStr = JOptionPane.showInputDialog(this, "Nuevo teléfono:", empleado.getTelefono());

	            if (nuevoNombre == null || nuevoApellido == null || nuevoTelefonoStr == null ||
	                nuevoNombre.trim().isEmpty() || nuevoApellido.trim().isEmpty() || nuevoTelefonoStr.trim().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
	                return;
	            }

	            if (!nuevoNombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	                JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.");
	                return;
	            }

	            if (!nuevoApellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	                JOptionPane.showMessageDialog(this, "El apellido solo debe contener letras.");
	                return;
	            }

	            if (!nuevoTelefonoStr.matches("\\d{6,15}")) {
	                JOptionPane.showMessageDialog(this, "El teléfono debe contener solo dígitos (6 a 15 dígitos).");
	                return;
	            }

	            try {
	                int nuevoTelefono = Integer.parseInt(nuevoTelefonoStr);
	                if (nuevoTelefono <= 0) {
	                    JOptionPane.showMessageDialog(this, "El teléfono debe ser un número positivo.");
	                    return;
	                }

	                empleado.setNombre(nuevoNombre.trim());
	                empleado.setApellido(nuevoApellido.trim());
	                empleado.setTelefono(nuevoTelefono);

	                controlador.actualizarEmpleado(empleado);
	                JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
	                cargarDatosTabla();

	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "El teléfono debe ser un número válido.");
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Seleccione un empleado para editar.");
	    }
	}
	//Eliminar mediante un boton presionado en la tabla
	protected void do_btnNewButton_1_actionPerformed(ActionEvent e) {
		int filaSeleccionada = table.getSelectedRow();
	    if (filaSeleccionada != -1) {
	        int id = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
	        Empleados empleado = buscarEmpleadoPorId(id);
	        if (empleado != null) {
	            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
	            if (confirmacion == JOptionPane.YES_OPTION) {
	                controlador.eliminarEmpleado(empleado);
	                JOptionPane.showMessageDialog(this, "Empleado eliminado.");
	                cargarDatosTabla();
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar.");
	    }
	}
	//Actualizar mediante un boton presionado en la tabla, para ver los cambios de otras personas.
	protected void do_btnNewButton_2_actionPerformed(ActionEvent e) {
		cargarDatosTabla();
	}
}
