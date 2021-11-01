package co.edu.javeriana.discovery.pica.supervisor.controller;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.service.ISupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/c3p/v1/Portal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupervisorController {

    private static final String RQUID = "X-RqUID";
    private static final String CODIGO = "Codigo";

    private ISupervisorService supervisorService;

    @Autowired
    public SupervisorController(ISupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }


    @PostMapping("/Supervisor")
    public ResponseEntity<Void> postSupervisor(@RequestBody ReqPostSupervisor reqPostSupervisor,  @RequestHeader(value=RQUID) String xRqUID ) {
        log.info("Creating Supervisor for RqUID {}", xRqUID);
        supervisorService.postSupervisor(reqPostSupervisor, xRqUID);
        return new ResponseEntity<>(putRqUIDHeader(xRqUID),HttpStatus.CREATED);
    }

    @PostMapping("/Supervisor/Autenticacion")
    public ResponseEntity<Void> postAutenticacion(@RequestBody ReqPostAutenticacion reqPostAutenticacion, @RequestHeader(value=RQUID) String xRqUID ) {
        log.info("Authentication for RqUID {}", xRqUID);
        supervisorService.postAutenticacion(reqPostAutenticacion, xRqUID);
        return new ResponseEntity<>(putRqUIDHeader(xRqUID),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Supervisor/{Codigo}")
    public ResponseEntity<RespGetSupervisor> getSupervisor( @RequestHeader(value=RQUID) String xRqUID, @PathVariable(CODIGO) String codigo) {
        log.info("Get Supervisor for RqUID {}", xRqUID);
        RespGetSupervisor response = supervisorService.getSupervisor(codigo, xRqUID);
        return new ResponseEntity<>(response,putRqUIDHeader(xRqUID),HttpStatus.OK);
    }

    private HttpHeaders putRqUIDHeader(String rquid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(RQUID,rquid);
        return headers;
    }
}
