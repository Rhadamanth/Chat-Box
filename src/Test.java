import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;

public class Test {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Show Users");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(620, 5, 150, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.setBounds(620, 55, 150, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Send");
		btnNewButton_2.setBounds(452, 55, 80, 25);
		frame.getContentPane().add(btnNewButton_2);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 109, 774, 350);
		frame.getContentPane().add(textPane);
		
		textField = new JTextField();
		textField.setBounds(12, 6, 428, 75);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Switch Name");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_3.setBounds(620, 30, 150, 25);
		frame.getContentPane().add(btnNewButton_3);
	}
}
