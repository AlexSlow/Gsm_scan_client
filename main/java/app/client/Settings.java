package app.client;

import app.DTO.Stantion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранятся подключенные станции
 */
@Data
public class Settings {

	public List<Stantion> ServerList=new ArrayList<>();

	public int TimeOut;

	public int TimeQuery;

}
