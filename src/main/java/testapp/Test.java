package testapp;

import fr.antoinecoupat.fxprojects.fxcompolib.editablelabel.EditableLabel;
import fr.antoinecoupat.fxprojects.fxcompolib.imagegallery.ImageGallery;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        ImageGallery gallery = new ImageGallery();
        List<Image> imageList = new ArrayList<>();
        File imageDir = new File("D:/Images/Autres");
        File[] filesList = imageDir.listFiles();

        for(File file : filesList){

            if(file.getName().endsWith(".jpg") ||
                    file.getName().endsWith(".png")){
                imageList.add(new Image("file:///"+file.getAbsolutePath()));
            }
        }


        gallery.setImages(imageList);

        root.getChildren().add(gallery);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        //ScenicView.show(scene);

        primaryStage.show();


    }
}
