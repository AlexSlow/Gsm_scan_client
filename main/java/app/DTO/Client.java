package app.DTO;

import lombok.Data;

import java.io.Serializable;


@Data
public class Client implements Serializable {
    public Client(){}
    public Client(String UUID){ this.UUID=UUID;}
    private String UUID;
    private String name;
    private Integer lon;
    private Integer lat;
}
