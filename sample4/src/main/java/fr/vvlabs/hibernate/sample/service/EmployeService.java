package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.EmployeDTO;
import fr.vvlabs.hibernate.sample.dto.SimpleIdNameDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.EmployeMapper;
import fr.vvlabs.hibernate.sample.model.Employe;
import fr.vvlabs.hibernate.sample.model.Employe.EmployeId;
import fr.vvlabs.hibernate.sample.model.Employeur;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import fr.vvlabs.hibernate.sample.repository.EmployeRepository;
import fr.vvlabs.hibernate.sample.repository.EmployeurRepository;
import fr.vvlabs.hibernate.sample.repository.PersonneRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository repository;
    private final EmployeMapper mapper;
    private final EmployeurRepository employeurRepository;
    private final PersonneRepository personneRepository;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<EmployeDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public EmployeDTO findById(Integer employerId, Integer personId) throws SampleException {
        return repository.findByIdEmployeurIdAndIdPersonneId(employerId, personId)
            .map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer employerId, Integer personId) {
        return repository.existsByIdEmployeurIdAndIdPersonneId(employerId, personId);
    }

    @Transactional(rollbackFor = Exception.class)
    public EmployeId create(final EmployeDTO dto) throws SampleException{
        Employe employe = mapper.mapToModel(dto);
        // a cet instant, les liens entités liées ne sont pas chargées
        return updateLinks(dto, repository.save(employe)).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public EmployeId update(final Integer employerId, final Integer personId, final EmployeDTO dto) throws SampleException {
        Optional<Employe> optionalModel = repository.findByIdEmployeurIdAndIdPersonneId(employerId, personId);
        EmployeId key = null;
        if (optionalModel.isPresent()) {
            Employe employe = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setEmployer(SimpleIdNameDTO.builder().id(employerId).build());
            dto.setPerson(SimpleIdNameDTO.builder().id(personId).build());
            // update model
            mapper.updateModel(employe, dto);
            // save
            key = updateLinks(dto, employe).getId();
        } else {
            throw new SampleException("employee " + employerId + "," + personId + " not saved");
        }
        return key;
    }

    @Transactional
    public void deleteById(final Integer employerId, final Integer personId) {
        repository.deleteByIdEmployeurIdAndIdPersonneId(employerId, personId);
    }

    private Employe updateLinks(EmployeDTO dto, Employe employe) throws SampleException {
        EmployeId id = new EmployeId();
        // chargement de l'employeur
        if(dto.getEmployer() != null && dto.getEmployer().getId() > 0) {
            Employeur employeur = employeurRepository.findById(dto.getEmployer().getId())
                .orElseThrow(() -> new SampleException("Employer " + dto.getEmployer().getId() + " not found !"));
            id.setEmployeur(employeur);
        }
        // chargement de la personne
        if(dto.getPerson() != null && dto.getPerson().getId() > 0) {
            Personne personne = personneRepository.findById(dto.getPerson().getId())
                .orElseThrow(() -> new SampleException("Person " + dto.getPerson().getId() + " not found !"));
            id.setPersonne(personne);
        }
        return repository.save(employe);
    }
}
