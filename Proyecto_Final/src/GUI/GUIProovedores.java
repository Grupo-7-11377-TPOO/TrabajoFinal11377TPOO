package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.Proovedor;
import controlador.ProovedorControlador;

public class GUIProovedores extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btnAgregarProovedor;
	private JButton btnEditarProovedor;
	private JButton btnEliminarProovedor;
	private JButton btnSalir;
	private ProovedorControlador controlador;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIProovedores frame = new GUIProovedores(new ProovedorControlador());
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
	public GUIProovedores(ProovedorControlador controlador) {
		setTitle("Ventana de proovedores");
		this.controlador = controlador;
		setBounds(100, 100, 539, 342);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnAgregarProovedor = new JButton("Agregar");
		btnAgregarProovedor.addActionListener(this);
		panel.add(btnAgregarProovedor);
		
		btnEditarProovedor = new JButton("Editar");
		btnEditarProovedor.addActionListener(this);
		panel.add(btnEditarProovedor);
		
		btnEliminarProovedor = new JButton("Eliminar");
		btnEliminarProovedor.addActionListener(this);
		panel.add(btnEliminarProovedor);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(this);
		panel.add(btnSalir);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		cargarTabla();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalir) {
			do_btnSalir_actionPerformed(e);
		}
		if (e.getSource() == btnEliminarProovedor) {
			do_btnEliminarProovedor_actionPerformed(e);
		}
		if (e.getSource() == btnEditarProovedor) {
			do_btnEditarProovedor_actionPerformed(e);
		}
		if (e.getSource() == btnAgregarProovedor) {
			do_btnAgregarProovedor_actionPerformed(e);
		}
	}
	private void cargarTabla() {
        List<Proovedor> lista = controlador.listarProovedores();
        String[] columnas = { "ID", "Nombre", "Teléfono", "RUC" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Proovedor p : lista) {
            Object[] fila = { p.getIdProovedor(), p.getNombre(), p.getTelefono(), p.getRuc() };
            modelo.addRow(fila);
        }
        table.setModel(modelo);
    }
	protected void do_btnAgregarProovedor_actionPerformed(ActionEvent e) {
		try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del proveedor:");
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del proveedor:");
            String telefono = JOptionPane.showInputDialog(this, "Ingrese el teléfono:");
            String ruc = JOptionPane.showInputDialog(this, "Ingrese el RUC:");

            if (idStr == null || nombre == null || telefono == null || ruc == null ||
                idStr.trim().isEmpty() || nombre.trim().isEmpty() || telefono.trim().isEmpty() || ruc.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            int id = Integer.parseInt(idStr);

            Proovedor p = new Proovedor();
            p.setIdProovedor(id);
            p.setNombre(nombre);
            p.setTelefono(telefono);
            p.setRuc(ruc);

            controlador.agregarProovedor(p);
            JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente.");
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + ex.getMessage());
        }
	}
	protected void do_btnEditarProovedor_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
        if (fila != -1) {
            try {
                int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
                String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", table.getValueAt(fila, 1));
                String telefono = JOptionPane.showInputDialog(this, "Nuevo teléfono:", table.getValueAt(fila, 2));
                String ruc = JOptionPane.showInputDialog(this, "Nuevo RUC:", table.getValueAt(fila, 3));

                if (nombre == null || telefono == null || ruc == null ||
                    nombre.trim().isEmpty() || telefono.trim().isEmpty() || ruc.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }

                Proovedor p = new Proovedor();
                p.setIdProovedor(id);
                p.setNombre(nombre);
                p.setTelefono(telefono);
                p.setRuc(ruc);

                controlador.actualizarProovedor(p);
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al editar proveedor: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para editar.");
        }
	}
	protected void do_btnEliminarProovedor_actionPerformed(ActionEvent e) {
		int fila = table.getSelectedRow();
        if (fila != -1) {
            int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este proveedor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controlador.eliminarProovedor(id);
                JOptionPane.showMessageDialog(this, "Proveedor eliminado.");
                cargarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.");
        }
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
		dispose();
	}
}
