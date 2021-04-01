package app.ServerConnection.Exception;

public class ServerNotConnected extends Exception {
    public ServerNotConnected(){
        super();
    }
    public ServerNotConnected(String str){
        super(str);
    }
}
