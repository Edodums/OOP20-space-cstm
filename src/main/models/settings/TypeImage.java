package main.models.settings;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class TypeImage {
  
  @JsonSerialize
  private String name;
  @JsonSerialize
  private Type type;
  @JsonSerialize
  private Grid grid;

  public TypeImage() {}
  
  public TypeImage(final String name, final Type type, final Grid grid) {
       this.name = name;
       this.type = type;
       this.grid = grid;
  }
  
  public String getName() {
      return this.name;
  }
  
  public Type getType() {
      return this.type;
  }
  
  public Grid getGrid() {
    return this.grid;
  }
  
  public void setGrid(Grid grid) {
    this.grid = grid;
  }
  
  public void setType(Type type) {
    this.type = type;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
