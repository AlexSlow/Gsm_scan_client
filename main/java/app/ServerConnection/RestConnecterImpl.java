package app.ServerConnection;


import app.DTO.*;
import app.ServerConnection.Exception.ServerNotResponseException;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Log4j
public class RestConnecterImpl implements RestConnection {



    private RestTemplate restTemplate;
    private Server server;

    RestConnecterImpl(){
        init();
    }
    void init(){
        restTemplate=new RestTemplate();
    }

    @Override
    public void test() throws ServerNotResponseException {
        try {
            HttpHeaders jsonHeader=new HttpHeaders();
            jsonHeader.setContentType(MediaType.APPLICATION_JSON);

            UriComponents uriComponents =
                    UriComponentsBuilder.fromHttpUrl(server.getHost()).path("testServer").encode().build();
            ResponseEntity<?> responseGS
                    = restTemplate.exchange(uriComponents.toUri(),HttpMethod.GET,new HttpEntity<>(null,jsonHeader), Void.class);
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=new ServerNotResponseException("Исключение теста соединения");
            serverNotResponseException.initCause(e);
          // e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }
    }

    @Override
    public void subscribe(Integer stantionDtoId, String userUUID) throws ServerNotResponseException {
        try {
            LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("stantionId", stantionDtoId);
            params.add("userUUID", userUUID);
            //add array
            UriComponentsBuilder builder =
            UriComponentsBuilder.fromHttpUrl(server.getHost()+"/"+STANTIONS_CONTROLLER_URL+"/"+SUBSCRIBE);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(params, headers);

            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);
           log.debug("Пользователь "+userUUID+" подписался на станцию "+stantionDtoId);
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=
                    new ServerNotResponseException("Исключение при подписке");
            serverNotResponseException.initCause(e);
            e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }

    }
    @Override
    public void unsubscribe(String userUUID) throws ServerNotResponseException {
        try {
            LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("userUUID", userUUID);
            UriComponentsBuilder builder =
                    UriComponentsBuilder.
                            fromHttpUrl(server.getHost()+"/"+STANTIONS_CONTROLLER_URL
                                    +"/"+UNSUBSCRIBE);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(params, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);
            log.debug("Пользователь "+userUUID+" произвел отписку ");
        } catch (Exception e){
        ServerNotResponseException serverNotResponseException=
                new ServerNotResponseException("Исключение при отписке");
        serverNotResponseException.initCause(e);
        e.printStackTrace();
        log.warn(e.getMessage());
        throw serverNotResponseException;
    }

    }

    @Override
    public List<StantionDto> getAllStantions() throws ServerNotResponseException {
        try {
            UriComponentsBuilder builder =
                    UriComponentsBuilder.
                            fromHttpUrl(server.getHost()+"/"+STANTIONS_CONTROLLER_URL+"/"+GET_ALL);
            ResponseEntity<StantionDto[]> response
                    = restTemplate.getForEntity(builder.build().encode().toUri(), StantionDto[].class);
            return Arrays.asList(response.getBody());
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=
                    new ServerNotResponseException("Исключение при получении станций");
            serverNotResponseException.initCause(e);
            e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }

    }

    @Override
    public void addStantion(Stantion stantion) throws ServerNotResponseException {
        try {
            log.debug("Создание станции");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponents uriComponents=UriComponentsBuilder.fromHttpUrl(server.getHost())
                    .path(STANTIONS_CONTROLLER_URL+"/"+ADD_STANTION).build();
            HttpEntity<Stantion> httpEntity=new HttpEntity<>(stantion,headers);
            restTemplate.exchange(uriComponents.encode().toUri(),HttpMethod.PUT,httpEntity,Void.class);
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=
            new ServerNotResponseException("Исключение при создании станции");
            serverNotResponseException.initCause(e);
            e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }

    }

    @Override
    public void removeStantion(Integer stantionDtoId) throws ServerNotResponseException {
        try {
            log.debug("Удаление станции с id= "+stantionDtoId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponents uriComponents=UriComponentsBuilder.fromHttpUrl(server.getHost())
                    .path(STANTIONS_CONTROLLER_URL+"/"+REMOVE_STANTION)
                    .buildAndExpand(stantionDtoId);
           // System.out.println(uriComponents);
            restTemplate.delete(uriComponents.toUri());
        }  catch (Exception e){
        ServerNotResponseException serverNotResponseException=
                new ServerNotResponseException("Исключение при удалении станции");
        serverNotResponseException.initCause(e);
        e.printStackTrace();
        log.warn(e.getMessage());
        throw serverNotResponseException;
    }

    }

    @Override
    public Stantion getStantionById(Integer id) throws ServerNotResponseException {
        try {
            log.debug("Получение станции с id= "+id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponents uriComponents=UriComponentsBuilder.fromHttpUrl(server.getHost())
                    .path(STANTIONS_CONTROLLER_URL)
                    .path("/"+GET_STANTION_BY_ID)
                    .buildAndExpand(id);
           // System.out.println(uriComponents);
         ResponseEntity<Stantion> stantion=restTemplate.getForEntity(uriComponents.encode().toUri(),Stantion.class);
         return stantion.getBody();
        }  catch (Exception e){
            ServerNotResponseException serverNotResponseException=
                    new ServerNotResponseException("Исключение при получении станции");
            serverNotResponseException.initCause(e);
            e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }
    }

    @Override
    public void startMonitoring() throws ServerNotResponseException {
        try {
            HttpHeaders jsonHeader=new HttpHeaders();
            jsonHeader.setContentType(MediaType.APPLICATION_JSON);

            UriComponents uriComponents =
                    UriComponentsBuilder.fromHttpUrl(server.getHost()).
                            path(SERVICE_CONTROLLER_URL+"/"+START).
                            encode().build();
                    restTemplate.exchange(uriComponents.toUri(),HttpMethod.GET,new HttpEntity<>(null,jsonHeader), Void.class);
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=new ServerNotResponseException("Исключение при попытке запуска старта мониторинга");
            serverNotResponseException.initCause(e);
            // e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }
    }

    @Override
    public void stopMonitoring() throws ServerNotResponseException {
        try {
            HttpHeaders jsonHeader=new HttpHeaders();
            jsonHeader.setContentType(MediaType.APPLICATION_JSON);

            UriComponents uriComponents =
                    UriComponentsBuilder.fromHttpUrl(server.getHost()).
                            path(SERVICE_CONTROLLER_URL+"/"+STOP).
                            encode().build();
            restTemplate.exchange(uriComponents.toUri(),
                    HttpMethod.GET,new HttpEntity<>(null,jsonHeader), Void.class);
        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=new ServerNotResponseException("Исключение теста соединения");
            serverNotResponseException.initCause(e);
            // e.printStackTrace();
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }

    }
    @Override
    public StantionSpeachDTO getPage(PageStantionIdDto pageStantionIdDto)  throws ServerNotResponseException {
        try {

            UriComponentsBuilder builder =
                    UriComponentsBuilder.
                            fromHttpUrl(server.getHost()+"/"+GET_PAGE_SPEACH);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PageStantionIdDto> httpEntity=new HttpEntity<>(pageStantionIdDto,headers);


            ResponseEntity<StantionSpeachDTO> responseEntity = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    httpEntity,
                    StantionSpeachDTO.class);
            return responseEntity.getBody();

        }catch (Exception e){
            ServerNotResponseException serverNotResponseException=new ServerNotResponseException("Исключение при получении страницы");
            serverNotResponseException.initCause(e);
            log.warn(e.getMessage());
            throw serverNotResponseException;
        }

    }
}
