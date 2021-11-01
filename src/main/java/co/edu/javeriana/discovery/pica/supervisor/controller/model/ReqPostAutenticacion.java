package co.edu.javeriana.discovery.pica.supervisor.controller.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class ReqPostAutenticacion {
  @JsonProperty("Usuario")
  private String usuario = null;

  @JsonProperty("Clave")
  private String clave = null;

}

