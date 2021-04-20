package app.DTO;

import lombok.Data;

@Data
public class PageStantionIdDto {
    Page page;
    Long StantionId;
    /**
     * Используется только в повторном обращении
     */
    Long SpeachId=0l;
    public PageStantionIdDto(){

    }

    public PageStantionIdDto(Page page, Long stantionId) {
        this.page = page;
        StantionId = stantionId;
    }
}
