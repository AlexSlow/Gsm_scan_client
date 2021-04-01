package app.ServerConnection.SessionHandler;

import app.DTO.StantionSpeachDTO;
import app.ServerConnection.SessionHandler.Transfer.StompSingleTransfer;
import lombok.Data;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.function.Consumer;

@Data
public class MonitoringStatusHandler extends StompSessionHandlerAdapter {
    private Consumer<Boolean> onGetMonitoringStatusCallback;

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Boolean.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Boolean status=(Boolean) payload;
        onGetMonitoringStatusCallback.accept(status);

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    }
}
