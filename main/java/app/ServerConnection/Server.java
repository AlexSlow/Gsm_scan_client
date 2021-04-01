package app.ServerConnection;

import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

@Data
public class Server {
    String host;
    public URL toURL() throws MalformedURLException {
        return new URL(host);
    }
}
