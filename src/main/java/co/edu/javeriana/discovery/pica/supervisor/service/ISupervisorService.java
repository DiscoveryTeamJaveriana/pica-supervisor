package co.edu.javeriana.discovery.pica.supervisor.service;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;

public interface ISupervisorService {
    void postSupervisor (ReqPostSupervisor request, String rquid);
    void postAutenticacion (ReqPostAutenticacion request, String rquid);
    RespGetSupervisor getSupervisor (String codigo, String rquid);
}
