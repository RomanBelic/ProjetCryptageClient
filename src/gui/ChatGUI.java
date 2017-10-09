package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;

public class ChatGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4111814831855679238L;
	
	protected final JFileChooser fileChooser;
	protected final JTextArea chatArea;
	protected final JTextField messageArea;
	protected final JButton btnSendMessage;
	protected final IChatGUI guiLogic;
	protected final JButton btnExit;
	protected final JButton btnUpload;
	protected final JButton btnChooseFile;
	protected final JLabel lblChosenFile;
	
	public ChatGUI() {
		super("GUI Chat");
		getContentPane().setLayout(null);
		chatArea = new JTextArea();
		chatArea.setEditable(false);
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
		
		btnUpload = new JButton("Upload");
		btnUpload.setBounds(306, 227, 97, 25);
		getContentPane().add(btnUpload);
		
		fileChooser = new JFileChooser();
		fileChooser.setBorder(new LineBorder(new Color(0, 0, 0)));
		fileChooser.setBounds(153, 253, 140, -24);
		getContentPane().add(fileChooser);
		
		btnChooseFile = new JButton("Open...");
		btnChooseFile.setBounds(214, 228, 79, 24);
		getContentPane().add(btnChooseFile);
		
		lblChosenFile = new JLabel("");
		lblChosenFile.setBounds(23, 230, 179, 22);
		getContentPane().add(lblChosenFile);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		setSize(new Dimension(445, 350));
		setLocationRelativeTo(null);
		
		guiLogic = new ChatGUILogic(this);
		attachActions(guiLogic);
		guiLogic.onWindowInit(0);
	}
	
	@Override
	public void dispose() {
		guiLogic.onDisposing();
		super.dispose();
	}
	
	private void attachActions(IChatGUI guiLogic){
		btnChooseFile.addActionListener((e) -> guiLogic.onOpenFileChooserClick(e, btnChooseFile));
		btnUpload.addActionListener((e) -> guiLogic.onSendFileButtonClick(e, btnUpload));
		btnSendMessage.addActionListener((e) -> guiLogic.onSendMessageButtonClick(e, btnSendMessage));
		btnExit.addActionListener((e) -> guiLogic.onExitButtonClick(e, btnExit));
	}
}
