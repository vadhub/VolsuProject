package com.vad.volsuproject.socketresponse;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class Client {

//    private String address = "217.149.179.104";
//    private int PORT = 80;

    private String URL = "wss://volsu.ru/struct/administrative/it/apiget.php/websocket";

    public void getMessage(){
        try {
            WebSocket webSocket = new WebSocketFactory().createSocket(URL);

            System.out.println(webSocket.getURI()+" "+webSocket.getSocket()+" kk");
            webSocket.connect();
            webSocket.addListener(new WebSocketAdapter(){
                @Override
                public void onTextMessage(WebSocket websocket, String text){
                    System.out.println(text);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

}
