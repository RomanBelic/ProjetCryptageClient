package gui;

import java.awt.event.ActionEvent;

public interface IConnectionGUI extends IWindowGUI {
	void onConnectionButtonClick(ActionEvent e, Object sender);
	void onRegisterButtonClick(ActionEvent e, Object sender);
	void onCloseWindowButtonClick(ActionEvent e, Object sender);
}
