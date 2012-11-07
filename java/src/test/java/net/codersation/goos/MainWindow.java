package net.codersation.goos;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String MAIN_WINOW_NAME = null;
	private static final String STATUS_JOINING = "Joining";
	protected static final String STATUS_LOST = "Lost";
	private final JLabel sniperStatus = createLabel(STATUS_JOINING);

	public MainWindow() {
		super("Auction Sniper");
		setName(MAIN_WINOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(sniperStatus);
		pack();
		setVisible(true);
	}

	private JLabel createLabel(String initialText) {
		JLabel result = new JLabel(initialText);
		result.setName(Main.SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}

	public void showStatus(String status) {
		sniperStatus.setText(status);
	}
}
