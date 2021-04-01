package app.ServerConnection.SessionHandler.Transfer;

import java.util.List;

public interface StompMultipleTransfer<T> {
    void transfer(List<T> data);
}
