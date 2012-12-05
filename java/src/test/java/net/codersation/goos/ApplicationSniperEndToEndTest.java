package net.codersation.goos;

import org.junit.After;
import org.junit.Test;

public class ApplicationSniperEndToEndTest {

	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();

	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
		// Step 1
		auction.startSellingItem();
		// Step 2
		application.startBiddingIn(auction);
		// Step 3
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		// Step 4
		auction.announceClosed();
		// Step 5
		application.showsSniperHasLostAuction();
	}

	@Test
	public void sniperMakesAHigherBitButLoses() throws Exception {
		auction.startSellingItem();

		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

		auction.reportPrice(1000, 98, "other bidder");

		application.hasShownSniperIsBidding();
		auction.hasRecievedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	// Additional cleanup
	@After
	public void stopAuction() {
		auction.stop();
	}

	@After
	public void stopApplication() {
		application.stop();
	}
}
