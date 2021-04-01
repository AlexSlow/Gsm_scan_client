package app;

import app.ServerConnection.ServerConnectionManager;
import app.client.Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
@Component
@Log4j
public class FXApplication extends Application {

    @PostConstruct
    @Override
    public void init() throws Exception {
        super.init();

        log.info("Инициализация FX клиента");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("Остановка FX клиента");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        log.info("Запуск FX клиента");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(getController());
        URL xmlUrl = getClass().getResource("/fxml/Main.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setTitle("СПО КБС");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    MainController getController(){
        MainController mainController=new MainController();
        return mainController;
    }
}
