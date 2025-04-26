package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.ProductoControlador;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlador = new ProductoControlador();
		setBounds(100, 100, 555, 403);
		{
			
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				mnNewMenu = new JMenu("Formulario");
				mnNewMenu.addActionListener(this);
				menuBar.add(mnNewMenu);
				{
					mntmNewMenuItem = new JMenuItem("Abrir");
					mntmNewMenuItem.addActionListener(this);
					mnNewMenu.add(mntmNewMenuItem);
				}
			}
			{
				mnNewMenu_1 = new JMenu("Tabla de productos");
				mnNewMenu_1.addActionListener(this);
				menuBar.add(mnNewMenu_1);
				{
					mntmNewMenuItem_1 = new JMenuItem("Visualizar");
					mntmNewMenuItem_1.addActionListener(this);
					mnNewMenu_1.add(mntmNewMenuItem_1);
				}
			}
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			desktopPane = new JDesktopPane();
			desktopPane.setBounds(0, 0, 539, 342);
			contentPane.add(desktopPane);
			desktopPane.setLayout(null);
		}
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmNewMenuItem_1) {
			do_mntmNewMenuItem_1_actionPerformed(e);
		}
		if (e.getSource() == mntmNewMenuItem) {
			do_mntmNewMenuItem_actionPerformed(e);
		}
	}
	private GUIFormulario formulario;
	private GUITablaProductos tabla;

	protected void do_mntmNewMenuItem_actionPerformed(ActionEvent e) {
		if (formulario == null || formulario.isClosed()) {
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
}
