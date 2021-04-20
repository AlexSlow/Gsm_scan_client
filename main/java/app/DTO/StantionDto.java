package app.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class StantionDto implements Serializable {
    private Long id;
    private String name;
    private StantionStatus status;
    public StantionDto(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StantionDto)) return false;
        StantionDto that = (StantionDto) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
      return name;
}
}
