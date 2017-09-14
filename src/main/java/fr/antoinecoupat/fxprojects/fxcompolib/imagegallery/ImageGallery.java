package fr.antoinecoupat.fxprojects.fxcompolib.imagegallery;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polyline;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Antoine on 07/08/2017.
 */
public class ImageGallery extends AnchorPane implements Initializable {

    @FXML
    private Button leftArrow;

    @FXML
    private Button rightArrow;

    @FXML
    private ImageView currentImage;

    @FXML
    private Label titleLabel;

    @FXML
    private HBox thumbnailSlot;

    @FXML
    private HBox imageContainer;

    @FXML
    private Button addImageButton;

    @FXML
    private Label placeHolder;

    @FXML
    private ScrollPane scrollThumbnails;


    private BooleanProperty displayAddButton = new SimpleBooleanProperty(true);

    private ObservableList<Image> imageList = FXCollections.observableArrayList();

    private ObservableList<ImageView> thumbnailList = FXCollections.observableArrayList();

    private ObservableList<ImageGalleryAddListener> addListeners = FXCollections.observableArrayList();

    private ObservableList<ImageGalleryRemoveListener> removeListeners = FXCollections.observableArrayList();

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
        this.addImageButton.visibleProperty().bind(this.displayAddButton);
        setArrowsVisible(false);
        setPlaceHolderVisible(true);

        this.currentImage.fitHeightProperty().bind(this.imageContainer.heightProperty());

        this.rightArrow.setOnAction(event->{

            selectNext();

        });

        this.leftArrow.setOnAction(event->{

            selectPrevious();

        });

        this.addImageButton.setOnAction(event -> {

            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers Images","*.jpg","*.png","*.gif");
            chooser.getExtensionFilters().add(filter);

            List<File> selectedImageFiles = chooser.showOpenMultipleDialog(this.getScene().getWindow());

            if(selectedImageFiles != null && selectedImageFiles.size() != 0){

                for (File selectedImageFile : selectedImageFiles) {
                    Image newImage = new Image(selectedImageFile.toURI().toString());
                    addImage(new Image(selectedImageFile.toURI().toString()));
                    fireImageAddedEvent(selectedImageFile,newImage);
                }
            }
            selectLast();

        });

        this.imageList.addListener((ListChangeListener) (c->{
            setArrowsVisible(imageList.size() > 1 );
            setPlaceHolderVisible(imageList.size() == 0);
        }));

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
            maximizeStage.initModality(Modality.APPLICATION_MODAL);

