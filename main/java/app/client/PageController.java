package app.client;

import app.DTO.Page;
import lombok.Data;

@Data
public class PageController {
    private Page page;
    private Integer stnationDtoId=-1;
    public void firstPage(){
        page.setStartRow(1);
    }
    public PageController(){
       init();
    }
    public PageController(Integer id){
        stnationDtoId=id;
        init();
    }
    private void init(){
        page=new Page();
        page.setLimit(100);
    }

}
