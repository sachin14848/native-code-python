//package com.question_generator.webClient;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.lang.reflect.Type;
//
//@Slf4j
//public class CustomWebSocketHandler implements StompFrameHandler {
//
//    @Override
//    public Type getPayloadType(StompHeaders headers) {
//        log.info("getPayloadType called {}", headers);
//        return null;
//    }
//
//    @Override
//    public void handleFrame(StompHeaders headers, Object payload) {
//        log.info("handleFrame called {}", headers);
//        TextMessage message = (TextMessage) payload;
//        String content = message.getPayload().toString();
//        log.info("Received message: {}", content);
//    }
//}