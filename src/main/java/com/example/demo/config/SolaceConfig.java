package com.example.demo.config;

import com.example.demo.event.PublishEventHandler;
import com.solacesystems.jcsmp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SolaceConfig {

    private JCSMPSession session;

    @Value("${solace.host}")
    private String solaceHost;

    @Value("${solace.username}")
    private String solaceUsername;

    @Value("${solace.password}")
    private String solacePassword;

    @Value("${solace.vpn}")
    private String solaceVpn;


    private JCSMPSession buildSession() throws JCSMPException {
        System.out.println("session initializing...");
        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, solaceHost);     // host:port
        properties.setProperty(JCSMPProperties.USERNAME, solaceUsername); // client-username
        properties.setProperty(JCSMPProperties.PASSWORD, solacePassword); // client-password
        properties.setProperty(JCSMPProperties.VPN_NAME,  solaceVpn); // message-vpn
        properties.setProperty(JCSMPProperties.REAPPLY_SUBSCRIPTIONS, true);  // re-subscribe Direct subs after reconnect

        JCSMPChannelProperties channelProps = new JCSMPChannelProperties();
        channelProps.setConnectRetries(3);
        channelProps.setConnectRetriesPerHost(3);
        channelProps.setConnectTimeoutInMillis(5000);

        channelProps.setReconnectRetries(150);
        channelProps.setReconnectRetryWaitInMillis(6000);

        properties.setProperty(JCSMPProperties.CLIENT_CHANNEL_PROPERTIES, channelProps);

        this.session = JCSMPFactory.onlyInstance().createSession(properties);
        this.session.connect();
        return session;
    }

    @Bean
    public JCSMPSession getSession() throws JCSMPException {
        return buildSession();
    }

    @Bean
    public XMLMessageProducer getProducer(@Autowired JCSMPSession session) throws JCSMPException {
        return session.getMessageProducer(new PublishEventHandler());
    }
}