package co.edu.javeriana.discovery.pica.supervisor.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class ReqPostSupervisor {

  @JsonProperty("Nombre")
  private String nombre = null;

  @JsonProperty("Identificacion")
  private String identificacion = null;

  @JsonProperty("Correo")
  private String correo = null;

  @JsonProperty("Telefono")
  private String telefono = null;

  @JsonProperty("Usuario")
  private String usuario = null;

  @JsonProperty("Clave")
  private String clave = null;

}

