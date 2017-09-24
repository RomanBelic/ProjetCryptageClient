package gui;

import java.awt.event.ActionEvent;

public interface IRegisterGUI extends IWindowGUI {
	void onValiderButtonClick(ActionEvent e, Object sender);
	void onExitButtonClick(ActionEvent e, Object sender);
}
