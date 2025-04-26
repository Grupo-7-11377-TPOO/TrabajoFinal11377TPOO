package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class GUITablaProductos extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITablaProductos frame = new GUITablaProductos();
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
	public GUITablaProductos() {
		setTitle("Visualizando");
		setBounds(100, 100, 539, 342);

	}

}
