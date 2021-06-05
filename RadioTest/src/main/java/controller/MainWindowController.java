package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Broadcast;
import logic.bcparts.BcPart;
import logic.bcparts.InterView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    @FXML private TableView<Broadcast> broadcastTable;
    @FXML private TableColumn<Broadcast, String> broadcastCol;
    @FXML private TableColumn<Broadcast, Double> broadcastLengthCol;
    @FXML private TableColumn<Broadcast, Double> profitCol;
    //
    @FXML private TableView<BcPart> partsTable;
    @FXML private TableColumn<BcPart, String> broadcastPartCol;
    @FXML private TableColumn<BcPart, String> infoCol;
    @FXML private TableColumn<BcPart, Double> partLengthCol;
    @FXML private TableColumn<BcPart, Double> priceCol;
    //
    private List<Broadcast> broadcasts;
    //
    public List<Broadcast> getBroadcasts() {
        return broadcasts;
    }
    public TableView<Broadcast> getBroadcastTable() {
        return broadcastTable;
    }


    public void initialize(){
        broadcasts = new ArrayList<>();
        broadcastCol.setCellValueFactory(new PropertyValueFactory<>("broadcast"));
        broadcastLengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
        broadcastTable.setItems(FXCollections.observableArrayList(broadcasts));
        broadcastPartCol.setCellValueFactory(new PropertyValueFactory<>("contentType"));
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        partLengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
        if (!broadcasts.isEmpty())
            partsTable.setItems(FXCollections.observableArrayList(broadcastTable.getFocusModel().getFocusedItem().getParts()));
    }

    public void newBroadcastClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main/newBroadcast.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Broadcast");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            NewBroadcast controller = fxmlLoader.getController();
            controller.init(this);
            stage.showAndWait();
            refreshTables();
        } catch (Exception e) {
            displayError(e.toString());
        }
    }

    public void removeABroadcastClicked() {
        if (broadcastTable.getFocusModel().getFocusedItem() != null){
            broadcasts.remove(broadcastTable.getFocusModel().getFocusedItem());
            refreshTables();
        } else
            displayInfo("Please, select a broadcast for removing!", "Warning!");
    }

    public void newPartClicked() {
        if (broadcastTable.getFocusModel().getFocusedItem() == null) {
            displayInfo("Please, select a broadcast for adding!", "Warning!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main/newPart.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Part");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            NewPart controller = fxmlLoader.getController();
            controller.init(this);
            stage.showAndWait();
            refreshTables();
        } catch (Exception e) {
            displayError(e.toString());
        }
    }

    public void removeAPartClicked() {
        if (partsTable.getFocusModel().getFocusedItem() != null){
            broadcasts.get(broadcastTable.getFocusModel().getFocusedIndex()).getParts().remove(partsTable.getFocusModel().getFocusedItem());
            refreshTables();
        } else
            displayInfo("Please, select a part of broadcast for removing!", "Warning!");
    }

    public void moreInfoClicked() {
        if (partsTable.getFocusModel().getFocusedItem().getContentType().equals("Interview")){
            InterView interView = (InterView) partsTable.getFocusModel().getFocusedItem();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            TextArea textArea = new TextArea(interView.getOnAirTalent().getInfo());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            GridPane expContent = new GridPane();
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.setHeaderText("On-air Talent: " + interView.getOnAirTalent().getName() + " Resume");
            alert.setTitle("On-air Talent: " + interView.getOnAirTalent().getName() + " Resume");
            alert.showAndWait();
        } else displayInfo("This button is able only for interview part!", "Warning!");
    }

    public void openFileClicked() {
        List<Broadcast> oldV = broadcasts;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file == null) return;
        try{
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            ObjectInputStream ois = new ObjectInputStream(fis);
            int filesize = ois.readInt();
            broadcasts = new ArrayList<>();
            for (int i = 0; i < filesize; i++) {
                broadcasts.add((Broadcast) ois.readObject());
            }
            ois.close();
            broadcastTable.setItems(FXCollections.observableArrayList(broadcasts));
        } catch (IOException | ClassNotFoundException ioe) {
            displayError(ioe.toString());
            broadcasts = oldV;
        }
    }

    public void saveFileClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file == null) return;
        try{
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(broadcasts.size());
            for (Broadcast broadcast : broadcasts) {
                oos.writeObject(broadcast);
            }
            oos.close();
        } catch (IOException ioe) {
            displayError(ioe.toString());
        }
    }

    public void displayError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void displayInfo(String contentText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void refreshTables() {
            broadcastTable.setItems(FXCollections.observableArrayList(broadcasts));
        if (!broadcasts.isEmpty()){
            partsTable.setItems(FXCollections.observableArrayList(broadcastTable.getFocusModel().getFocusedItem().getParts()));
            broadcastTable.refresh();
            partsTable.refresh();
        }
    }
}
