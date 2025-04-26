package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIFormulario extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIFormulario frame = new GUIFormulario();
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
	public GUIFormulario() {
		setTitle("Abierto");
		setBounds(100, 100, 539, 342);
		getContentPane().setLayout(null);
		{
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(this);
			btnSalir.setBounds(210, 143, 89, 23);
			getContentPane().add(btnSalir);
		}

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalir) {
			do_btnSalir_actionPerformed(e);
		}
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
		dispose();
	}
}
