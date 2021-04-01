package app;




import app.ServerConnection.ServerConnectionManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Log4j
public class SpringApplication {

    @Autowired private FXApplication fxApplication;

    public static final String PREFFIX = "/app/";
    public static final String ADD_IFORMATION_ABOUT_CLIENT = "/addInformationOboutClient";
    public static final String SUBSCRIBE = "/subscribe";
    public static final String UNSUBSCRIBE = "/unsubscribe";

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication application = new org.springframework.boot.SpringApplication(SpringApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);

    }

    @PostConstruct
    public void init() throws Exception {

       log.info("Запус Spring- слиента");
      // FXApplication fxApplication=new FXApplication();
      // fxApplication.init();

    }
}

