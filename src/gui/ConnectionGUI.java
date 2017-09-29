package gui;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ConnectionGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6133629344740990767L;
	
	protected final JTextField userloginJTextfield;
	protected final JPasswordField passwordJPassword;
	protected final JButton buttonRegister;
	protected final JButton buttonConnection;
	protected final JButton buttonExit;
	protected final IConnectionGUI guiLogic;
	
	public ConnectionGUI(){
		super("GUI Client Serveur");
		JPanel panel = new JPanel();
		panel.setBounds(0, 90, 282, 35);
		buttonConnection = new JButton("Valider");
		buttonRegister = new JButton("S'inscrire");
		panel.add(buttonConnection);
		panel.add(buttonRegister);
	    getContentPane().setLayout(null);
	    getContentPane().add(panel);
	    userloginJTextfield = new JTextField("");
	    userloginJTextfield.setBounds(150, 18, 100, 24);
	    getContentPane().add(userloginJTextfield);
	    userloginJTextfield.setPreferredSize(new Dimension(100, 24));
	    JLabel labelUsername = new JLabel("Identifiant : ", SwingConstants.CENTER);
	    labelUsername.setBounds(27, 13, 111, 35);
	    getContentPane().add(labelUsername);
	    JLabel lblNewLabel = new JLabel("Mot de passe : ");
	    lblNewLabel.setBounds(27, 42, 111, 35);
	    getContentPane().add(lblNewLabel);
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    passwordJPassword = new JPasswordField("");
	    passwordJPassword.setBounds(150, 48, 100, 24);
	    getContentPane().add(passwordJPassword);
	    buttonExit = new JButton("Exit");
	    buttonExit.setBounds(92, 135, 97, 25);
	    getContentPane().add(buttonExit);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);			
		setSize(new Dimension(290, 220));
		setLocationRelativeTo(null);
		
		guiLogic = new ConnectionGUILogic(this);
		attachActions(guiLogic);
		guiLogic.onWindowInit(this, new Object[]{});
	}
	
	private void attachActions(IConnectionGUI guiLogic){
		buttonConnection.addActionListener((e) -> guiLogic.onConnectionButtonClick(e, buttonConnection));
		buttonRegister.addActionListener((e) -> guiLogic.onRegisterButtonClick(e, buttonRegister));
		buttonExit.addActionListener((e) -> guiLogic.onCloseWindowButtonClick(e, buttonExit));
	}
}