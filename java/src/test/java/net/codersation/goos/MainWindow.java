package net.codersation.goos;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	private static final String MAIN_WINOW_NAME = null;

	public MainWindow() {
		super("Auction Sniper");
		setName(MAIN_WINOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
