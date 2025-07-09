package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class GUIProgramadores extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblN;
	private JLabel lblN_1;
	private JLabel lblN_2;
	private JLabel lblN_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIProgramadores frame = new GUIProgramadores();
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
	public GUIProgramadores() {
		setTitle("Informacion General");
		setBounds(100, 100, 539, 430);
		getContentPane().setLayout(null);
		{
			lblNewLabel = new JLabel("Segura Chunga, Sebastián Alonso ");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(40, 141, 222, 31);
			getContentPane().add(lblNewLabel);
		}
		{
			lblNewLabel_1 = new JLabel("Solís Lengua, Braian Javier ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(61, 333, 170, 31);
			getContentPane().add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("Sipán Fuertes, Walter Javier ");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(304, 141, 183, 31);
			getContentPane().add(lblNewLabel_2);
		}
		{
			lblNewLabel_3 = new JLabel("Suica Salcedo, Alvaro Luis");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_3.setBounds(316, 333, 170, 31);
			getContentPane().add(lblNewLabel_3);
		}
		{
			lblN = new JLabel("N00");
			lblN.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblN.setBounds(84, 163, 105, 31);
			getContentPane().add(lblN);
		}
		{
			lblN_1 = new JLabel("N00");
			lblN_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblN_1.setBounds(314, 163, 105, 31);
			getContentPane().add(lblN_1);
		}
		{
			lblN_2 = new JLabel("N00");
			lblN_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblN_2.setBounds(106, 358, 105, 31);
			getContentPane().add(lblN_2);
		}
		{
			lblN_4 = new JLabel("N00376156");
			lblN_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblN_4.setBounds(354, 358, 80, 31);
			getContentPane().add(lblN_4);
		}

	}
}
