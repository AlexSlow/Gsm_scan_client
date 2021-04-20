package app.client;

import app.DTO.Page;
import lombok.Data;

/**
 * Хранит информацию о текущей станции и странице
 */
@Data
public class PageController {
    private Page page;
    private Long stnationDtoId=-1l;
    public void firstPage(){
        page.setStartRow(1);
    }

    public PageController(){
       init();
    }
    public PageController(Long id){
        stnationDtoId=id;
        init();
    }
    private void init(){
        page=new Page();
        page.setLimit(50);
    }

}
