package app.DTO;

import lombok.Data;

@Data
public class PageStantionIdDto {
    Page page;
    Integer StantionId;
    public PageStantionIdDto(){

    }

    public PageStantionIdDto(Page page, Integer stantionId) {
        this.page = page;
        StantionId = stantionId;
    }
}
