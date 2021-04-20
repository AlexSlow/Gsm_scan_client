package app.ServerConnection.SessionHandler;

import app.DTO.Client;
import app.DTO.StantionDto;
import app.ServerConnection.SessionHandler.Transfer.StompMultipleTransfer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import lombok.Data;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Consumer;
@Data
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    private ObservableList<Client> stompMultipleTransfer;

    @Override
    public Type getPayloadType (StompHeaders headers){
        return Client[].class;
    }

    @Override
    public void handleFrame (StompHeaders headers, Object payload){
        Client[] clients=(Client[])payload;
        Platform.runLater(()-> {
            stompMultipleTransfer.clear();
            stompMultipleTransfer.addAll(Arrays.asList(clients));
        });

    }

    @Override
    public void afterConnected (StompSession session, StompHeaders connectedHeaders){

    }

    @Override
    public void handleException (StompSession session, StompCommand command, StompHeaders headers,
    byte[] payload, Throwable exception){
    }
}
