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
                    if (item.isActive())
                        imageView=new ImageView(imageManager.getImage(ImageManager.TypeImage.online));
                    else imageView=new ImageView(imageManager.getImage(ImageManager.TypeImage.error));
                    setText(item.getName());
                    setGraphic(imageView);
                }
            }
        };
}
}
