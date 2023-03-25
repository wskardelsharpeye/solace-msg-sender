package com.example.demo.service;

import com.solacesystems.jcsmp.*;

public class SendService {

    private final XMLMessageProducer producer;

    public SendService(XMLMessageProducer producer) {
        this.producer = producer;
    }

    public void sendToQueue(String queue, long ttl, int priority) throws JCSMPException {
        final Queue q = JCSMPFactory.onlyInstance().createQueue(queue);
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Hello world!";
        msg.setText(text);
        msg.setPriority(priority);
        msg.setTimeToLive(ttl);
        System.out.printf("About to send message '%s' to queue '%s' with priority '%s'...%n",text,q.getName(), priority);
        producer.send(msg,q);
    }
}
