package app.ServerConnection.Serializators;


import app.ServerConnection.Server;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Data
@Slf4j
public class JSONSerializator<T> {
//private  String file_name="server.json";
private  String file_name;
    private Class<T> type;
private Path path;
public JSONSerializator(String file, Class<T> tClass){
    this.file_name=file;
    type=tClass;
}

    public void serialize(T data) {
        log.debug("Начало сохранения в файл"+file_name);
        try {
            initFile();
            SaveSettings(data);

        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Ошибка создания файла "+file_name);
        }
    }

    public T deserialize() throws Exception {

        log.debug("Начало загрузки сервера");
        path=Paths.get(file_name);
            if (Files.exists(path))
            return new Gson().fromJson(GetSettingsFile(),type);
           else {
                log.warn("ошибка. Нет файла "+file_name);
                throw  new IOException("Ошибка десериализации");
            }

    }
    private void initFile() throws IOException {
        path= Paths.get(file_name);
        if (Files.exists(path)) Files.delete(path);
        Files.createFile(path);
    }
    private  String GetSettingsFile() {
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{}";
        }
    }

    private   void SaveSettings(T data) {
        ArrayList<String> jsonString = new ArrayList<String>();
        jsonString.add(new Gson().toJson(data));
        try {

            Files.write(path, jsonString, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
