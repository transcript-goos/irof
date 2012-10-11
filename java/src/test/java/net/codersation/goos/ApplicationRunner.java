package net.codersation.goos;

public class ApplicationRunner {

	protected static final String XMPP_HOSTNAME = null;
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	private static final String STATUS_JOINING = null;
	private static final String STATUS_LOST = null;
	private AuctionSniperDriver driver;

	public void startBiddingIn(final FakeAuctionServer auction) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showsSniperStatus(STATUS_JOINING);
	}

	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(STATUS_LOST);
	}

	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}

}
