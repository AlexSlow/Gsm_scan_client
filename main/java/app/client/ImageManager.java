package app.client;

import app.client.Controller.MainController;
import javafx.scene.image.Image;

/**
 * Выдает картинки статуса
 */
public class ImageManager {
    public enum TypeImage{
        error,online,offlaine
    }
    public Image getImage(TypeImage typeImage){
        switch (typeImage)
        {
            case error:
               // System.out.println(MainController.class.getClassLoader().getResource("img/Error.png"));
                return new Image(getClass().getClassLoader().getResource("img/Error.png").toString());
            case online:
                return new Image(getClass().getClassLoader().getResource("img/Online.png").toString());
            case offlaine:
                return new Image(getClass().getClassLoader().getResource("img/Offline.png").toString());
        }
        throw new RuntimeException("Нет таких картинок!");
    }
}
