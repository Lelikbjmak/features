package com.innowise.testws.config.multi;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TestWSBrokerController {

  @MessageMapping("/updatePortfolio")
  @SendTo("/topic/portfolio")
  public String updatePortfolio(String message) {
    return "Portfolio updated: " + message;
  }
}
