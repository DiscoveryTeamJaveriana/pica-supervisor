package co.edu.javeriana.discovery.pica.supervisor.controller;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.Error;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.service.ISupervisorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/c3p/v1/Portal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupervisorController {

    private static final String XRQUID = "X-RqUID";
    private static final String CODIGO = "Codigo";
    private static final String REQUEST = "Request";
    private static final String RESPONSE = "Response";
    private static final String RESPONSECODE = "ResponseCode";
    private static final String RQUID = "RqUID";
    private static final String ERRORCREACION = "Error al crear supervisor";
    private static final String CODIGOERRORCREACION = "300";
    private static final String ERRORAUTENTICACION = "Error al autenticar usuario";
    private static final String CODIGOERRORAUTENTICACION = "100";

    private ISupervisorService supervisorService;

    @Autowired
    private Tracer tracer;

    @Autowired
    public SupervisorController(ISupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }


    @PostMapping("/Supervisor")
    public ResponseEntity<Object> postSupervisor(@RequestBody ReqPostSupervisor reqPostSupervisor,  @RequestHeader(value=XRQUID) String xRqUID ) throws JsonProcessingException {
        log.info("Creating Supervisor for RqUID {}", xRqUID);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reqPostSupervisor);
        SupervisorController.this.tracer.currentSpan().tag(REQUEST,json);
        SupervisorController.this.tracer.currentSpan().tag(RQUID,xRqUID);
        try {
            supervisorService.postSupervisor(reqPostSupervisor, xRqUID);
        }catch (Exception e) {
            SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.PARTIAL_CONTENT.toString());
            String jsonError = mapper.writeValueAsString(buildError(ERRORCREACION,CODIGOERRORCREACION));
            SupervisorController.this.tracer.currentSpan().tag(RESPONSE,jsonError);
            return new ResponseEntity<>(jsonError,putRqUIDHeader(xRqUID),HttpStatus.PARTIAL_CONTENT);
        }
        SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.CREATED.toString());
        return new ResponseEntity<>(putRqUIDHeader(xRqUID),HttpStatus.CREATED);
    }

    @PostMapping("/Supervisor/Autenticacion")
    public ResponseEntity<Object> postAutenticacion(@RequestBody ReqPostAutenticacion reqPostAutenticacion, @RequestHeader(value=XRQUID) String xRqUID ) throws JsonProcessingException {
        log.info("Authentication for RqUID {}", xRqUID);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reqPostAutenticacion);
        SupervisorController.this.tracer.currentSpan().tag(REQUEST,json);
        SupervisorController.this.tracer.currentSpan().tag(RQUID,xRqUID);
        try {
            supervisorService.postAutenticacion(reqPostAutenticacion, xRqUID);
        }catch (Exception e) {
            SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.PARTIAL_CONTENT.toString());
            String jsonError = mapper.writeValueAsString(buildError(ERRORAUTENTICACION,CODIGOERRORAUTENTICACION));
            SupervisorController.this.tracer.currentSpan().tag(RESPONSE,jsonError);
            return new ResponseEntity<>(jsonError,putRqUIDHeader(xRqUID),HttpStatus.PARTIAL_CONTENT);
        }
        SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.NO_CONTENT.toString());
        return new ResponseEntity<>(putRqUIDHeader(xRqUID),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Supervisor/{Codigo}")
    public ResponseEntity<RespGetSupervisor> getSupervisor( @RequestHeader(value=XRQUID) String xRqUID, @PathVariable(CODIGO) String codigo) throws JsonProcessingException {
        log.info("Get Supervisor for RqUID {}", xRqUID);
        SupervisorController.this.tracer.currentSpan().tag(RQUID,xRqUID);
        RespGetSupervisor response = supervisorService.getSupervisor(codigo, xRqUID);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);
        SupervisorController.this.tracer.currentSpan().tag(RESPONSE,json);
        SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.OK.toString());
        return new ResponseEntity<>(response,putRqUIDHeader(xRqUID),HttpStatus.OK);
    }

    private HttpHeaders putRqUIDHeader(String rquid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(XRQUID,rquid);
        return headers;
    }

    private Error buildError(String mensaje, String codigo) {
        Error error = new Error();
        error.setCodigo(codigo);
        error.setMensaje(mensaje);
        return error;
    }
}
