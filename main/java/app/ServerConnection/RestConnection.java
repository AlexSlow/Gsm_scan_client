package app.ServerConnection;

import app.DTO.*;
import app.ServerConnection.Exception.ServerNotResponseException;

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

    public static final  String SERVICE_CONTROLLER_URL="Service";
    public static final String START="start";
    public static final String STOP="stop";
    public static final String STATUS="status";

    public static final  String GET_PAGE_SPEACH="Speach/page";
    public static final  String GET_PAGE_SPEACH_WITH_ID_LESS="Speach/pageWithIdLess";

    void test() throws ServerNotResponseException;
    void subscribe(Integer stantionDtoId,String userUUID) throws ServerNotResponseException;
    void unsubscribe(String userUUID) throws ServerNotResponseException;
    List<StantionDto> getAllStantions() throws ServerNotResponseException;
    void addStantion(Stantion stantion) throws ServerNotResponseException;
    void removeStantion(Integer stantionDtoId) throws ServerNotResponseException;
    Stantion getStantionById(Integer id) throws ServerNotResponseException;
    void startMonitoring() throws ServerNotResponseException;
    void stopMonitoring() throws ServerNotResponseException;
    StantionSpeachDTO getPage(PageStantionIdDto pageStantionIdDto) throws ServerNotResponseException;
    StantionSpeachDTO getPageLessId(PageStantionIdDto pageStantionIdDto) throws ServerNotResponseException;
    Boolean getMonitoringStatus() throws ServerNotResponseException;
}
