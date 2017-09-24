package gui;

import java.awt.event.ActionEvent;

public class RegisterGUILogic extends AbstractUILogic<RegisterGUI> implements IRegisterGUI{

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
		if (ui.icallback == null)
			return;
		ui.icallback.onCalledBack(0);
	}

	@Override
	public void onWindowInit(Object... args) {
	
		
	}

	@Override
	public void onWindowClose(Object... args) {
		// TODO Auto-generated method stub
		
	}

}
