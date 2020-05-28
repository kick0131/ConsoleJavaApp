package samples.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class JsonMappingDummy {
  String name;
  Integer age;

  @Override
  public String toString() {
    return "name=" + this.getName() + ", age=" + this.getAge();
  }
}