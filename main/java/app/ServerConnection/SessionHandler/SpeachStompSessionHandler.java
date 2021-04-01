package app.ServerConnection.SessionHandler;

import app.DTO.StantionSpeachDTO;
import app.ServerConnection.SessionHandler.Transfer.StompMultipleTransfer;
import app.ServerConnection.SessionHandler.Transfer.StompSingleTransfer;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Log4j
@Data
public class SpeachStompSessionHandler extends StompSessionHandlerAdapter {
    private StompSingleTransfer<StantionSpeachDTO> speachStompSingleTransfer;

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return StantionSpeachDTO.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        StantionSpeachDTO stantionSpeachDTO=(StantionSpeachDTO)payload;
        log.debug("Получены данные!" + stantionSpeachDTO);
        speachStompSingleTransfer.transfer(stantionSpeachDTO);

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
       log.debug("Подключено получение речи");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
      //log.warn("Исключение при передачи речи (websocket) ");
    }
}
