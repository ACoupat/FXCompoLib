package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 31/08/2017.
 */
public abstract class EditableControl<T> extends AnchorPane implements Initializable {


    @FXML
    private AnchorPane editorNodeSlot;

    @FXML
    protected Label label;

    /**
     * The text property that is displayed when the label is visible
     */
    protected StringProperty text = new SimpleStringProperty("");

     /**
     * The property that handles the value managed by the component
     */
    protected ObjectProperty<T> value = new SimpleObjectProperty<T>();

    protected Node editorNode;

    private boolean validateFlag = false;

    public EditableControl() {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("fr/antoinecoupat/fxprojects/fxcompolib/fxml/editablelabel/EditableLabel.fxml"));
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

        Platform.runLater(()->{
            //Setting the specific editor node
            this.editorNodeSlot.getChildren().add(this.editorNode);
            showLabel();
            AnchorPane.setBottomAnchor(this.editorNode,0D);
            AnchorPane.setRightAnchor(this.editorNode,0D);
            AnchorPane.setLeftAnchor(this.editorNode,0D);
            AnchorPane.setTopAnchor(this.editorNode,0D);


            this.label.textProperty().bind(this.textProperty());

            this.label.setOnMouseClicked(event->{
                if(event.getClickCount() == 2){
                    showEditor();
                }

            });

            this.editorNode.setOnKeyPressed(event->{
                if(event.getCode() == KeyCode.ENTER){
                    validateValue();
                    showLabel();
                }
                if(event.getCode() == KeyCode.ESCAPE){
                    discardValue();
                    showLabel();
                }
            });

            this.editorNodeSlot.focusedProperty().addListener((obs,ov,nv)->{
                if(!nv){
                    validateValue();
                }
            });
        });

    }

    protected void showEditor(){
        showEditor(true);
    }

    protected void showLabel(){ showEditor(false); }

    /**
     * Override this method to define the behavior
     * of the component when the value is validated
     */
    protected abstract void validateValue();

    /**
     * Override this method to define the behavior
     * of the component when the value is discarded
     */
    protected abstract void discardValue();

    /**
     * Override this method to convert the value to a string
     * and display it in the label
     */
    protected abstract void formatValueToLabel();

    /**
     * Shows/Hides the editor/label according to the boolean parameter
     * Override this method to add extra behaviour such as text selection etc...
     * @param value if true the editor will be displayed and the label will be hidden
     */
    protected void showEditor(boolean value){
        this.editorNode.setManaged(value);
        this.editorNode.setVisible(value);
        this.editorNode.requestFocus();
        this.label.setManaged(!value);
        this.label.setVisible(!value);
    }

    public String getText() {
        return text.get();
    }

    public ReadOnlyStringProperty textProperty() {
        return text;
    }

    //Getters & Setters

    public T getValue() {
        return value.get();
    }

    public ReadOnlyObjectProperty<T> valueProperty() {
        return value;
    }

    /**
     * Override this method to change the value of the editor node
     * @param value
     */
    public void setValue(T value){
        this.value.set(value);
    };

}
