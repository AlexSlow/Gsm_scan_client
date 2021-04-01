package app.ServerConnection.Exception;

public class ServerNotResponseException extends Exception {
    public ServerNotResponseException(){
        super();
    }
    public ServerNotResponseException(String str){
        super(str);
    }
}
