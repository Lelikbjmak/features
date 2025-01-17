package com.innowise.testws.config.single.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class MyTextWSHandler extends TextWebSocketHandler {

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    session.sendMessage(new TextMessage(message.getPayload()));
  }
}
