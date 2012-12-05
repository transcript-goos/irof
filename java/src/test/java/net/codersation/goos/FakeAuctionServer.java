package net.codersation.goos;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer {

	public static final String XMPP_HOSTNAME = "localhost";
	private static final String ITEM_ID_AS_LOGIN = "auction-%s";
	private static final String AUCTION_PASSWORD = "auction";
	private static final String AUCTION_RESOURCE = "Auction";
	private final String itemId;
	private XMPPConnection connection;
	private Chat currentChat;
	private final SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
		messageListener.receiveAMessage(is(anything()));
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(new Message());
	}

	public void stop() {
		connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		currentChat.sendMessage(String
				.format("SOLVersion: 1.1; Event: ORICE; Currentprice: %d; Increment: %d; Bidder: %s;", price,
						increment, bidder));
	}

	public void hasRecievedBid(int bid, String sniperId) throws InterruptedException {
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
		messageListener.receiveAMessage(equalTo(String.format("SOLVersion: 1.1; Command: BID; price: %d;", bid)));
	}

	public static class SingleMessageListener implements MessageListener {

		private final ArrayBlockingQueue<Message> messsages = new ArrayBlockingQueue<>(1);

		@Override
		public void processMessage(Chat chat, Message message) {
			messsages.add(message);
		}

		public void receiveAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
			final Message messsage = messsages.poll(5, TimeUnit.SECONDS);
			assertThat("Message", messsage, is(notNullValue()));
			assertThat(messsage.getBody(), messageMatcher);
		}
	}
}
