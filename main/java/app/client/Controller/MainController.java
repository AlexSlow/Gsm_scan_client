package app.client.Controller;

import app.DTO.*;
import app.ServerConnection.Exception.ServerNotConnected;
import app.ServerConnection.Exception.ServerNotResponseException;
import app.ServerConnection.ServerConnectionManager;
import app.ServerConnection.SessionHandler.IndificateStompSessionHandler;
import app.ServerConnection.SessionHandler.MonitoringStatusHandler;
import app.ServerConnection.SessionHandler.Transfer.SpeachTransfer;
import app.ServerConnection.WebSocketConnecterImpl;
import app.ServerConnection.WebsocketConnection;
import app.client.*;
import app.client.GUI.*;
import app.client.GUI.Charts.MultipleSeriasCharts.*;
import app.client.GUI.Charts.SingleSeriasFrontCharts.*;
import app.client.modal.AlertWindows;
import app.client.modal.ChooseCurrentClient;
import app.client.modal.InputHostModalWindow;
import app.client.modal.ShowSpeachModalWindow;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Component
@Data
@Log4j
public class MainController {

    @FXML
    private MenuItem settings_main;
    @FXML
    private TabPane left_menu;
    @FXML
    private ListView<StantionDto> ServerList;
    @FXML
    private SplitPane tableSplitPane;
    @FXML
    private ToggleButton startStopButton;
    @FXML
    private WebView webMap;
    @FXML
    private TableView<Speach> dataTable;
    @FXML
    private Label textError;
    @FXML
    private ComboBox<SqlSort> sortSelect;
    @FXML
    private BarChart<String, Integer> chartCountry;
    @FXML
    private BarChart<String, Integer> chartOperators;
    @FXML
    private ListView<Client>  ClientListView;
    @FXML
    private FlowPane MonitoringPane;
    private WebEngine webEngine;
    /**
     * Окно статистики
     */


    @FXML private BarChart<String,Integer> statisticBarChartOperator;
    @FXML private BarChart<String,Integer> statisticBarChartCounty;
    @FXML private BarChart<String,Integer> EventChart;
    private MultipleSeriasChartDecorator multipleSeriasChartDecorator=new
            MultipleSeriasChartDecorator();
    @FXML private LineChart<String,Integer> AmountOfAbbonents;
    @FXML private Button btStatisticSettings;

    /**
     * Terehov
     */
    @FXML
    private HBox hboxServerStatusText;
    @FXML
    private Button btServerStart;
    @FXML
    private MenuItem MenuServer;
    @FXML private MenuItem MiCurrentClient;




    // -- ------------------Модель----------------------------------------------------------------------------------------
    //@Autowired
    private ServerConnectionManager serverConnectionManager;
    //------------------- Переменные состояния

    private StantionDto curentStantion;
    private Client curentClient;
    private PageController pageController=new PageController();
    SpeachTransfer speachTransfer;
    private int selectRow = 0;



    void getObserverList(){

      ServerList.setCellFactory(l-> ListCellFactory.getCell());
    }


