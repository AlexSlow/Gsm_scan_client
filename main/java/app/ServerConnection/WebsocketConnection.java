package app.ServerConnection;

import app.DTO.Stantion;

import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface WebsocketConnection {
    boolean getConnectionStatuse();
    void changeState() throws ExecutionException, InterruptedException, MalformedURLException;
    void connect() throws ExecutionException, InterruptedException, MalformedURLException;
    void disconnect();
}
