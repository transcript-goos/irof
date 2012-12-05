package test.auctionsniper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import net.codersation.goos.AuctionEventListner;
import net.codersation.goos.AuctionMessageTranslator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {

	private final Mockery context = new Mockery();
	private final AuctionEventListner listner = context.mock(AuctionEventListner.class);
	public static final Chat UNUSED_CHAT = null;
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listner);

	@Test
	public void notifiesAuctionClosedWhenCloseMessageRecieved() throws Exception {
		context.checking(new Expectations(){{
			oneOf(listner).auctionClosed();
		}});

		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");

		translator.processMessage(UNUSED_CHAT, message);
	}
}
