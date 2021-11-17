package co.edu.javeriana.discovery.pica.supervisor.repository;

import co.edu.javeriana.discovery.pica.supervisor.repository.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, String> {

    Supervisor findSupervisorByUsername(String username);
}
