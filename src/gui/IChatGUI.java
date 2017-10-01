package gui;

import java.awt.event.ActionEvent;

public interface IChatGUI extends IWindowGUI {
	void onSendMessageButtonClick (ActionEvent e, Object sender);
	void onExitButtonClick (ActionEvent e, Object sender);
	void onSendFileButtonClick (ActionEvent e, Object sender);
}
