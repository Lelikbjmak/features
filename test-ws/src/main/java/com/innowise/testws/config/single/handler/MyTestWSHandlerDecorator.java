package com.innowise.testws.config.single.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

@Slf4j
@Component
public class MyTestWSHandlerDecorator extends WebSocketHandlerDecorator {

  public MyTestWSHandlerDecorator(MyTextWSHandler delegate) {
    super(delegate);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    var protocol = session.getAcceptedProtocol();
    var sender = session.getPrincipal();
    var id = session.getId();
    var handshakeHeaders = session.getHandshakeHeaders();

    log.info("Protocol: {}, Id: {}", protocol, id);
    super.handleMessage(session, message);
  }
}
