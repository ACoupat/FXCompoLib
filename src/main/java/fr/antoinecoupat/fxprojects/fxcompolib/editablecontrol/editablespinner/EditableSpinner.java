package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editablespinner;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Antoine on 04/09/2017.
 */
public class EditableSpinner extends EditableControl{

    private Spinner<Double> editorSp = new Spinner<>();

    private DoubleProperty value = new SimpleDoubleProperty(0);

    private boolean integers = false;


    public EditableSpinner(double min, double max, double step, double value, boolean integers) {

        this.editorNode = this.editorSp;
        this.editorSp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(min,max,value,step));
        this.editorSp.setEditable(true);
        this.integers = integers;
        this.textProperty().set(this.editorSp.getValue().toString());

        //Dispatching the event to the handler in the super class
        this.editorSp.getEditor().setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                this.editorNode.getOnKeyPressed().handle(event);
            }
        });
    }

    public EditableSpinner(boolean integers) {
        this(-Double.MAX_VALUE, Double.MAX_VALUE,0.1,0.0,integers);
    }

    public EditableSpinner() {
        this(false);
    }

    @Override
    protected void validateValue() {
        if(matchesRegex()){
            this.value.set(Double.parseDouble(this.editorSp.getEditor().getText()));
        }
        else{
            String valueToSet = (integers) ? Integer.toString(this.value.intValue()) : Double.toString(this.value.doubleValue());
            this.editorSp.getEditor().setText(valueToSet);
        }
        formatNumberToLabel();
    }

    @Override
    protected void discardValue() {
        this.editorSp.getValueFactory().setValue(this.value.get());
        formatNumberToLabel();
    }

    private void formatNumberToLabel(){ //TODO factorize ?

        String textToDisplay = "";
        if(integers){
            textToDisplay = Integer.toString(this.value.intValue());
        }else{
            textToDisplay = Double.toString((this.value.doubleValue()));
        }
        this.textProperty().set(textToDisplay);
    }

    /**
     * @return true if the value in the spinner
     * matches the right number format
     */
    private boolean matchesRegex(){

        String regex ="";
        if(this.integers){
            regex = "^\\d+$"; //TODO regex
        }else{
            regex = "-?\\d+(.\\d+)?"; //TODO regex
        }
        return Pattern.matches(regex, this.editorSp.getEditor().getText());

    }
}
