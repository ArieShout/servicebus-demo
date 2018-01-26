package com.microsoft.demo;

import com.microsoft.azure.servicebus.*;

import java.time.Duration;

public class ServiceBusExample {
    private static final String CONNECTION_STRING = "";

    public static void main(String[] args) throws Exception {
//        send();
        receive(null);
    }

    private static void receiveAsync(String sessionId) throws Exception {
        ClientFactory.acceptSessionFromConnectionStringAsync(CONNECTION_STRING, sessionId)
                .thenAccept(ServiceBusExample::receiveFromSession);
    }

    private static void receive(String sessionId) throws Exception {
        IMessageSession session = ClientFactory.acceptSessionFromConnectionString(CONNECTION_STRING, sessionId);
        receiveFromSession(session);
    }

    private static void receiveFromSession(IMessageSession session) {
        try {
            while (true) {
                IMessage message = session.receive(Duration.ofSeconds(10));
                if (message == null) {
                    session.close();
                    break;
                }
                Thread.sleep(1000);
                String body = new String(message.getBody());
                System.out.printf("%s - %s\n", Thread.currentThread().getName(), body);
                session.complete(message.getLockToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send() throws Exception {
        IMessageSender sender = ClientFactory.createMessageSenderFromConnectionString(CONNECTION_STRING);
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 40; ++j) {
                String id = String.valueOf(j * 10 + i);
                Message msg = new Message(String.format("ID = %s (%d), session = %d", id, i, j));
                msg.setMessageId(id);
                msg.setSessionId(String.valueOf(j));
                sender.send(msg);
            }
        }
        sender.close();
    }
}