    @FXML
    protected void initialize() {

        getObserverList();
        loadClient();
     //   stantionListObserver.subscribe(ServerList.getItems());
      //  clientListObserver.subscribe(ClientListView.getItems());


        serverConnectionManager = new ServerConnectionManager();
        serverConnectionManager.setWebsocketConnection(getWebSocketConnection());
        try {
            tryConnected();

        } catch (Exception e) {
            // e.printStackTrace();
        }

        // Map init
        webEngine = webMap.getEngine();
          webEngine.
                  load(this.getClass().getClassLoader().
                          getResource("Maps/Map.html").toExternalForm());


        // Settings init
        settings_main.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
        settings_main.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Открыть настройки
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    SettingsController settingsController = new SettingsController();
                    settingsController.setStantionDtos(ServerList.getItems());
                    settingsController.setServerConnectionManager(serverConnectionManager);
                    loader.setController(settingsController);
                    URL location = getClass().getClassLoader().getResource("fxml/Settings.fxml");
                    //System.out.println(location);
                    loader.setLocation(location);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Настройки");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn(e.getMessage());
                }
            }
        });


        setTableView();


        //Задать значение полю выбора фильтра
        for (SqlSort sort : SqlSort.values()) {
            sortSelect.getItems().add(sort);
        }
        sortSelect.getSelectionModel().select(0);

        //Установка обработчика открытия модального окна
        MenuServer.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent event) {

                InputHostModalWindow inputHostModalWindow = new InputHostModalWindow();
                inputHostModalWindow.setHost(Optional.of(serverConnectionManager.getServer().getHost()));
                inputHostModalWindow.open(serverConnectionManager.getServer().getHost());
                serverConnectionManager.setServer(inputHostModalWindow.getHost().get());
                serverConnectionManager.saveServer();
                tryConnected();
            }
        });
        MiCurrentClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChooseCurrentClient chooseCurrentClient = new ChooseCurrentClient();
                chooseCurrentClient.open(curentClient);
                curentClient = chooseCurrentClient.getClient().get();
                saveClient();

            }
        });
        btServerStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Старт сервера
                CompletableFuture.runAsync(() -> {
                    try {
                        serverConnectionManager.getWebsocketConnection().changeState();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (serverConnectionManager.getWebsocketConnection().
                            getConnectionStatuse())
                        Platform.runLater(() -> {
                            btServerStart.setText("Отключиться от сервера");
                        });
                    else {
                        Platform.runLater(() -> {
                            btServerStart.setText("Подключиться к серверу");
                        });
                        Platform.runLater(() -> {
                            ServerList.getItems().clear();
                            ClientListView.getItems().clear();
                        });

                    }
                    try {
                        tryConnected();

                        //Получить станции
                        Platform.runLater(()->{
                        ServerList.getItems().clear();
                        ClientListView.getItems().clear();
                        try {
                            ServerList.getItems().addAll(serverConnectionManager.
                                    getRestConnection().getAllStantions());
                        } catch (ServerNotResponseException e) {
                            e.printStackTrace();
                        }
                        //Получить клиентов

                        try {
                            ClientListView.getItems().addAll(serverConnectionManager
                                    .getRestConnection().getClients());
                        } catch (ServerNotResponseException e) {
                            e.printStackTrace();
                        }
                    });

                    } catch (ServerNotResponseException e) {
                        e.printStackTrace();
                        //Сервер недоступен
                    } catch (ServerNotConnected serverNotConnected) {
                        //Сервер доступен, но неподключен по websoket
                    }

                });
            }
        });


    dataTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    dataTableClick();
                }
            }
        }
    });
    ServerList.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                  ServerListMouseClick();
                }
            }
        }
    });

        /**
         * Проставить контроллеры для передних графиков на первом окне
         */
    setChartControllers();
    registrSratisticBarCharts();
    onStitisticSettingOpen();

    //isDownloud=true;
    }

    /**
     *  INIT END
     */


    void setChartControllers(){
        CountryXYChartController countryChartController=new CountryXYChartController(chartCountry);

        OperatorXYChartController operatorChartController=new OperatorXYChartController(chartOperators);



        ChartControllerDecorator chartControllerDecorator=
                new ChartControllerDecorator();
        chartControllerDecorator.getChartControllerList().add(countryChartController);
        chartControllerDecorator.getChartControllerList().add(operatorChartController);
        speachTransfer.getChartControllers().add(chartControllerDecorator);

    }
    public void leftMenuMauseEnter() {
        KeyValue widthValue = new KeyValue
                (left_menu.prefWidthProperty(),200, Interpolator.EASE_BOTH );
        KeyFrame frame = new KeyFrame(Duration.millis(150), widthValue);
        Timeline timeline = new Timeline(frame);
        timeline.play();

       // left_menu.setPrefWidth(200);
    }

    public void leftMenuMauseLeave() {

       // left_menu.setPrefWidth(30);
        KeyValue widthValue = new KeyValue
                (left_menu.prefWidthProperty(),30, Interpolator.EASE_BOTH );

        KeyFrame frame = new KeyFrame(Duration.millis(150), widthValue);
        Timeline timeline = new Timeline(frame);
        timeline.play();
    }

    /**
     * Переключение между станциями
     */
    public void ServerListMouseClick() {

        curentStantion = ServerList.getItems().get(ServerList.getSelectionModel().getSelectedIndex());
        pageController.setStnationDtoId(curentStantion.getId());


        try {
           speachTransfer.setEqualsFilter(true);
           speachTransfer.clear();
           serverConnectionManager.getRestConnection()
           .subscribe(curentStantion.getId(),curentClient.getUUID());
           PageStantionIdDto pageStantionIdDto=new PageStantionIdDto(pageController.getPage(),
                   curentStantion.getId());


           speachTransfer.transfer(serverConnectionManager.getRestConnection().getPage
                   (pageStantionIdDto));

           //Обработчик при прокрутке. Может инициироваться только после показа сцены
          // setScrollHandler();


         //  speachTransfer.setEqualsFilter(false);

        } catch (ServerNotResponseException e) {
            e.printStackTrace();
            AlertWindows.alert("Ошибка подписки на данную станцию!","Ошибка");
        }

        log.info("Подписка на сервер");
    }

    public void startStopButtonClick() {
        if (!serverConnectionManager.getWebsocketConnection().getConnectionStatuse()) return;
        CompletableFuture.runAsync(()->{
            if (startStopButton.isSelected()) {
                //Запустить
                try {
                    serverConnectionManager.getRestConnection().startMonitoring();
                } catch (ServerNotResponseException e) {
                    e.printStackTrace();
                    AlertWindows.alert(e.getMessage(),"Ошибка запуска");
                }
            } else {
                //Остановить
                try {
                    serverConnectionManager.getRestConnection().stopMonitoring();
                } catch (ServerNotResponseException e) {
                    e.printStackTrace();
                    AlertWindows.alert(e.getMessage(),"Ошибка остановки");
                }
            }
        });

    }


    public void dataTableClick(){

        selectRow = dataTable.getSelectionModel().getSelectedIndex();
        ShowSpeachModalWindow showSpeachModalWindow=new ShowSpeachModalWindow();
        showSpeachModalWindow.open(dataTable.getItems().get(selectRow));
        //Открыть модальное окно
    }



    public void tryConnected() throws ServerNotResponseException, ServerNotConnected {

        class serverStatus implements Runnable{
            private Image image;
            private String text;
            public serverStatus(Image i,String t){
                this.image=i;
                this.text=t;
            }
            @Override
            public void run() {
            showServerStatus(image,text);
            }
        }

            ImageManager imageManager=new ImageManager();
            Image image=null;
            String text="";
            try {
              serverConnectionManager.tryConnected();
              if (serverConnectionManager.getWebsocketConnection().getConnectionStatuse())
              {
                  image= imageManager.getImage(ImageManager.TypeImage.online);
                  text="Подключено к серверу "+serverConnectionManager.getServer().getHost();
                 Platform.runLater(()->btServerStart.setText("Отключиться от сервера"));
              }else {
                  image= imageManager.getImage(ImageManager.TypeImage.offlaine);
                  text="Доступен сервер "+serverConnectionManager.getServer().getHost();
                 Platform.runLater(()->btServerStart.setText("Подключиться к серверу"));
                  throw new ServerNotConnected();
              }
            } catch (ServerNotResponseException e) {
                image= imageManager.getImage(ImageManager.TypeImage.error);
                text="Сервер недоступен "+serverConnectionManager.getServer().getHost();
                Platform.runLater(()->btServerStart.setText("Подключиться к серверу"));
                throw new ServerNotResponseException();

            } finally {
                Platform.runLater(new serverStatus(image,text));
            }

    }

    /**
     * Управление label состояния сервера
     * @param img
     * @param msg
     */
    public void   showServerStatus(Image img,String msg){
    hboxServerStatusText.getChildren().clear();
    hboxServerStatusText.getChildren().add(new ImageView(img));
    hboxServerStatusText.getChildren().add(new Label(msg));
    }

    /**
     * CD интерфейс по созданию собственного клиента
     */
    void saveClient(){
        ClientLoader clientLoader=new ClientLoader(curentClient);
        clientLoader.saveClient();
    }
    void loadClient(){
        ClientLoader clientLoader=new ClientLoader(curentClient);
        clientLoader.loadCurrentUser();
        curentClient=clientLoader.getCurentClient();
    }

    WebsocketConnection getWebSocketConnection(){
        WebSocketConnecterImpl webSocketConnecter=new WebSocketConnecterImpl();
        webSocketConnecter.setServer(serverConnectionManager.getServer());
        webSocketConnecter.setSessionHandler(getIndificateStompHandler());
        return webSocketConnecter;
    }




    IndificateStompSessionHandler getIndificateStompHandler(){
        IndificateStompSessionHandler sessionHandler = new IndificateStompSessionHandler();
        sessionHandler.setCurrentClient(curentClient);
        sessionHandler.setStantionsStompMultipleTransfer(ServerList.getItems());
        sessionHandler.setClientStompMultipleTransfer(ClientListView.getItems());

        speachTransfer=new SpeachTransfer();
        speachTransfer.setTableView(dataTable);
        speachTransfer.setPageController(pageController);
        sessionHandler.setSpeachStompSingleTransfer(speachTransfer);

        sessionHandler.setOnClose(()-> {
            try {
                tryConnected();
            } catch (ServerNotResponseException e) {
               // e.printStackTrace();
            } catch (ServerNotConnected serverNotConnected) {
                //serverNotConnected.printStackTrace();
            }
        });;
        sessionHandler.setMonitoringStatusHandler(getMonitoringStatusHandler());
        return sessionHandler;
    }

     MonitoringStatusHandler getMonitoringStatusHandler(){
        MonitoringStatusHandler monitoringStatusHandler=new MonitoringStatusHandler();
        monitoringStatusHandler.setOnGetMonitoringStatusCallback(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (aBoolean){
                    Platform.runLater(()->startStopButton.setText("Стоп"));
                }else{
                    Platform.runLater(()->startStopButton.setText("Старт"));
                }
            }
        });
        return monitoringStatusHandler;
     }




    /**
     * Установка привязки столбцов, и фабрики для вывода дат
     */
    void setTableView(){
        dataTable.getColumns().forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
            if (column.getId().equals("date"))
            {

                column.setCellFactory(c -> {
                    TableCell cell = new TableCell<Speach, Date>() {
                        private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);
                            if(empty) {
                                setText(null);
                            }
                            else {
                                setText(format.format(item));
                            }
                        }
                    };
                    return cell;
                });

            }

    });
    }



    /**
     Достижение конца прокрутки
     */
    public void setScrollHandler(){

        ScrollBar tvScrollBar = (ScrollBar) dataTable.lookup(".scroll-bar:vertical");
        tvScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ((Double) newValue == 1.0) {
                Speach last=dataTable.getItems().get(dataTable.getItems().size()-1);
                PageStantionIdDto pageStantionIdDto=new PageStantionIdDto();
                pageStantionIdDto.setPage(pageController.getPage());
                pageStantionIdDto.setStantionId(curentStantion.getId());
                pageStantionIdDto.setSpeachId(last.getId());
                try {
                    speachTransfer.transfer(serverConnectionManager.
                            getRestConnection().getPageLessId(pageStantionIdDto));

                    tvScrollBar.setValue(0.99);

                } catch (ServerNotResponseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Обработка данных при построении графиков статистики
     */
    void onStitisticSettingOpen(){

        btStatisticSettings.setOnAction(event->{
            // Открыть настройки
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                StatisticModalController statisticModalController = new StatisticModalController();
                statisticModalController.setStantionDtos(ServerList.getItems());
                statisticModalController.setMultipleSeriasChartDecorator(multipleSeriasChartDecorator);
                statisticModalController.setRestConnection(serverConnectionManager.getRestConnection());
                loader.setController(statisticModalController);
                URL location = getClass().getClassLoader().getResource("fxml/ChartSettingsModal.fxml");

                loader.setLocation(location);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Настройки");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn(e.getMessage());
            }
        });

    }
    private void registrSratisticBarCharts()
    {
        multipleSeriasChartDecorator.clear();

        AbonentChartController abonentChartController=new
                AbonentChartController(AmountOfAbbonents);
        multipleSeriasChartDecorator.getChartControllers().
                add(abonentChartController);

        CountryChartController countryChartController=
                new CountryChartController(statisticBarChartCounty);

        multipleSeriasChartDecorator.getChartControllers().
                add(countryChartController);

        OperatorChartController operatorChartController=
                new OperatorChartController(statisticBarChartOperator);

        multipleSeriasChartDecorator.getChartControllers().
                add(operatorChartController);

        EventChartController eventChartController=new
                EventChartController(EventChart);
        multipleSeriasChartDecorator.getChartControllers().
                add(eventChartController);
    }

}


