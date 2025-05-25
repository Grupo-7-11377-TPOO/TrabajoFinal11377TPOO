package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class GUITablaDetalleVenta extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITablaDetalleVenta frame = new GUITablaDetalleVenta();
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
	public GUITablaDetalleVenta() {
		setBounds(100, 100, 450, 300);

	}

}
