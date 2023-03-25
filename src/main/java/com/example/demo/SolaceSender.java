package com.example.demo;

import com.example.demo.config.AppConfig;
import com.example.demo.dto.Option;
import com.example.demo.utils.RequestBuilder;
import com.solacesystems.jcsmp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class SolaceSender implements CommandLineRunner {

    private final XMLMessageProducer xmlMessageProducer;
    private final AppConfig appConfig;

    public SolaceSender(XMLMessageProducer xmlMessageProducer, AppConfig appConfig) {
        this.xmlMessageProducer = xmlMessageProducer;
        this.appConfig = appConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(SolaceSender.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("how many rfq need to generate?: ");
            String input = scanner.nextLine();
            System.out.println("destination?: ");
            String queue = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                exit = true;
            } else {
                for (int i = 1; i <= Integer.valueOf(input); i++) {
                    try {
                        buildAndSend(queue);
                    } catch (JCSMPException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void buildAndSend(String q) throws JCSMPException {
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(q);
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        List<Option> rfqs = RequestBuilder.build();
        int randomIndex = RandomUtils.nextInt(0, rfqs.size());
        String text = rfqs.get(randomIndex).toString();
        int priority = RandomUtils.nextInt(0,10);
        int ttl = RandomUtils.nextInt(1,10);
        msg.setText(text);
        msg.setPriority(priority);
        msg.setTimeToLive(ttl);
        msg.setDeliveryMode(DeliveryMode.PERSISTENT);
        log.info("sending rfq: {} to queue {}. priority {} ttl: {}",text, queue.getName(), priority, ttl);
        xmlMessageProducer.send(msg,queue);
    }
}

