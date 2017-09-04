package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editabledatepicker;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;


/**
 * Created by Antoine on 31/08/2017.
 */
public class EditableDatePicker extends EditableControl{

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    /**
     * DatePicker used as an editor
     */
    private DatePicker editorDp = new DatePicker();

    //TODO Manage several date formats

    /**
     * Creates a new EditableDatePicker
     */
    public EditableDatePicker() {
        super();
        this.editorNode = editorDp;

        LocalDate startDate = LocalDate.now();

        this.date.set(startDate);
        this.editorDp.setValue(startDate);

        formatDateToLabel();
    }

    /**
     * If the value of the DatePicker is valid, it is
     * set as the value of the component and the label
     * is displayed. If it is not valid the old value is
     * kept.
     */
    @Override
    protected void validateValue() {
        LocalDate newValue = editorDp.getValue();
        if(newValue != null){
            this.date.set(editorDp.getValue());
            formatDateToLabel();
            //showLabel();
        }else{
            discardValue();
        }

    }

    /**
     * Sets the last valid value as the value
     * of the component and displays the label
     */
    @Override
    protected void discardValue() {
        this.editorDp.setValue(this.date.get());
        //showLabel();
    }

    /**
     * Converts the date value of the component
     * to a String and displays it in the label
     */
    private void formatDateToLabel(){

        LocalDateStringConverter converter = new LocalDateStringConverter();
        this.textProperty().set(converter.toString(this.date.get()));
    }

    //Getters & Setters
    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }


}
