//package com.question_generator.webClient;
//
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandler;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
//@Component
//public class WebSocketClientService {
//
//    private WebSocketStompClient stompClient;
//    private StompSession session;
//
//    public WebSocketClientService() {
//        StandardWebSocketClient client = new StandardWebSocketClient();
//        this.stompClient = new WebSocketStompClient(client);
//    }
//
//    public void connectToServiceA() {
//        stompClient.connect("ws://service-a-url/ws", new StompSessionHandlerAdapter() {
//            @Override
//            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//                WebSocketClientService.this.session = session;
//                session.subscribe("/topic/data", new CustomWebSocketHandler());
//            }
//        });
//    }
//
//}
