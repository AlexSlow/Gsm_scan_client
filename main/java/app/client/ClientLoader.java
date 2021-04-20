package app.client;

import app.DTO.Client;
import app.ServerConnection.Serializators.JSONSerializator;
import lombok.Data;

/**
 * Загрузка текущего клиента
 */
@Data
public class ClientLoader {
private static final String fileClient="curent client.json";
private Client curentClient;
public ClientLoader(Client client){
    curentClient=client;
}
   public void loadCurrentUser(){
        JSONSerializator<Client> jsonSerializator=new JSONSerializator<>(fileClient,Client.class);
        try {
            curentClient=jsonSerializator.deserialize();
        } catch (Exception e) {
            Client client=new Client();
            client.setName("Без имени");
            curentClient=client;
        }
    }
  public   void saveClient(){
        JSONSerializator<Client> jsonSerializator=new JSONSerializator<>(fileClient,Client.class);
        try {
            jsonSerializator.serialize(curentClient);
        } catch (Exception e) {

         e.printStackTrace();
        }
    }
}
