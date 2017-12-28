package com.app.coinally.in;

import android.app.Application;


import com.app.coinally.in.Utils.Constants;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
