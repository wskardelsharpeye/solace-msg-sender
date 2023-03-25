package com.example.demo.subscriber;

import com.solacesystems.jcsmp.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SenderTest {

    private final String HOST = "192.168.3.73";
    private final String USERNAME = "default";
    private final String VPN_NAME = "default";
    private final String PASSWORD = "default";

    @Test
    public void solaceContextTest() throws JCSMPException {
        System.out.println("TopicSubscriber initializing...");
        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, HOST);     // host:port
        properties.setProperty(JCSMPProperties.USERNAME, USERNAME); // client-username
        properties.setProperty(JCSMPProperties.VPN_NAME,  VPN_NAME); // message-vpn

        properties.setProperty(JCSMPProperties.REAPPLY_SUBSCRIPTIONS, true);  // re-subscribe Direct subs after reconnect
        JCSMPChannelProperties channelProps = new JCSMPChannelProperties();
        channelProps.setConnectRetries(3);
        channelProps.setConnectRetriesPerHost(3);
        channelProps.setConnectTimeoutInMillis(5000);

        channelProps.setReconnectRetries(150);
        channelProps.setReconnectRetryWaitInMillis(6000);

        properties.setProperty(JCSMPProperties.CLIENT_CHANNEL_PROPERTIES, channelProps);
        properties.setProperty(JCSMPProperties.PASSWORD, PASSWORD); // client-password

        final Topic topic = JCSMPFactory.onlyInstance().createTopic("tutorial/topic");
        final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);

        // Channel properties
        JCSMPChannelProperties cp = (JCSMPChannelProperties) session
                .getProperty(JCSMPProperties.CLIENT_CHANNEL_PROPERTIES);
        assertEquals(3, (int)cp.getConnectRetries());
        assertEquals(3, (int)cp.getConnectRetriesPerHost());

        assertEquals(150, (int)cp.getReconnectRetries());
        assertEquals(6000, (int)cp.getReconnectRetryWaitInMillis());
        //session.connect();
    }
}

