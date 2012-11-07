package net.codersation.goos;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Main {
	private MainWindow ui;

	public static final String MAIN_WINDOW_NAME = null;
	public static final String SNIPER_STATUS_NAME = null;

	public Main() throws Exception {
		startUserInterface();
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow();
			}
		});
	}
}
