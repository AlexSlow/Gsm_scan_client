package app.ServerConnection.SessionHandler;

import app.DTO.Client;
import app.DTO.StantionDto;
import app.DTO.StantionSpeachDTO;
import app.ServerConnection.CallBack;
import app.ServerConnection.SessionHandler.Transfer.StompMultipleTransfer;

import app.ServerConnection.SessionHandler.Transfer.StompSingleTransfer;
import app.ServerConnection.Topics;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;


@Log4j
@Data
public class IndificateStompSessionHandler extends StompSessionHandlerAdapter {
    private Client currentClient;
    private CallBack onClose;

    private StompMultipleTransfer<StantionDto> stantionsStompMultipleTransfer;
    private StompSingleTransfer<StantionSpeachDTO> speachStompSingleTransfer;
    private MonitoringStatusHandler monitoringStatusHandler;

   // private StompSingleTransfer<Client> clientStompSingleTransfer;
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

        synchronized (session){
            //Топик данного пользователя
            session.subscribe(Topics.SELF_CLIENT, this);
            session.send(Topics.PREFFIX+Topics.ADD_NFORMATION_ABOUT_CLIENT,currentClient);
            //Произвести инициализацию пользователя;
            //Произвести подписку клиента на изменение станций и клиентов
            //Подписка на личный топик
            StantionDtoStompSessionHandler stantionDtoStompSessionHandler=new StantionDtoStompSessionHandler();
            stantionDtoStompSessionHandler.setStantionDtoStompMultipleTransfer(stantionsStompMultipleTransfer);
            session.subscribe(Topics.TOPIC_STANTION_DTO,stantionDtoStompSessionHandler);

            SpeachStompSessionHandler speachStompSessionHandler=new SpeachStompSessionHandler();
            speachStompSessionHandler.setSpeachStompSingleTransfer(speachStompSingleTransfer);
            session.subscribe(Topics.SELF_SPEACH,speachStompSessionHandler);

            session.subscribe(Topics.TOPIC_MONITORING_STATUS,monitoringStatusHandler);
        }
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.warn("Закрытие соединения");
        onClose.callBack();
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers,
                                byte[] payload, Throwable exception) {
        log.error("Got an exception", exception);
        onClose.callBack();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Client.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    Client c=(Client) payload;
    currentClient.setUUID(c.getUUID());
      log.info("Получен свой UUID" + currentClient.getUUID());
      //clientStompSingleTransfer.transfer(currentClient);
    }
}
