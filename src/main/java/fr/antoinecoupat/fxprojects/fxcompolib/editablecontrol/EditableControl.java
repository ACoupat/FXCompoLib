package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public abstract class EditableControl extends AnchorPane implements Initializable {


    @FXML
    private AnchorPane editorNodeSlot;

    @FXML
    protected Label label;

    /**
     * The effective text managed by the component
     */
    private StringProperty text = new SimpleStringProperty("EditableLabele");

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


            this.label.textProperty().bindBidirectional(this.textProperty());

            this.label.setOnMouseClicked(event->{
                System.out.println("lol");
                if(event.getClickCount() == 2){
                    showEditor();
                }

            });

            this.editorNode.setOnKeyPressed(event->{
                if(event.getCode() == KeyCode.ENTER){
                    validateValue();
                }
                if(event.getCode() == KeyCode.ESCAPE){
                    discardValue();
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

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
