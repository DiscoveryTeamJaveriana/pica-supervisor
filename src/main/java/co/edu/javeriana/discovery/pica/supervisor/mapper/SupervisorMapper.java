package co.edu.javeriana.discovery.pica.supervisor.mapper;

import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostAutenticacion;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.ReqPostSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.controller.model.RespGetSupervisor;
import co.edu.javeriana.discovery.pica.supervisor.repository.model.Supervisor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupervisorMapper {

    public static RespGetSupervisor mapSupervisorToRespGetSupervisor(final Supervisor supervisor) {

        RespGetSupervisor respGetSupervisor = new RespGetSupervisor();
        respGetSupervisor.setCodigo(supervisor.getId());
        respGetSupervisor.setNombre(supervisor.getName());
        respGetSupervisor.setIdentificacion(supervisor.getIdentification());
        respGetSupervisor.setCorreo(supervisor.getEmail());
        respGetSupervisor.setTelefono(supervisor.getPhone());
        respGetSupervisor.setUsuario(supervisor.getUsername());

        return respGetSupervisor;
    }

    public static Supervisor mapReqPostSupervisorToSupervisor(final ReqPostSupervisor reqPostSupervisor) {

        return Supervisor.builder()
                .name(reqPostSupervisor.getNombre())
                .identification(reqPostSupervisor.getIdentificacion())
                .email(reqPostSupervisor.getCorreo())
                .phone(reqPostSupervisor.getTelefono())
                .username(reqPostSupervisor.getUsuario())
                .password(reqPostSupervisor.getClave())
                .build();
    }

    public static Supervisor mapReqPostAutenticacionToSupervisor(final ReqPostAutenticacion reqPostAutenticacion) {

        return Supervisor.builder()
                .username(reqPostAutenticacion.getUsuario())
                .password(reqPostAutenticacion.getClave())
                .build();
    }
}
