package net.codersation.goos;

public interface AuctionEventListner {
	public void auctionClosed();
	public void currentPrice(int price, int increment);
}
