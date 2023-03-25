package com.example.demo.controller;

import com.solacesystems.jcsmp.XMLMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SenderController {

    @Autowired
    private XMLMessageProducer xmlMessageProducer;

}
