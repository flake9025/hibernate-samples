package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Employe;
import fr.vvlabs.hibernate.sample.model.Employe.EmployeId;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, EmployeId> {

  Optional<Employe> findByEmployeurIdAndPersonneId(Integer employeurId, Integer employeId);

  boolean existsByEmployeurIdAndPersonneId(Integer employeurId, Integer employeId);

  void deleteByEmployeurIdAndPersonneId(Integer employeurId, Integer employeId);
}
