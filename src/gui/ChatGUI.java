package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ChatGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4111814831855679238L;
	
	protected final JTextArea chatArea;
	protected final JTextField messageArea;
	protected final JButton btnSendMessage;
	protected final IChatGUI guiLogic;
	private JButton btnExit;
	
	public ChatGUI() {
		super("GUI Chat");
		getContentPane().setLayout(null);
		chatArea = new JTextArea();
		chatArea.setBounds(154, 64, 123, 190);
		getContentPane().add(chatArea);
		JScrollPane scrollPane = new JScrollPane(chatArea,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(23, 13, 380, 166);
		getContentPane().add(scrollPane);
		messageArea = new JTextField();
		messageArea.setBounds(23, 195, 270, 22);
		getContentPane().add(messageArea);
		messageArea.setColumns(10);
		btnSendMessage = new JButton("Send");
		btnSendMessage.setBounds(306, 192, 97, 25);
		getContentPane().add(btnSendMessage);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(306, 265, 97, 25);
		getContentPane().add(btnExit);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		setSize(new Dimension(445, 350));
		setLocationRelativeTo(null);
		
		guiLogic = new ChatGUILogic(this);
		attachActions(guiLogic);
		guiLogic.onWindowInit(0);
	}
	
	private void attachActions(IChatGUI guiLogic){
		btnSendMessage.addActionListener((e) -> guiLogic.onSendMessageButtonClick(e, btnSendMessage));
		btnExit.addActionListener((e) -> guiLogic.onExitButtonClick(e, btnExit));
	}
}
