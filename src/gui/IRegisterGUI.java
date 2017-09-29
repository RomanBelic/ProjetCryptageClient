package gui;

import java.awt.event.ActionEvent;

import interfaces.Patterns.ICallback;

public interface IRegisterGUI extends IWindowGUI {
	void onValiderButtonClick(ActionEvent e, Object sender);
	void onExitButtonClick(ActionEvent e, Object sender);
	public void setOnCloseButtonCallback(ICallback<Object> icallback);
}
