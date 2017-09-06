package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editabledatepicker;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;


/**
 * Created by Antoine on 31/08/2017.
 */
public class EditableDatePicker extends EditableControl<LocalDate>{

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
        this.value.set(startDate);
        this.editorDp.setValue(startDate);
        formatValueToLabel();
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
            this.value.set(editorDp.getValue());
            formatValueToLabel();
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
        this.editorDp.setValue(this.value.get());
    }

    @Override
    protected void formatValueToLabel() {
        LocalDateStringConverter converter = new LocalDateStringConverter();
        this.text.set(converter.toString(this.value.get()));
    }

    @Override
    public void setValue(LocalDate value) {
        super.setValue(value);
        this.editorDp.setValue(value);
        formatValueToLabel();
    }

}
