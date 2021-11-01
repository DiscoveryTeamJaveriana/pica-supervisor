package co.edu.javeriana.discovery.pica.supervisor.service.impl;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.service.ISupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupervisorService implements ISupervisorService {
    @Override
    public void postSupervisor(ReqPostSupervisor request, String rquid) {
    log.info("Panic implement me !");
    }

    @Override
    public void postAutenticacion(ReqPostAutenticacion request, String rquid) {
        log.info("Panic implement me !");
    }

    @Override
    public RespGetSupervisor getSupervisor(String codigo, String rquid) {
        RespGetSupervisor respGetSupervisor = new RespGetSupervisor();
        respGetSupervisor.setCodigo("123");
        respGetSupervisor.setNombre("Mauro");
        respGetSupervisor.setIdentificacion("12345678");
        respGetSupervisor.setCorreo("mauro@gmail.com");
        respGetSupervisor.setTelefono("310310310");
        respGetSupervisor.setUsuario("Mauro123");
        return respGetSupervisor;
    }
}
