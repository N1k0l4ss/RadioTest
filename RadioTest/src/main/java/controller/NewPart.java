package controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import logic.OnAirTalent;
import logic.bcparts.Advertisement;
import logic.bcparts.InterView;
import logic.bcparts.MusicPart;
import logic.bcparts.OtherContent;

import java.util.regex.Pattern;


public class NewPart {
    @FXML private TextArea infoArea;
    @FXML private TextField lengthField;
    @FXML private RadioButton radioMusic;
    @FXML private ToggleGroup type;
    @FXML private RadioButton radioAd;
    @FXML private RadioButton radioInt;
    @FXML private RadioButton radioOther;
    @FXML private TextField contentField;
    @FXML private TextField priceField;
    @FXML private TextArea talentInfoArea;
    @FXML private TextField talentNameField;
    private MainWindowController mainWindowController;


    public void cancelClicked() {
        closeStage();
    }

    public void okClicked() {
        if (!fieldsIsNotEmpty()) {
            mainWindowController.displayInfo("Fields can not be empty!", "Warning!");
            return;
        }
        int focusedIndex = mainWindowController.getBroadcastTable().getFocusModel().getFocusedIndex();
        if (radioMusic.isSelected()){
            mainWindowController.getBroadcasts().get(focusedIndex).getParts()
                    .add(new MusicPart(infoArea.getText(), Integer.parseInt(lengthField.getText())));
        } else if (radioAd.isSelected()) {
            mainWindowController.getBroadcasts().get(focusedIndex).getParts()
                    .add(new Advertisement(infoArea.getText(), Integer.parseInt(lengthField.getText())));
        } else if (radioInt.isSelected()) {
            mainWindowController.getBroadcasts().get(focusedIndex).getParts()
                    .add(new InterView(new OnAirTalent(talentNameField.getText(), talentInfoArea.getText()),infoArea.getText(), Integer.parseInt(lengthField.getText())));
        } else if (radioOther.isSelected()) {
            mainWindowController.getBroadcasts().get(focusedIndex).getParts()
                    .add(new OtherContent(infoArea.getText(), Integer.parseInt(lengthField.getText()), contentField.getText(), Double.parseDouble(priceField.getText())));
        }

        if (mainWindowController.getBroadcasts().get(focusedIndex).isSuccessfullyAdded(mainWindowController)) {
            mainWindowController.getBroadcasts().get(focusedIndex).calcProfit();
            closeStage();
        }
    }

    public void initialize(){
        radioMusic.setSelected(true);
        checkRadioBtns();
        digitFilterDouble(lengthField);
        digitFilterDouble(priceField);
    }

    private void closeStage() {
        Stage stage = (Stage) lengthField.getScene().getWindow();
        stage.close();
    }

    public void init(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private boolean fieldsIsNotEmpty(){
        if (infoArea.getText().isBlank() || lengthField.getText().isBlank())
            return false;
        if (!talentInfoArea.isDisable() && talentInfoArea.getText().isBlank())
            return false;
        if (!talentNameField.isDisable() && talentNameField.getText().isBlank())
            return false;
        if (!contentField.isDisable() && contentField.getText().isBlank())
            return false;
        if (!priceField.isDisable() && priceField.getText().isBlank())
            return false;
        return true;
    }

    public void checkRadioBtns() {
        if (!radioOther.isFocused()) {
            contentField.setDisable(true);
            priceField.setDisable(true);
        } else {
            contentField.setDisable(false);
            priceField.setDisable(false);
        }
        if (!radioInt.isFocused()) {
            talentInfoArea.setDisable(true);
            talentNameField.setDisable(true);
        } else {
            talentInfoArea.setDisable(false);
            talentNameField.setDisable(false);
        }
    }


    private void digitFilterDouble(TextField field) {
        Pattern p = Pattern.compile("(\\d+[\\.,]?\\d*)?");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) field.setText(oldValue);
            else field.setText(newValue.replaceAll(",", "."));
        });
    }
}
