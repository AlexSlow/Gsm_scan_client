package app.DTO;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class StantionSpeachDTO {
    private StantionDto stantionDto;
    private List<Speach> speachList;

    public StantionSpeachDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StantionSpeachDTO)) return false;
        StantionSpeachDTO that = (StantionSpeachDTO) o;
        return getStantionDto().equals(that.getStantionDto()) &&
                getSpeachList().equals(that.getSpeachList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStantionDto(), getSpeachList());
    }
}
