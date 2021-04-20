package app.ServerConnection;

import app.DTO.*;
import app.ServerConnection.Exception.ServerNotResponseException;

import java.util.Date;
import java.util.List;

public interface RestConnection {
    public static final  String STANTIONS_CONTROLLER_URL="gServer";
    public static final String SUBSCRIBE="subscribe";
    public static final String UNSUBSCRIBE="unsubscribe";
    public static final String GET_ALL="getStantions";
    public static final String GET_SUBSCRIBE="getSubscribe";
    public static final String ADD_STANTION="AddStantion";
    public static final String REMOVE_STANTION="RemoveStantion/{id}";
    public static final String GET_STANTION_BY_ID="getStantion/{id}";
    public static final  String GET_CLIENTS="clients";

    public static final  String SERVICE_CONTROLLER_URL="Service";
    public static final String START="start";
    public static final String STOP="stop";
    public static final String STATUS="status";

    public static final  String GET_PAGE_SPEACH="Speach/page";
    public static final  String GET_PAGE_SPEACH_WITH_ID_LESS="Speach/pageWithIdLess";
    public static final  String GET_ALL_BY_PERIOD="Speach/getAllByPeriod";



    void test() throws ServerNotResponseException;
    void subscribe(Long stantionDtoId,String userUUID) throws ServerNotResponseException;
    void unsubscribe(String userUUID) throws ServerNotResponseException;
    List<StantionDto> getAllStantions() throws ServerNotResponseException;
    void addStantion(Stantion stantion) throws ServerNotResponseException;
    void removeStantion(Long stantionDtoId) throws ServerNotResponseException;
    Stantion getStantionById(Long id) throws ServerNotResponseException;
    void startMonitoring() throws ServerNotResponseException;
    void stopMonitoring() throws ServerNotResponseException;
    StantionSpeachDTO getPage(PageStantionIdDto pageStantionIdDto) throws ServerNotResponseException;
    StantionSpeachDTO getPageLessId(PageStantionIdDto pageStantionIdDto) throws ServerNotResponseException;
    StantionSpeachDTO getAllByPeriod(Date start,Date end,Long stantionId) throws ServerNotResponseException;
    Boolean getMonitoringStatus() throws ServerNotResponseException;

    List<Client> getClients() throws ServerNotResponseException;


}
