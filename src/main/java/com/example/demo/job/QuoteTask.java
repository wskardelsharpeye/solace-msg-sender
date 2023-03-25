package com.example.demo.job;

import com.solacesystems.jcsmp.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Component
public class QuoteTask {

    private final XMLMessageProducer xmlMessageProducer;

    private final JCSMPSession jcsmpSession;

    private static final Logger log = LoggerFactory.getLogger(QuoteTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 1000)
    public void sendToTopic() throws JCSMPException {
        /** Anonymous inner-class for handling publishing events */
        XMLMessageProducer prod = jcsmpSession.getMessageProducer(new JCSMPStreamingPublishCorrelatingEventHandler() {
            @Override
            public void responseReceivedEx(Object key) {
                System.out.println("Producer received response for msg: " + key.toString());
            }

            @Override
            public void handleErrorEx(Object key, JCSMPException cause, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n", key.toString(), timestamp, cause);
            }
        });

        final Topic topic = JCSMPFactory.onlyInstance().createTopic("topic/test");
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Hello world!";
        int priority = RandomUtils.nextInt(0,10);
        msg.setText(text);
        msg.setPriority(priority);
        System.out.printf("About to send message '%s' to topic '%s' with priority '%s'...%n",text,topic.getName(), priority);
        prod.send(msg,topic);

    }

    //@Scheduled(fixedRate = 100, initialDelay = 2000)
    public void sendToQueue() throws JCSMPException {
        final Queue queue = JCSMPFactory.onlyInstance().createQueue("queue/test");
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Hello world!";
        int priority = RandomUtils.nextInt(0,10);
        msg.setText(text);
        msg.setPriority(priority);
        msg.setTimeToLive(100000);
        System.out.printf("About to send message '%s' to queue '%s' with priority '%s'...%n",text,queue.getName(), priority);
        xmlMessageProducer.send(msg,queue);
    }
}
