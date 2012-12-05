package net.codersation.goos;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

	private final AuctionEventListner listner;

	public AuctionMessageTranslator(AuctionEventListner listner) {
		this.listner = listner;
	}

	public void processMessage(Chat unusedChat, Message message) {
		HashMap<String, String> event = unpackEventFrom(message);

		String type = event.get("Event");
		switch (type) {
		case "CLOSE":
			listner.auctionClosed();
			break;
		case "PRICE":
			listner.currentPrice(Integer.parseInt(event.get("CurrentPrice")), Integer.parseInt(event.get("Increment")));
			break;
		default:
			break;
		}
	}

	private HashMap<String, String> unpackEventFrom(Message message) {
		HashMap<String, String> event = new HashMap<>();
		for (String element : message.getBody().split(";")) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		return event;
	}
}
