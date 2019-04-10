package com.example.nadavlotan.autodemo;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class TextFetcher {

    private Socket mSocket;
    //    private static final String URL = "https://abcmain.nadavlotan6.now.sh:3000";
    private static final String URL = "http://10.0.2.2:3000";
//    private static final String URL = "http://10.10.75.37:3000";

    {
        try {
            mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public Socket getmSocket() {
        return mSocket;
    }
}
