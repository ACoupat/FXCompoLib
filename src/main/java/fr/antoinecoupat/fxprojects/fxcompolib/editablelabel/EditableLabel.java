package fr.antoinecoupat.fxprojects.fxcompolib.editablelabel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 07/08/2017.
 */
public class EditableLabel extends AnchorPane implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    /**
     * The effective text managed by the component
     */
    private StringProperty text = new SimpleStringProperty("EditableLabel");

    private boolean validateFlag = false;

    public EditableLabel() {

        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("fr/antoinecoupat/fxprojects/fxcompolib/editablelabel/fxcompolib/fxml/editablelabel/EditableLabel.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.setClassLoader(getClass().getClassLoader());
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showLabel();

        this.label.textProperty().bindBidirectional(this.textProperty());
        this.textField.textProperty().bindBidirectional(this.textProperty());

        this.label.setOnMouseClicked(event->{

            if(event.getClickCount() == 2){
                showTextField();
            }

        });

        this.textField.setOnKeyPressed(event->{

            if(event.getCode() == KeyCode.ENTER){
               validateValue();
            }
            //TODO improve
            if(event.getCode() == KeyCode.ESCAPE){
                this.textField.setText(this.label.getText());
                showLabel();
            }

        });

        this.textField.focusedProperty().addListener((obs,ov,nv)->{
            if(!nv){
                validateValue();
            }
        });
    }

    private void showTextField(){
        showTextField(true);
    }

    private void showLabel(){
        showTextField(false);
    }

    private void validateValue(){
        this.label.setText(this.textField.getText());
        showLabel();
    }

    /**
     * Shows/Hides the textfield/label according to the boolean parameter
     * @param value if true the text field will be displayed and the label will be hidden
     */
    private void showTextField(boolean value){
        this.textField.setManaged(value);
        this.textField.setVisible(value);
        this.textField.requestFocus();
        this.textField.selectAll();
        this.label.setManaged(!value);
        this.label.setVisible(!value);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

}