            maximizeStage.show();

        });

    }

    /**
     * Highlights the thumbnail corresponding to the current
     * image that is displayed on top
     */
    private void highlightThumbnail(){

        this.thumbnailList.get(this.currentIndex).setEffect(new DropShadow());
    }

    /**
     * Resets the thumbnail highlighting of the current image
     */
    private void resetThumbnail(){

        this.thumbnailList.get(this.currentIndex).setEffect(null);
    }



    /**
     * Hides/Shows the placeholder that replaces the ImageView
     * @param value
     */
    private void setPlaceHolderVisible(boolean value) {

        this.currentImage.setVisible(!value);
        this.currentImage.setManaged(!value);

        this.placeHolder.setVisible(value);
        this.placeHolder.setManaged(value);

    }

    /**
     * Hides/Shows the arrows
     * @param value
     */
    private void setArrowsVisible(boolean value) {
        this.leftArrow.setVisible(value);
        this.rightArrow.setVisible(value);
    }

    /**
     * Selects the previous image in the list and displays it
     * in the top view.
     */
    private void selectPrevious(){
        int nbImages = this.imageList.size();
        int newIndex;
        if(this.currentIndex == 0){
            newIndex = nbImages - 1;
        }else{
            newIndex = (this.currentIndex - 1);
        }

        this.currentImage.setImage(this.imageList.get(newIndex));
        resetThumbnail();
        this.currentIndex = newIndex;
        changeHighlighting(newIndex);
    }

    /**
     * Selects the next image in the list and displays it
     * in the top view.
     */
    private void selectNext(){

        int nbImages = this.imageList.size();
        int newIndex = (this.currentIndex + 1) % nbImages;
        this.currentImage.setImage(this.imageList.get(newIndex));
        changeHighlighting(newIndex);
    }

    /**
     * Selects the last image in the list and displays it
     * in the top view.
     */
    private void selectLast(){
        int nbImages = this.imageList.size();
        this.currentImage.setImage(this.imageList.get(nbImages - 1));
        changeHighlighting(nbImages - 1);
    }

    /**
     * Removes the highlighting on the current thumbnail
     * and highlights another
     * @param newIndex index of the new thumbnail to highlight
     */
    private void changeHighlighting(int newIndex){
        resetThumbnail();
        this.currentIndex = newIndex;
        highlightThumbnail();
        adjustView();
    }

    /**
     * Changes the position of the scroll pane to position
     * the current thumbnail in the center of the view
     */
    private void adjustView(){

        Node concernedThumbnail = this.thumbnailSlot.getChildren().get(this.currentIndex);
        double leftX = concernedThumbnail.getBoundsInParent().getMinX();
        double scrollRatio = leftX / (this.thumbnailSlot.getBoundsInParent().getWidth() -
                this.thumbnailSlot.getPadding().getLeft() -
                this.thumbnailSlot.getPadding().getRight() -
                this.thumbnailSlot.getChildren().get(this.thumbnailSlot.getChildren().size()-1).getBoundsInParent().getWidth()
        );

        this.scrollThumbnails.setHvalue(scrollRatio);
        
    }

    public void setImages(List<Image> images){
        this.imageList.clear();
        if(images.size() > 0){
            for(Image image : images){
                addImage(image);
            }
            this.currentImage.setImage(this.imageList.get(0));
        }
    }

    private void addImage(Image image){

        this.imageList.add(image);
        ImageView thumbnail = new ImageView(image);
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitHeight(85);
        this.thumbnailSlot.getChildren().add(thumbnail);
        this.thumbnailList.add(thumbnail);

        thumbnail.setOnMouseClicked(event->{

            if(event.getButton() == MouseButton.PRIMARY) {
                Image selectedImage = thumbnail.getImage();
                changeHighlighting(this.imageList.indexOf(selectedImage));
                this.currentImage.setImage(selectedImage);
            }
            else if(event.getButton() == MouseButton.SECONDARY){

                ContextMenu menu = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Supprimer");
                deleteItem.setOnAction(actionEvent->{
                    removeImage(this.thumbnailList.indexOf(thumbnail));
                });
                menu.getItems().add(deleteItem);
                menu.show(thumbnail, event.getScreenX(), event.getScreenY());
            }
        });

    }

    private void removeImage(int index){

        //If the image that will be deleted is currently displayed
        if(currentIndex == index){
            if(this.imageList.size() > 1){

                this.selectNext();
                this.currentIndex --;
            }
        }
        this.thumbnailSlot.getChildren().remove(this.thumbnailList.get(index));
        this.thumbnailList.remove(index);

        Image removedImage = this.imageList.get(index);
        this.imageList.remove(removedImage);
        fireImageRemovedEvent(removedImage);

    }

    public boolean isDisplayAddButton() {
        return displayAddButton.get();
    }

    public BooleanProperty displayAddButtonProperty() {
        return displayAddButton;
    }

    public void setDisplayAddButton(boolean displayAddButton) {
        this.displayAddButton.set(displayAddButton);
    }

    //Listeners Management
    public void addAddListener(ImageGalleryAddListener listener){
        this.addListeners.add(listener);
    }

    public void removeAddListener(ImageGalleryAddListener listener){
        this.addListeners.remove(listener);
    }

    public void addRemoveListener(ImageGalleryRemoveListener listener){
        this.removeListeners.add(listener);
    }

    public void removeRemoveListener(ImageGalleryRemoveListener listener){
        this.removeListeners.remove(listener);
    }

    public void fireImageAddedEvent(File imageFile, Image image){
        for(ImageGalleryAddListener listener : this.addListeners){
            listener.imageAdded(imageFile, image);
        }
    }

    public void fireImageRemovedEvent(Image image){
        for(ImageGalleryRemoveListener listener : this.removeListeners){
            listener.imageRemoved(image);
        }
    }

}

