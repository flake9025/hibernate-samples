package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.EmployeurDTO;
import fr.vvlabs.hibernate.sample.dto.SimpleIdNameDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.EmployeurMapper;
import fr.vvlabs.hibernate.sample.model.Employe;
import fr.vvlabs.hibernate.sample.model.Employeur;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.repository.EmployeurRepository;
import fr.vvlabs.hibernate.sample.repository.PersonneRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeurService {

    private final EmployeurRepository repository;
    private final EmployeurMapper mapper;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<EmployeurDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<EmployeurDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public EmployeurDTO findById(Integer id) throws SampleException {
        return repository.findById(id).map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(final EmployeurDTO dto) throws SampleException{
        Employeur employeur = mapper.mapToModel(dto);
        // save
        return repository.save(employeur).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer update(final Integer id, final EmployeurDTO dto) throws SampleException {
        Optional<Employeur> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Employeur employeur = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(employeur, dto);
            // save
            repository.save(employeur);
        } else {
            throw new SampleException("employer " + id + " not saved");
        }
        return id;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }
}
