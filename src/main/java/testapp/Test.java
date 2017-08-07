package testapp;

import fr.antoinecoupat.fxprojects.fxcompolib.editablelabel.EditableLabel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Antoine on 07/08/2017.
 */
public class Test extends Application{

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();

        EditableLabel label = new EditableLabel();
        root.getChildren().add(label);

        EditableLabel label2 = new EditableLabel();
        root.getChildren().add(label2);
        AnchorPane.setTopAnchor(label2, 45.0);


        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();


    }
}
