import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Test2 {

	private JFrame frmSignIn;
	private JTextField textField;
	private JPasswordField passwordField;
	private JCheckBox chckbxShowPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test2 window = new Test2();
					window.frmSignIn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSignIn = new JFrame();
		frmSignIn.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 12));
		frmSignIn.setTitle("Sign in");
		frmSignIn.setBounds(100, 100, 290, 225);
		frmSignIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSignIn.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(45, 40, 200, 19);
		frmSignIn.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(44, 108, 188, 19);
		frmSignIn.getContentPane().add(passwordField);
		
		chckbxShowPassword = new JCheckBox("Show password");
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar((char)0);
				}else {
					passwordField.setEchoChar((char)1);
				}
			}
		});
		chckbxShowPassword.setBounds(44, 135, 153, 23);
		frmSignIn.getContentPane().add(chckbxShowPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(44, 81, 66, 15);
		frmSignIn.getContentPane().add(lblPassword);
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setBounds(44, 12, 66, 15);
		frmSignIn.getContentPane().add(lblLogin);
	}
}
