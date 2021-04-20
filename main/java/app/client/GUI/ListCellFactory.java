package app.client.GUI;

import app.DTO.StantionDto;
import app.client.ImageManager;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class ListCellFactory {
    public static ListCell<StantionDto> getCell(){
        return new ListCell<StantionDto>(){
            @Override
            protected void updateItem(StantionDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView imageView;
                    ImageManager imageManager=new ImageManager();
                    switch (item.getStatus())
                    {
                        case Online:
                            imageView=new ImageView(imageManager.getImage(ImageManager.TypeImage.online));
                            break;
                        case Offline:
                            imageView=new ImageView(imageManager.getImage(ImageManager.TypeImage.offlaine));
                            break;
                        case notAvailable:
                            default:
                            imageView=new ImageView(imageManager.getImage(ImageManager.TypeImage.error));
                            break;
                    }
                    setText(item.getName());
                    setGraphic(imageView);
                }
            }
        };
}
}
