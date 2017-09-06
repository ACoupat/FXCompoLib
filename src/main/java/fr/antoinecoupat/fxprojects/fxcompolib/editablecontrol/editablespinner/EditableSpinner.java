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
public class EditableSpinner extends EditableControl<Double>{

    private Spinner<Double> editorSp = new Spinner<>();

    private boolean integers = false;


    public EditableSpinner(double min, double max, double step, double value, boolean integers) {

        this.editorNode = this.editorSp;
        this.editorSp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(min,max,value,step));
        this.editorSp.setEditable(true);
        this.integers = integers;
        this.text.set(this.editorSp.getValue().toString());

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
            String valueToSet = (integers) ? Integer.toString(this.value.get().intValue()) : Double.toString(this.value.get().doubleValue()); //TODO simplify
            this.editorSp.getEditor().setText(valueToSet);
        }
        formatValueToLabel();
    }

    @Override
    protected void discardValue() {
        this.editorSp.getValueFactory().setValue(this.value.get());
        formatValueToLabel();
    }

    @Override
    protected void formatValueToLabel() {
        String textToDisplay = "";
        if(integers){
            textToDisplay = Integer.toString(this.value.get().intValue());
        }else{
            textToDisplay = Double.toString(this.value.get().doubleValue());
        }
        this.text.set(textToDisplay);
    }

    @Override
    public void setValue(Double value) {
        super.setValue(value);
        this.editorSp.getValueFactory().setValue(value);
        formatValueToLabel();
    }

    /**
     * @return true if the value in the spinner
     * matches the right number format
     */
    private boolean matchesRegex(){

        String regex ="";
        if(this.integers){
            regex = "^\\d+$";
        }else{
            regex = "-?\\d+(.\\d+)?";
        }
        return Pattern.matches(regex, this.editorSp.getEditor().getText());

    }

    private Double getRightVersionOfValue(){
        return integers ? value.get().intValue() : value.get().doubleValue();
    }
}
