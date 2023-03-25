package com.example.demo.event;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPStreamingPublishCorrelatingEventHandler;

public class PublishEventHandler implements JCSMPStreamingPublishCorrelatingEventHandler
{
    @Override
    public void responseReceivedEx(Object key) {
        System.out.println("Producer received response");
    }

    @Override
    public void handleErrorEx(Object key, JCSMPException cause, long timestamp) {
        System.out.printf("Producer received error for msg: %s@%s - %s%n", key.toString(), timestamp, cause);
    }
}
