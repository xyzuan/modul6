package id.xyzprjkt.modul6;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Objects;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MainActivity extends Application {

    final String appName = "myUMM CourseTable";
    final String css = Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm();
    private final ObservableList<mhsDB> data = FXCollections.observableArrayList();
    final HBox hBox = new HBox();

    public void start (Stage stage){
        stage.setTitle(appName);
        String name = "Jody Yuantoro";

        Label mainBtn = new Label("See our time");
        mainBtn.setOnMouseClicked(e -> stage.setScene(mainActivity(name)));
        stage.setScene(landingPage(mainBtn));
        stage.setResizable(false);
        stage.show();
    }
    public Scene landingPage(Label backButton) {

        GridPane homeContainer = new GridPane();
        homeContainer.setId("home-container");
        Scene homePage = new Scene(homeContainer, 1280, 720);

        homePage.getStylesheets().add(css);

        GridPane introContainer = new GridPane();
        introContainer.setId("intro-container");
        Text introTitle = new Text("Welcome to\n" + appName);
        introTitle.setId("intro-title");
        Text introText = new Text("Made with <3 by\n" + "Jody Yuantoro - 202110370311147");
        introText.setId("intro-text");
        GridPane btnContainer = new GridPane();

        backButton.setId("timetable-btn");
        btnContainer.add(backButton, 0, 0);

        introContainer.add(introTitle, 0, 0);
        introContainer.add(introText, 0, 1);
        introContainer.add(btnContainer, 0, 2);

        HBox imageContainer = new HBox();
        imageContainer.setId("img-container");
        homeContainer.add(introContainer ,0, 0);
        homeContainer.add(imageContainer, 1, 0);

        return homePage;
    }

    public Scene mainActivity(String name) {

        // Main View
        GridPane mainViewContainer = new GridPane();
        mainViewContainer.setId("main-container");
        Scene mainActivity = new Scene(mainViewContainer, 1280, 720);
        mainActivity.getStylesheets().add(css);

        // Header View
        GridPane headerViewContainer = new GridPane();
        headerViewContainer.setId("header-container");
        final Label titleHeader = new Label(appName + "\n" + name);
        titleHeader.setId("header");
        headerViewContainer.add(titleHeader,0,0);

        // Table View
        GridPane tableViewContainer = new GridPane();
        tableViewContainer.setId("table-container");

        TableView table = new TableView();
        table.setId("table-view");
        table.prefWidthProperty().bind(tableViewContainer.widthProperty());
        table.prefHeightProperty().bind(tableViewContainer. heightProperty());

        table.setEditable(true);

        final TableColumn dosenColumn = new TableColumn("Nama Dosen");
        final TableColumn matkulColumn = new TableColumn("Mata Kuliah");
        final TableColumn jamColumn = new TableColumn("Jam ke");
        final TableColumn GKBCol = new TableColumn("GKB");
        final TableColumn RuanganCol = new TableColumn("Ruangan");

        dosenColumn.setMinWidth(200); dosenColumn.setMaxWidth(200);
        matkulColumn.setMinWidth(160); matkulColumn.setMaxWidth(160);
        jamColumn.setMinWidth(100); jamColumn.setMaxWidth(100);
        GKBCol.setMinWidth(100); GKBCol.setMaxWidth(100);
        RuanganCol.setMinWidth(222); RuanganCol.setMaxWidth(222);

        dosenColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<mhsDB, String>, ObservableValue>) p -> p.getValue().nameDosenProperty());
        matkulColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<mhsDB, String>, ObservableValue>) p -> p.getValue().nameMatkulProperty());
        jamColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<mhsDB, String>, ObservableValue>) p -> p.getValue().timeProperty());
        GKBCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<mhsDB, String>, ObservableValue>) p -> p.getValue().gkbProperty());
        RuanganCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<mhsDB, String>, ObservableValue>) p -> p.getValue().ruanganProperty());

        table.getColumns().addAll(dosenColumn, matkulColumn, jamColumn, GKBCol, RuanganCol);
        table.setItems(data);

        // Input View
        GridPane inputViewContainer = new GridPane();
        inputViewContainer.setId("input-container");

        final TextField inputDosen = new TextField();
        final TextField inputMatkul = new TextField();
        final TextField inputJam = new TextField();
        final TextField inputGKB = new TextField();
        final TextField inputRuangan = new TextField();

        // Dosen Field
        final Text inputDosenTitle = new Text("Nama Dosen"); inputDosenTitle.setId("input-field-text");
        inputDosen.setPromptText("Masukkan Nama Dosen");
        inputDosen.setId("input-field");

        final Text inputMatkulTitle = new Text("Mata Kuliah"); inputMatkulTitle.setId("input-field-text");
        inputMatkul.setPromptText("Masukkan Nama Matkul");
        inputMatkul.setId("input-field");

        final Text inputJamTitle = new Text("Jam Pelajaran"); inputJamTitle.setId("input-field-text");
        inputJam.setPromptText("Masukkan Jam Pelajaran");
        inputJam.setId("input-field");
        inputJam.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputJam.setText(newValue.replaceAll("\\D", ""));
            }
        });

        final Text inputGKBTitle = new Text("Gedung GKB"); inputGKBTitle.setId("input-field-text");
        inputGKB.setPromptText("GKB");
        inputGKB.setId("input-field");
        inputGKB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputGKB.setText(newValue.replaceAll("\\D", ""));
            }
        });

        final Text inputRuanganTitle = new Text("Ruangan"); inputRuanganTitle.setId("input-field-text");
        inputRuangan.setPromptText("Ruangan");
        inputRuangan.setId("input-field");

        final Button buttonAdd = new Button("Add");
        buttonAdd.setId("button");
        buttonAdd.setOnAction((ActionEvent e) -> {
            if ( inputDosen.getText().isEmpty() || inputMatkul.getText().isEmpty() || inputJam.getText().isEmpty() || inputGKB.getText().isEmpty() || inputRuangan.getText().isEmpty()){
                Alert errorDialog = new Alert(Alert.AlertType.ERROR, "Data Yang Di Input Harus Tidak Ada Yang Boleh Kosong");
                errorDialog.showAndWait();
            } else {
                data.add(new mhsDB(
                        inputDosen.getText(),
                        inputMatkul.getText(),
                        inputJam.getText(),
                        inputGKB.getText(),
                        inputRuangan.getText()));
                clearTextField(inputDosen, inputMatkul, inputJam, inputGKB, inputRuangan);
            }
        });

        Button buttonUpdate = new Button("Update");
        buttonUpdate.setId("button");
        buttonUpdate.setOnAction((ActionEvent e) -> {

            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            if ( inputDosen.getText().isEmpty() || inputMatkul.getText().isEmpty() || inputJam.getText().isEmpty() || inputGKB.getText().isEmpty() || inputRuangan.getText().isEmpty()){
                Alert errorDialog = new Alert(Alert.AlertType.ERROR, "Data Yang Di Input Harus Tidak Ada Yang Boleh Kosong");
                errorDialog.showAndWait();
            } else {
                data.set(selectedIndex, new mhsDB(
                        inputDosen.getText(),
                        inputMatkul.getText(),
                        inputJam.getText(),
                        inputGKB.getText(),
                        inputRuangan.getText()));
                clearTextField(inputDosen, inputMatkul, inputJam, inputGKB, inputRuangan);
            }
        });

        final Button buttonDelete = new Button("Delete");
        buttonDelete.setId("button");
        buttonDelete.setOnAction(actionEvent -> table.getItems().removeAll(table.getSelectionModel().getSelectedItem()));

        hBox.getChildren().addAll(
                inputDosen,inputMatkul,inputJam,inputGKB,inputRuangan,
                buttonAdd,buttonUpdate,buttonDelete);
        hBox.setSpacing(10);

        final VBox vBox = new VBox();
        vBox.setSpacing(18);
        vBox.setPadding(new Insets(15,15,15,15));
        vBox.getChildren().addAll(titleHeader,table,hBox);

        // Container Controller
        headerViewContainer.add(titleHeader, 0 ,0);

        tableViewContainer.add(table, 0, 0);

        inputViewContainer.add(inputDosenTitle,0,0);
        inputViewContainer.add(inputDosen, 0,1);

        inputViewContainer.add(inputMatkulTitle,0,2);
        inputViewContainer.add(inputMatkul,0,3);

        inputViewContainer.add(inputGKBTitle,0,4);
        inputViewContainer.add(inputGKB,0,5);

        inputViewContainer.add(inputJamTitle,0,6);
        inputViewContainer.add(inputJam,0,7);

        inputViewContainer.add(inputRuanganTitle,0,8);
        inputViewContainer.add(inputRuangan,0,9);

        inputViewContainer.add(buttonAdd,0,10);
        inputViewContainer.add(buttonUpdate,0,11);
        inputViewContainer.add(buttonDelete,0,12);

        mainViewContainer.add(headerViewContainer,0,0);
        mainViewContainer.add(tableViewContainer, 0,1);
        mainViewContainer.add(inputViewContainer,1,1);

        return mainActivity;
    }

    private void clearTextField(TextField dosen, TextField matkul, TextField jam, TextField gkb, TextField ruangan) {
        dosen.clear();
        matkul.clear();
        jam.clear();
        gkb.clear();
        ruangan.clear();
    }

    public static class mhsDB {

        final private SimpleStringProperty nameDosen,nameMatkul, time, gkb, ruangan;
        private mhsDB(String nameDosen, String nameMatkul, String time, String gkb, String ruangan){
            this.nameDosen = new SimpleStringProperty(nameDosen);
            this.nameMatkul = new SimpleStringProperty(nameMatkul);
            this.time = new SimpleStringProperty(time);
            this.gkb = new SimpleStringProperty(gkb);
            this.ruangan = new SimpleStringProperty(ruangan);
        }
        public SimpleStringProperty nameDosenProperty() {
            return nameDosen;
        }
        public SimpleStringProperty nameMatkulProperty() {
            return nameMatkul;
        }
        public SimpleStringProperty timeProperty() {
            return time;
        }
        public SimpleStringProperty gkbProperty() {
            return gkb;
        }
        public SimpleStringProperty ruanganProperty() {
            return ruangan;
        }
    }
}