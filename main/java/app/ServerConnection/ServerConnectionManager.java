package app.ServerConnection;
import app.ServerConnection.Exception.ServerNotResponseException;
import app.ServerConnection.Serializators.JSONSerializator;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

@Service
@Data
@Log4j
/**
 * Содержит crud интерфейс работы с сущностью сервера.
 */
public class ServerConnectionManager {
    private static  final String FILE_SERVER="server.json";

    private RestConnection restConnection;
    private WebsocketConnection websocketConnection;
    Server server;
    JSONSerializator<Server> JSONSerializator =new JSONSerializator(FILE_SERVER,Server.class);
    public void saveServer(){
    JSONSerializator.serialize(server);
    }
    public void loadServer() throws Exception {
    this.server= JSONSerializator.deserialize();
    }
    public ServerConnectionManager(){
        init();
    }
    public void  init() {
        try {
            loadServer();
        } catch (Exception serverNotFound) {
            server=new Server();
            server.setHost("");
            serverNotFound.printStackTrace();
        }

        RestConnecterImpl restConnecter=new RestConnecterImpl();
        restConnecter.setServer(this.server);
        this.restConnection=restConnecter;
    }
    public void tryConnected() throws ServerNotResponseException {
       restConnection.test();
    }
    public void setServer(String URL){
        server.setHost(URL);
    }

}
