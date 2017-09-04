package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editablelabel;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 07/08/2017.
 */
public class EditableLabel extends EditableControl implements Initializable {

    /**
     * The textfield that will be used as an editor
     */
    private TextField editorTf = new TextField();

    /**
     * The effective text managed by the component
     */
    private StringProperty text = new SimpleStringProperty("EditableLabel");


    private boolean validateFlag = false;

    public EditableLabel() {

        this.editorTf = new TextField();
        this.editorNode = editorTf;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);
        Platform.runLater(()->{
            showLabel();
            this.label.textProperty().bindBidirectional(this.textProperty());
            this.editorTf.textProperty().bindBidirectional(this.textProperty());
        });


    }

    @Override
    protected void validateValue() {
        this.label.setText(this.editorTf.getText());
        showLabel();
    }

    @Override
    protected void discardValue() {
        this.editorTf.setText(this.label.getText());
        showLabel();
    }

    @Override
    protected void showEditor(){
        super.showEditor();
        this.editorTf.selectAll();
    }

}

