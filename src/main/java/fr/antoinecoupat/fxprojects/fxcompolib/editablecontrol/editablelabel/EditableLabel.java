package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editablelabel;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 07/08/2017.
 */
public class EditableLabel extends EditableControl<String>{

    /**
     * The textfield that will be used as an editor
     */
    private TextField editorTf = new TextField();

    public EditableLabel(String text) {

        this.editorTf = new TextField();
        this.editorNode = editorTf;
        this.text.set(text);
    }

    public EditableLabel(){
        this("Label");
    }

    /*@Override
    public void initialize(URL location, ResourceBundle resources){
        super.initialize(location,resources);
        Platform.runLater(()->{
            showLabel();
           // this.editorTf.textProperty().bind(this.textProperty());
        });
    }*/

    @Override
    protected void validateValue() {
        this.value.set(this.editorTf.getText());
        formatValueToLabel();
    }

    @Override
    protected void discardValue() {
        this.editorTf.setText(this.label.getText());
    }

    @Override
    protected void formatValueToLabel() {
        this.text.set(this.value.get());
    }

    @Override
    protected void showEditor(){
        super.showEditor();
        this.editorTf.selectAll();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        this.editorTf.setText(value);
        formatValueToLabel();
    }

}

