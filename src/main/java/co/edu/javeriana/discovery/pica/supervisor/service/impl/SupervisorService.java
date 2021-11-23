package co.edu.javeriana.discovery.pica.supervisor.service.impl;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.*;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.Error;
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
        try {
            supervisorRepository.save(supervisor);
            }catch (Exception e) {
            log.info("Error al crear supervisor");
            throw e;
        }
    }

    @Override
    public RespPostAutenticacion postAutenticacion(ReqPostAutenticacion request, String rquid) {
        try {
            Supervisor supervisor = supervisorRepository.findSupervisorByUsername(request.getUsuario());
            if (supervisor.getPassword().equals(request.getClave())){
                log.info("Inicio de sesion exitoso");
                RespPostAutenticacion response = new RespPostAutenticacion();
                response.setId(supervisor.getId());
                response.setNombre(supervisor.getName());
                response.setCorreo(supervisor.getEmail());
                return response;
            }else throw new RuntimeException("Clave incorrecta");
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public RespGetSupervisor getSupervisor(String codigo, String rquid) {

        return SupervisorMapper.mapSupervisorToRespGetSupervisor(supervisorRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No Supervisor")));
        //TODO: Add ControllerAdvice for exception control
    }
}
