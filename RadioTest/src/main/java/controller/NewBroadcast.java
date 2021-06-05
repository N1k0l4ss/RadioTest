package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Broadcast;

import java.util.regex.Pattern;

public class NewBroadcast {
    @FXML private TextField lengthField;
    @FXML private TextField bCastField;
    private MainWindowController mainWindowController;


    public void cancelClicked() {
        closeStage();
    }

    public void addClicked() {
        if (lengthField.getText().isBlank() || bCastField.getText().isBlank())
        {
            mainWindowController.displayInfo("Fields can not be empty!", "Warning!");
            return;
        }
        Broadcast broadcast = new Broadcast(bCastField.getText(), Integer.parseInt(lengthField.getText()));
        mainWindowController.getBroadcasts().add(broadcast);
        closeStage();
    }

    public void initialize(){
        Pattern p = Pattern.compile("[0-9]*");
        lengthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) lengthField.setText(oldValue);
        });
    }

    public void closeStage() {
        Stage stage = (Stage) lengthField.getScene().getWindow();
        stage.close();
    }

    public void init(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
