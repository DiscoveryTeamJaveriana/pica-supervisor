package co.edu.javeriana.discovery.pica.supervisor.service.impl;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.mapper.SupervisorMapper;
import co.edu.javeriana.discovery.pica.supervisor.repository.SupervisorRepository;
import co.edu.javeriana.discovery.pica.supervisor.repository.model.Supervisor;
import co.edu.javeriana.discovery.pica.supervisor.service.ISupervisorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupervisorService implements ISupervisorService {

    private final SupervisorRepository supervisorRepository;

    @Override
    public void postSupervisor(ReqPostSupervisor request, String rquid) {

        Supervisor supervisor = SupervisorMapper.mapReqPostSupervisorToSupervisor(request);
        supervisorRepository.save(supervisor);

    }

    @Override
    public void postAutenticacion(ReqPostAutenticacion request, String rquid) {

        supervisorRepository.findSupervisorByUsername(request.getUsuario());
        //TODO:Authentication Implementation
    }

    @Override
    public RespGetSupervisor getSupervisor(String codigo, String rquid) {

        return SupervisorMapper.mapSupervisorToRespGetSupervisor(supervisorRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No Supervisor")));
        //TODO: Add ControllerAdvice for exception control
    }
}
