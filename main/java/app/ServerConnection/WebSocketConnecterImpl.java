package app.ServerConnection;

import app.DTO.Client;
import app.DTO.StantionDto;
import app.DTO.StantionSpeachDTO;
import app.ServerConnection.SessionHandler.IndificateStompSessionHandler;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Data
@Log4j
public class WebSocketConnecterImpl implements  WebsocketConnection {
    private static final String ENDPOINT="ws";
    private static final String METHOD="ws";
    private Server server;
    private WebSocketStompClient stompClient;
    private StompSession session;
    private IndificateStompSessionHandler sessionHandler;

  //  private StompMultipleTransfer<Client> clientsStompMultipleTransfer;

    @Override
    public boolean getConnectionStatuse() {
        if (session!=null)
        return session.isConnected();
        else return false;
    }

    @Override
    public void connect() throws ExecutionException, InterruptedException, MalformedURLException {

       // List<Transport> transports = new ArrayList<>();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
        container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
       // transports.add(new WebSocketTransport(new StandardWebSocketClient(container)));
       // WebSocketClient webSocketClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(new StandardWebSocketClient(container));
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());



        URL hostURL=server.toURL();
        session = stompClient.connect
                (METHOD+"://"+hostURL.getHost()+":"+hostURL.getPort()+"/"+ENDPOINT,sessionHandler)
                .get();


    }

    @Override
    public void disconnect() {
        session.disconnect();
    }

    @Override
    public void changeState() throws ExecutionException, InterruptedException, MalformedURLException {
        if (getConnectionStatuse()) disconnect();
        else connect();
    }

}
