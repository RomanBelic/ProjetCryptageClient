package main;

import java.awt.EventQueue;

import utils.UIUtils;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(() -> {
			UIUtils.initializeUI();
		});
	}
}
