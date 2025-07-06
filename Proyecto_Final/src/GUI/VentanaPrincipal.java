package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.Empleados;
import controlador.DetalleVentaControlador;
import controlador.EmpleadoControlador;
import controlador.ProductoControlador;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JDesktopPane desktopPane;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private ProductoControlador controlador;
	private EmpleadoControlador controladorEmpleado;
	private DetalleVentaControlador controladorDetalle;
    private GUIFormulario formulario;
    private GUITablaProductos tabla;
    private GUIEmpleados empleados;
    private GUITablaEmpleados tablaEmpleados;
    private GUIDetalleVenta detalleVenta;
    private GUITablaDetalleVenta tablaDetalleVenta;
    private GUIDetalleCompra detalleCompra;
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem mntmNewMenuItem_4;
	private JMenuItem mntmNewMenuItem_5;
	private JLabel lblNewLabel;
	private JMenuItem mntmNewMenuItem_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
	        try {
	            VentanaPrincipal frame = new VentanaPrincipal();
	            frame.setVisible(true);

	            // Abrir el diálogo de login después que se muestra la ventana principal
	            GUILogin login = new GUILogin(frame, true);
	            login.setLocationRelativeTo(frame);
	            login.setVisible(true);

	            if (!login.isLoginExitoso()) {
	                JOptionPane.showMessageDialog(frame, "Debe iniciar sesión para continuar.");
	                System.exit(0);
	            }
	            Empleados usuarioLogueado = login.getUsuarioActual();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
	

	/**
	 * Create the frame.
	 */ 
	
	public VentanaPrincipal() {
		setTitle("Interfaz Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlador = new ProductoControlador();
		controladorEmpleado = new EmpleadoControlador();
		controladorDetalle = new DetalleVentaControlador();
		setBounds(100, 100, 555, 480);
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				mnNewMenu = new JMenu("Formulario");
				mnNewMenu.addActionListener(this);
				menuBar.add(mnNewMenu);
				{
					mntmNewMenuItem = new JMenuItem("Productos");
					mntmNewMenuItem.addActionListener(this);
					mnNewMenu.add(mntmNewMenuItem);
				}
				{
					mntmNewMenuItem_2 = new JMenuItem("Venta");
					mntmNewMenuItem_2.addActionListener(this);
					mnNewMenu.add(mntmNewMenuItem_2);
				}
				{
					mntmNewMenuItem_5 = new JMenuItem("Empleado");
					mntmNewMenuItem_5.addActionListener(this);
					mnNewMenu.add(mntmNewMenuItem_5);
				}
				{
					mntmNewMenuItem_6 = new JMenuItem("Compra");
					mntmNewMenuItem_6.addActionListener(this);
					mnNewMenu.add(mntmNewMenuItem_6);
				}
			} 
			{
				mnNewMenu_1 = new JMenu("Tablas");
				mnNewMenu_1.addActionListener(this);
				menuBar.add(mnNewMenu_1);
				{
					mntmNewMenuItem_1 = new JMenuItem("Visualizar Inventario");
					mntmNewMenuItem_1.addActionListener(this);
					mnNewMenu_1.add(mntmNewMenuItem_1);
				}
				{
					mntmNewMenuItem_3 = new JMenuItem("Visualizar detalle de ventas");
					mntmNewMenuItem_3.addActionListener(this);
					mnNewMenu_1.add(mntmNewMenuItem_3);
				}
				{
					mntmNewMenuItem_4 = new JMenuItem("Visualizar Empleados");
					mntmNewMenuItem_4.addActionListener(this);
					mnNewMenu_1.add(mntmNewMenuItem_4);
				}
			}
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			desktopPane = new JDesktopPane();
			desktopPane.setBounds(0, 0, 539, 419);
			contentPane.add(desktopPane);
			desktopPane.setLayout(null);
			{
				lblNewLabel = new JLabel("NEGOCIO DE LICORES");
				lblNewLabel.setFont(new Font("Nirmala Text", Font.BOLD, 40));
				lblNewLabel.setBounds(58, 182, 420, 87);
				desktopPane.add(lblNewLabel);
			}
		}
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmNewMenuItem_6) {
			do_mntmNewMenuItem_6_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem_3) {
			do_mntmNewMenuItem_3_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem_2) {
			do_mntmNewMenuItem_2_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem_4) {
			do_mntmNewMenuItem_4_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem_5) {
			do_mntmNewMenuItem_5_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem_1) {
			do_mntmNewMenuItem_1_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem) {
			do_mntmNewMenuItem_actionPerformed(e);
		}
	}
    
	private void cerrarInternalFrames() {
	    for (JInternalFrame frame : desktopPane.getAllFrames()) {
	        frame.dispose();  // Cierra cada frame del escritorio
	    }
	}
	protected void do_mntmNewMenuItem_actionPerformed(ActionEvent e) {
		if (formulario == null || formulario.isClosed()) {
			cerrarInternalFrames();
			formulario = new GUIFormulario(controlador);
	        desktopPane.add(formulario);
	        centrarInternalFrame(formulario);
	        formulario.setVisible(true);
	    } else {
	        try {
	            formulario.setSelected(true); // Si ya existe, lo traemos al frente
	        } catch (java.beans.PropertyVetoException e1) {
	            e1.printStackTrace();
	        }
	    }
	}
	protected void do_mntmNewMenuItem_1_actionPerformed(ActionEvent e) {
		if (tabla == null || tabla.isClosed()) {
			cerrarInternalFrames();
			tabla = new GUITablaProductos(controlador);
	        desktopPane.add(tabla);
	        centrarInternalFrame(tabla);
	        tabla.setVisible(true);
	    } else {
	        try {
	            tabla.setSelected(true); // Si ya existe, lo traemos al frente
	        } catch (java.beans.PropertyVetoException e1) {
	            e1.printStackTrace();
	        }
	    }
	}
	private void centrarInternalFrame(JInternalFrame frame) {
	    int x = (desktopPane.getWidth() - frame.getWidth()) / 2;
	    int y = (desktopPane.getHeight() - frame.getHeight()) / 2;
	    frame.setLocation(x, y);
	}
	//menuitem que abriria la GUIEmpleados en la ventanaPrincipal
	protected void do_mntmNewMenuItem_5_actionPerformed(ActionEvent e) {
		if (empleados == null || empleados.isClosed()) {
			cerrarInternalFrames();
			empleados = new GUIEmpleados(controladorEmpleado); // Crear nueva instancia
	        desktopPane.add(empleados);     // Añadir al escritorio
	        centrarInternalFrame(empleados); // Centrarlo
	        empleados.setVisible(true);     // Mostrarlo
	    } else {
	        try {
	            empleados.setSelected(true); // Enfocar si ya estaba creado
	        } catch (java.beans.PropertyVetoException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	//menuitem que abriria la GUITablaEmpleados en la ventanaPrincipal
	protected void do_mntmNewMenuItem_4_actionPerformed(ActionEvent e) {
		if (tablaEmpleados == null || tablaEmpleados.isClosed()) {
			cerrarInternalFrames();
			tablaEmpleados = new GUITablaEmpleados(controladorEmpleado);
            desktopPane.add(tablaEmpleados);
            centrarInternalFrame(tablaEmpleados);
            tablaEmpleados.setVisible(true);
        } else {
            try {
                tablaEmpleados.setSelected(true);
            } catch (java.beans.PropertyVetoException e1) {
                e1.printStackTrace();
            }
        }
	}
	//menuItem que abriria la GUIDetalleVenta en la ventana Principal
	protected void do_mntmNewMenuItem_2_actionPerformed(ActionEvent e) {
		if (detalleVenta == null || detalleVenta.isClosed()) {
	        cerrarInternalFrames();
	        detalleVenta = new GUIDetalleVenta(controlador, controladorEmpleado); // Crear nueva instancia
	        desktopPane.add(detalleVenta);       // Añadir al escritorio
	        centrarInternalFrame(detalleVenta);  // Centrarlo
	        detalleVenta.setVisible(true);       // Mostrarlo
	    } else {
	        try {
	            detalleVenta.setSelected(true);  // Enfocar si ya estaba creado
	        } catch (java.beans.PropertyVetoException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	//menuItem que abriria la GUITablaDetalleVenta en la ventana Principal
	protected void do_mntmNewMenuItem_3_actionPerformed(ActionEvent e) {
		if (tablaDetalleVenta == null || tablaDetalleVenta.isClosed()) {
	        cerrarInternalFrames();
	        tablaDetalleVenta = new GUITablaDetalleVenta(controladorDetalle); // Instancia de la tabla
	        desktopPane.add(tablaDetalleVenta);             // Añadir al escritorio
	        centrarInternalFrame(tablaDetalleVenta);        // Centrar
	        tablaDetalleVenta.setVisible(true);             // Mostrar
	    } else {
	        try {
	            tablaDetalleVenta.setSelected(true);        // Enfocar si ya está creado
	        } catch (java.beans.PropertyVetoException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	//menuItem que abriria la GUIDetalleCompra en la ventana Principal
	protected void do_mntmNewMenuItem_6_actionPerformed(ActionEvent e) {
		if (detalleCompra == null || detalleCompra.isClosed()) {
	        cerrarInternalFrames();
	        detalleCompra = new GUIDetalleCompra();
	        desktopPane.add(detalleCompra);
	        centrarInternalFrame(detalleCompra);
	        detalleCompra.setVisible(true);
	    } else {
	        try {
	            detalleCompra.setSelected(true);
	        } catch (java.beans.PropertyVetoException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
}
