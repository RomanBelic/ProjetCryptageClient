package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import utils.UIUtils;

public class ConnectionGUILogic extends AbstractUILogic<ConnectionGUI> implements IConnectionGUI{

	private final RegisterGUI regUI;
	public ConnectionGUILogic(ConnectionGUI ui) {
		super(ui);
		regUI = UIUtils.getRegisterGUI();
		regUI.setCallBackOperation(this::onReturnedFromRegisterUI);
	}

	@Override
	public void onConnectionButtonClick(ActionEvent e, Object sender) {
		
		
	}

	@Override
	public void onRegisterButtonClick(ActionEvent e, Object sender) {
		regUI.setVisible(true);
		ui.setVisible(false);
	}

	@Override
	public void onCloseWindowButtonClick(ActionEvent e, Object sender) {
		ui.dispose();
		System.exit(0);
	}

	@Override
	public void onWindowInit(Object... args) {
		ui.setVisible(true);
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
		
	}
	
	private void onReturnedFromRegisterUI(Object arg){
		EventQueue.invokeLater(() -> {
			regUI.setVisible(false);
			ui.setVisible(true);
		});
	}
}
