package app.client.Controller;


import app.DTO.Stantion;
import app.DTO.StantionDto;
import app.DTO.TypeConnection;
import app.ServerConnection.Exception.ServerNotResponseException;
import app.ServerConnection.ServerConnectionManager;
import app.client.GUI.ListCellFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
@Data
public class SettingsController {
    @FXML
    private TabPane TabMenu;
    @FXML
    private ListView ListMenu;
    @FXML
    private ListView<StantionDto> ServerList;
    @FXML
    private TextField ServerName;
    @FXML
    private TextField ServerLogin;
    @FXML
    private TextField ServerPassword;
    @FXML
    private TextField ServerHost;
    @FXML
    private TextField ServerPath;
    @FXML
    private TextField ServerLon;
    @FXML
    private TextField ServerLat;
    @FXML
    private Button ServerAddBtn;
    @FXML
    private Button ServerDeleteBtn;
    @FXML
    private Button ServerPingBtn;
    @FXML
    private Button ServerUpdateBtn;
    @FXML
    private ComboBox ComboBoxType;
    @FXML
    private CheckBox CheckBoxActive;

    @FXML
    private Button bt_add;


    private ObservableList<StantionDto> stantionDtos;
    private ServerConnectionManager serverConnectionManager;


    @FXML
    protected void initialize() {
        //Тут происходит загрузка параметров
      //  settings = Helper.LoadSettings();
        ServerList.setItems(stantionDtos);
        ServerList.setCellFactory(l-> ListCellFactory.getCell());
        ListMenu.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TabMenu.getTabs().forEach(tab -> {
            ListMenu.getItems().add(tab.getText());
        });
        ListMenu.getSelectionModel().selectFirst();
        ServerList.getSelectionModel().selectFirst();

        Arrays.asList(TypeConnection.values()).forEach(type -> {
            ComboBoxType.getItems().add(type);
        });
       // listServerClick();
        bt_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServerName.setText("");
                ServerLogin.setText("");
                ServerPassword.setText("");
                ServerHost.setText("");
                ServerPath.setText("");
                ServerLon.setText("");
                ServerLat.setText("");
                ServerAddBtn.setVisible(true);
                ServerDeleteBtn.setVisible(false);
                ServerPingBtn.setVisible(false);
                ServerUpdateBtn.setVisible(false);
                CheckBoxActive.setSelected(false);
                ComboBoxType.getSelectionModel().clearSelection();

            }
        });
    }

    public void listMenuClick() {
        TabMenu.getTabs().forEach(tab -> {
            if(tab.getText() == ListMenu.getSelectionModel().getSelectedItem().toString()){
                TabMenu.getSelectionModel().select(ListMenu.getSelectionModel().getSelectedIndex());
            }
        });
    }

    public void listServerClick() throws ServerNotResponseException {

            Stantion server = serverConnectionManager.getRestConnection().getStantionById(
           (ServerList.getSelectionModel().getSelectedIndex()));
            ServerName.setText(server.getName());
            ServerLogin.setText(server.getUsername());
            ServerPassword.setText(server.getPassword());
            ServerHost.setText(server.getHost());
            ServerPath.setText(server.getFile());
            ServerLon.setText(server.getCoord_X()+"");
            ServerLat.setText(server.getCoord_Y() + "");
            ComboBoxType.getSelectionModel().select(server.getTypeConnection());
            //CheckBoxActive.setSelected(server.Active);
            ServerAddBtn.setVisible(false);
            ServerDeleteBtn.setVisible(true);
            ServerPingBtn.setVisible(true);
            ServerUpdateBtn.setVisible(true);
    }


    public void serverUpdate() throws ServerNotResponseException {

        Stantion stantion= getServerData();
        stantion.setId(ServerList.getItems().
                get(ServerList.getSelectionModel().getSelectedIndex()).getId());
        serverConnectionManager.getRestConnection().addStantion(stantion);
      //  reload();
    }

    public void serverCreate() throws ServerNotResponseException {
        serverConnectionManager.getRestConnection().addStantion(getServerData());
    }

    public void serverDelete() throws ServerNotResponseException {
        StantionDto selected=ServerList.getItems().
                get(ServerList.getSelectionModel().getSelectedIndex());
        serverConnectionManager.getRestConnection().
                removeStantion( (selected.getId()));
    }

    public void serverPing(){

    }

    private Stantion getServerData() {
        Stantion s = new Stantion();
		s.setHost(ServerHost.getText());
		s.setName(ServerName.getText());
		s.setPassword(ServerPassword.getText());
		s.setFile( ServerPath.getText());
		s.setUsername(ServerLogin.getText());
		s.setCoord_X(Double.parseDouble(ServerLon.getText()));
        s.setCoord_Y(Double.parseDouble(ServerLat.getText()));
        s.setTypeConnection(TypeConnection.valueOf(ComboBoxType.getSelectionModel().getSelectedItem().toString()));
        //s.Active = CheckBoxActive.isSelected();
		return s;
    }

    private void reload(){
        ListMenu.getItems().clear();
       // ServerList.getItems().clear();
        ComboBoxType.getItems().clear();
        initialize();
    }
}