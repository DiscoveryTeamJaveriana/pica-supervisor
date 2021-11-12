package co.edu.javeriana.discovery.pica.supervisor.controller;

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

    private ISupervisorService supervisorService;

    @Autowired
    private Tracer tracer;

    @Autowired
    public SupervisorController(ISupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }


    @PostMapping("/Supervisor")
    public ResponseEntity<Void> postSupervisor(@RequestBody ReqPostSupervisor reqPostSupervisor,  @RequestHeader(value=XRQUID) String xRqUID ) throws JsonProcessingException {
        log.info("Creating Supervisor for RqUID {}", xRqUID);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reqPostSupervisor);
        SupervisorController.this.tracer.currentSpan().tag(REQUEST,json);
        SupervisorController.this.tracer.currentSpan().tag(RQUID,xRqUID);
        supervisorService.postSupervisor(reqPostSupervisor, xRqUID);
        SupervisorController.this.tracer.currentSpan().tag(RESPONSECODE,HttpStatus.CREATED.toString());
        return new ResponseEntity<>(putRqUIDHeader(xRqUID),HttpStatus.CREATED);
    }

    @PostMapping("/Supervisor/Autenticacion")
    public ResponseEntity<Void> postAutenticacion(@RequestBody ReqPostAutenticacion reqPostAutenticacion, @RequestHeader(value=XRQUID) String xRqUID ) throws JsonProcessingException {
        log.info("Authentication for RqUID {}", xRqUID);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reqPostAutenticacion);
        SupervisorController.this.tracer.currentSpan().tag(REQUEST,json);
        SupervisorController.this.tracer.currentSpan().tag(RQUID,xRqUID);
        supervisorService.postAutenticacion(reqPostAutenticacion, xRqUID);
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
        headers.set(RQUID,rquid);
        return headers;
    }
}
