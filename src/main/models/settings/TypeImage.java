package main.models.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.models.settings.interfaces.GridImage;
import main.models.settings.interfaces.Type;

public class TypeImage implements CustomizableTypeImage {
  
  @JsonSerialize
  private String name;
  @JsonSerialize
  private Type type;
  @JsonSerialize
  private GridImage grid;
  
  @JsonCreator
  public TypeImage(@JsonProperty("name") final String name,@JsonProperty("type") final Type type,@JsonProperty("grid") final Grid grid) {
       this.name = name;
       this.type = type;
       this.grid = grid;
  }

  @Override
  public String getName() {
      return this.name;
  }

  @Override
  public Type getType() {
      return this.type;
  }

  @Override
  public GridImage getGrid() {
    return this.grid;
  }

  @Override
  public void setGrid(GridImage grid) {
    this.grid = grid;
  }

  @Override
  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
