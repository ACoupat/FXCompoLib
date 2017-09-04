package testapp;

import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editabledatepicker.EditableDatePicker;
import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editablelabel.EditableLabel;
import fr.antoinecoupat.fxprojects.fxcompolib.editablecontrol.editablespinner.EditableSpinner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

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

        //Test Image storage
        File imgFile = new File("D:/Images/Autres/photo profil facebookCharlie.png");

        byte[] bytes = TestImageDb.fileToBytes(imgFile);

        Image img = TestImageDb.bytesToImage(bytes);

        ImageView imgv = new ImageView(img);

       // root.getChildren().add(imgv);


       /* EditableLabel label = new EditableLabel();
        root.getChildren().add(label);*/

       /* ImageGallery gallery = new ImageGallery();
        List<Image> imageList = new ArrayList<>();
        File imageDir = new File("D:/Images/Autres");
        File[] filesList = imageDir.listFiles();

        for(File file : filesList){

            if(file.getName().endsWith(".jpg") ||
                    file.getName().endsWith(".png")){
                imageList.add(new Image("file:///"+file.getAbsolutePath()));
            }
        }


        gallery.setImages(imageList);*/

        //root.getChildren().add(gallery);

        EditableLabel label = new EditableLabel("Labeleuh");

        EditableDatePicker dp = new EditableDatePicker();

        EditableSpinner sp = new EditableSpinner();

        Scene scene = new Scene(root,400,400);
        root.getChildren().add(label);
        root.getChildren().add(dp);
        root.getChildren().add(sp);
        dp.setTranslateX(200);
        sp.setTranslateY(100);

        primaryStage.setScene(scene);

        //ScenicView.show(scene);

        primaryStage.show();


    }
}
