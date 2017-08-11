package testapp;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Antoine on 10/08/2017.
 */
public class TestImageDb {

    public static byte[] fileToBytes(File imagefile){

        byte[] bFile = new byte[(int) imagefile.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(imagefile);
            //convert file into array of bytes
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bFile;
    }

    public static Image bytesToImage(byte[] bytes){

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        Image image = new Image(bais);

        return image;

    }



}
