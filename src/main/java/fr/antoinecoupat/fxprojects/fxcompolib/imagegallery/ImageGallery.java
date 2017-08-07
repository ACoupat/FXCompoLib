package fr.antoinecoupat.fxprojects.fxcompolib.imagegallery;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polyline;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 07/08/2017.
 */
public class ImageGallery extends AnchorPane implements Initializable {


    @FXML
    private Polyline leftArrow;

    @FXML
    private ImageView currentImage;

    @FXML
    private Polyline rightArrow;

    @FXML
    private Label titleLabel;

    @FXML
    private HBox thumbnailSlot;

    @FXML
    private HBox imageContainer;


    private ObservableList<Image> imageList = FXCollections.observableArrayList();

    /**
     * Index of the image currently displayed
     */
    private int currentIndex = 0;

    private static double IMAGE_HEIGHT = 200;

    public ImageGallery() {

        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("fr/antoinecoupat/fxprojects/fxcompolib/fxml/imagegallery/ImageGallery.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.setClassLoader(getClass().getClassLoader());
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.currentImage.setPreserveRatio(true);

        Platform.runLater(()->{
            this.currentImage.fitHeightProperty().bind(this.imageContainer.heightProperty());
        });




        this.rightArrow.setOnMouseClicked(event->{

            int nbImages = this.imageList.size();
            int newIndex = (this.currentIndex + 1) % nbImages;
            System.out.println(newIndex);
            this.currentImage.setImage(this.imageList.get(newIndex));
            this.currentIndex = newIndex;

        });

        this.leftArrow.setOnMouseClicked(event->{

            int nbImages = this.imageList.size();
            int newIndex;
            if(this.currentIndex == 0){
                newIndex = nbImages - 1;
            }else{
                newIndex = (this.currentIndex - 1);
            }

            System.out.println(newIndex);
            this.currentImage.setImage(this.imageList.get(newIndex));
            this.currentIndex = newIndex;

        });

        //Setting the window to maximize the image on the screen
        this.currentImage.setOnMouseClicked(event->{

            //TODO Add arrows in the maximized view
            Stage maximizeStage = new Stage(StageStyle.UNDECORATED);

            double width = Screen.getPrimary().getVisualBounds().getWidth();
            double height = Screen.getPrimary().getVisualBounds().getHeight();

            maximizeStage.setWidth(0.85 * width);
            maximizeStage.setHeight(0.85 * height);

            BorderPane root = new BorderPane();
            ImageView maximizedView = new ImageView(this.currentImage.getImage());
            maximizedView.setPreserveRatio(true);
            maximizedView.setFitHeight(0.85 * maximizeStage.getHeight());
            root.setStyle("-fx-background-color: black;");

            maximizedView.setStyle("-fx-padding: 25px");
            root.setCenter(maximizedView);
            Scene scene = new Scene(root);
            scene.setOnKeyPressed(keyEvent->{
                if(keyEvent.getCode() == KeyCode.ESCAPE){
                    maximizeStage.close();
                }
            });

            scene.setOnMouseClicked(clickEvent->{
                maximizeStage.close();
            });

            maximizeStage.setScene(scene);

            maximizeStage.show();

        });

    }


    public void setImages(Iterable<Image> images){

        this.imageList.clear();
        for(Image image : images){
            this.imageList.add(image);
            System.out.println("image");
            ImageView thumbnail = new ImageView(image);
            thumbnail.setPreserveRatio(true);
            thumbnail.setFitHeight(85);
            this.thumbnailSlot.getChildren().add(thumbnail);

            thumbnail.setOnMouseClicked(event->{

                Image selectedImage = thumbnail.getImage();
                this.currentIndex = this.imageList.indexOf(selectedImage);
                this.currentImage.setImage(selectedImage);

            });
        }
        this.currentImage.setImage(this.imageList.get(0));

    }



}

