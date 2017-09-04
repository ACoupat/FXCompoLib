package fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editabledatepicker;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.EditableControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


/**
 * Created by Antoine on 31/08/2017.
 */
public class EditableDatePicker extends EditableControl implements Initializable {

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());

    private DatePicker editorDp = new DatePicker();

    private ObjectProperty<DateFormat> dateFormat = new SimpleObjectProperty<DateFormat>(new SimpleDateFormat("dd/MM/yyyy"));

    public EditableDatePicker() {

        this.editorNode = editorDp;

        this.dateFormat.addListener((obs, ov, nv)->{
            formatDateToLabel();
        });

    }

    @Override
    protected void validateValue() {
        this.date.set(editorDp.getValue());
        formatDateToLabel();
        showLabel();
    }

    @Override
    protected void discardValue() {
        showLabel();
    }

    private void formatDateToLabel(){
        this.label.setText(dateFormat.get().format(this.date.get().toEpochDay()));
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

    public DateFormat getDateFormat() {
        return dateFormat.get();
    }

    public ObjectProperty<DateFormat> dateFormatProperty() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat.set(dateFormat);
    }
}
