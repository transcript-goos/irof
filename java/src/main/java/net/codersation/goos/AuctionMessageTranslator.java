package net.codersation.goos;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

	private final AuctionEventListner listner;

	public AuctionMessageTranslator(AuctionEventListner listner) {
		this.listner = listner;
	}

	public void processMessage(Chat unusedChat, Message message) {
		listner.auctionClosed();
	}
}
