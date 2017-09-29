package gui;

import java.awt.event.ActionEvent;

import interfaces.Patterns.ICallback;

public class RegisterGUILogic extends AbstractUILogic<RegisterGUI> implements IRegisterGUI{

	private ICallback<Object> callbackCloseButton;
	
	public RegisterGUILogic(RegisterGUI ui){
		super(ui);
	}
	
	@Override
	public void onValiderButtonClick(ActionEvent e, Object sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExitButtonClick(ActionEvent e, Object sender) {
		// TODO Auto-generated method stub
		if (callbackCloseButton == null)
			return;
		callbackCloseButton.onCalledBack(0);
	}

	@Override
	public void onWindowInit(Object... args) {
	
		
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setOnCloseButtonCallback(ICallback<Object> icallback){
		this.callbackCloseButton = icallback;
	}

}
