package app.ServerConnection.SessionHandler;

import app.DTO.StantionDto;
import app.ServerConnection.SessionHandler.Transfer.StompMultipleTransfer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.Arrays;

@Log4j
@Data
public class StantionDtoStompSessionHandler extends StompSessionHandlerAdapter {
    private ObservableList<StantionDto> stantionDtoStompMultipleTransfer;
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return StantionDto[].class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
       // log.debug("Прибыли станции");
        StantionDto[] stantionDto=(StantionDto[])payload;

        Platform.runLater(()->{
            stantionDtoStompMultipleTransfer.clear();
            stantionDtoStompMultipleTransfer.addAll(Arrays.asList(stantionDto));
        });

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.debug("Подключен обработчик станций (websocket)");
    }


}
