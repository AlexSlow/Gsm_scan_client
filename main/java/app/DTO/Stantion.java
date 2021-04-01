package app.DTO;

import lombok.Data;


import java.util.Objects;

@Data
public class Stantion {
    private Integer id;
  private String name;
  private String username;
  private String password;
  private String host;
  private String file;
  private TypeConnection typeConnection;
  private Double coord_X;
  private Double coord_Y;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHost());
    }
}
