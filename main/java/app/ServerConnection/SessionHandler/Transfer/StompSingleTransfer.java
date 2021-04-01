package app.ServerConnection.SessionHandler.Transfer;

public interface StompSingleTransfer<T>{
    void transfer(T data);
}
