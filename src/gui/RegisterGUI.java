package gui;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7289001126939436994L;
	protected final JTextField loginJTextfield;
	protected final JPasswordField passwordJPassword;
	protected final JButton buttonValider;
	protected final JTextField usernameJTextfield;
	protected final JButton buttonExit;
	protected final IRegisterGUI guiLogic;
	protected final JLabel errLabel;
	
	public RegisterGUI(){
		super("User's register");
		JPanel panel = new JPanel();
		JLabel usernameLabel = new JLabel("Identifiant : ");
		usernameLabel.setBounds(12, 10, 70, 30);
		JLabel passwordLabel = new JLabel("Mot de passe : ");
		passwordLabel.setBounds(12, 53, 89, 30);
		loginJTextfield = new JTextField("");
		loginJTextfield.setBounds(119, 13, 100, 24);
		passwordJPassword = new JPasswordField("");
		passwordJPassword.setBounds(119, 50, 100, 24);
		buttonValider = new JButton("Valider");
		buttonValider.setBounds(69, 167, 107, 25);
		loginJTextfield.setPreferredSize(new Dimension(100, 24));
		passwordJPassword.setPreferredSize(new Dimension(100, 24));
		panel.setLayout(null);
		panel.add(usernameLabel);
		panel.add(loginJTextfield);
		panel.add(passwordLabel);
		panel.add(passwordJPassword);
		panel.add(buttonValider);
		setContentPane(panel);
	    JLabel lblNom = new JLabel("Nom : ");
	    lblNom.setBounds(12, 84, 70, 30);
	    panel.add(lblNom);
	    buttonExit = new JButton("Annuler");
	    buttonExit.setBounds(69, 205, 107, 25);
	    panel.add(buttonExit);
	    usernameJTextfield = new JTextField("");
	    usernameJTextfield.setPreferredSize(new Dimension(100, 24));
	    usernameJTextfield.setBounds(119, 87, 100, 24);
	    panel.add(usernameJTextfield);
	    
	    errLabel = new JLabel("");
	    errLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    errLabel.setForeground(Color.RED);
	    errLabel.setBounds(0, 127, 252, 27);
	    panel.add(errLabel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);			// Stop running when we close the window
		setSize(270, 290);	
		setLocationRelativeTo(null);
		guiLogic = new RegisterGUILogic(this);
		attachActions(guiLogic);
		guiLogic.onWindowInit(0);
	}
	
	@Override
	public void dispose() {
		guiLogic.onDisposing();
		super.dispose();
	}
	
	private void attachActions(IRegisterGUI guiLogic){
		buttonValider.addActionListener((e) -> guiLogic.onValiderButtonClick(e, e.getSource()));
		buttonExit.addActionListener((e) -> guiLogic.onExitButtonClick(e, e.getSource()));
	}
}
