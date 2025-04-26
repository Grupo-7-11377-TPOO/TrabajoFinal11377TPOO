package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class GUIFormulario extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnSalir;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JComboBox comboBox;
	private JButton btnNewButton;

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
		{
			getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
			{
				lblNewLabel = new JLabel("Código del producto:");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel);
			}
			{
				textField = new JTextField();
				textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(textField);
				textField.setColumns(10);
			}
			{
				lblNewLabel_3 = new JLabel("Nombre del producto:");
				lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_3);
			}
			{
				textField_1 = new JTextField();
				textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(textField_1);
				textField_1.setColumns(10);
			}
			{
				lblNewLabel_5 = new JLabel("Tipo de Licor:");
				lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_5);
			}
			{
				comboBox = new JComboBox();
				comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Ron", "Vodka", "Whisky", "Cerveza de trigo"}));
				getContentPane().add(comboBox);
			}
			{
				lblNewLabel_7 = new JLabel("Precio:");
				lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_7);
			}
			{
				textField_3 = new JTextField();
				textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(textField_3);
				textField_3.setColumns(10);
			}
			{
				lblNewLabel_6 = new JLabel("Stock disponible:");
				lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(lblNewLabel_6);
			}
			{
				textField_4 = new JTextField();
				textField_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
				getContentPane().add(textField_4);
				textField_4.setColumns(10);
			}
		}
		btnSalir = new JButton("Cancelar");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalir.addActionListener(this);
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			getContentPane().add(btnNewButton);
		}
		getContentPane().add(btnSalir);

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
