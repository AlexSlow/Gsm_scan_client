package app.DTO;

import lombok.Data;

@Data
public class PageStantionIdDto {
    Page page;
    Integer StantionId;
    /**
     * Используется только в повторном обращении
     */
    Long SpeachId=0l;
    public PageStantionIdDto(){

    }

    public PageStantionIdDto(Page page, Integer stantionId) {
        this.page = page;
        StantionId = stantionId;
    }
}
