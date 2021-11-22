package co.edu.javeriana.discovery.pica.supervisor.controller.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class RespPostAutenticacion {
  @JsonProperty("Id")
  private String Id = null;
  @JsonProperty("Nombre")
  private String Nombre = null;
}

