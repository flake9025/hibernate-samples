package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.EnfantDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.EnfantMapper;
import fr.vvlabs.hibernate.sample.model.Enfant;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.repository.EnfantRepository;
import fr.vvlabs.hibernate.sample.repository.PersonneRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnfantService {

    private final EnfantRepository repository;
    private final EnfantMapper mapper;
    private final PersonneRepository personneRepository;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<EnfantDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<EnfantDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public EnfantDTO findById(Integer id) throws SampleException {
        return repository.findById(id).map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Transactional
    public Integer create(final EnfantDTO dto) throws SampleException{
        Enfant enfant = mapper.mapToModel(dto);
        // a cet instant, les liens entités liées ne sont pas chargées
        return updateLinks(dto, repository.save(enfant)).getId();
    }

    @Transactional
    public Integer update(final Integer id, final EnfantDTO dto) throws SampleException {
        Optional<Enfant> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Enfant enfant = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(enfant, dto);
            // a cet instant, les liens entités liées ne sont pas chargées
            updateLinks(dto, enfant);
        } else {
            throw new SampleException("child " + id + " not saved");
        }
        return id;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }

    private Enfant updateLinks(EnfantDTO dto, Enfant enfant) throws SampleException {
        // chargement des parents
        if(dto.getFather() != null && dto.getFather().getId() > 0) {
            Personne pere = personneRepository.findById(dto.getFather().getId())
                .orElseThrow(() -> new SampleException("Person " + dto.getFather().getId() + " not found !"));
            enfant.setPere(pere);
        }
        if(dto.getMother() != null && dto.getMother().getId() > 0) {
            Personne mere = personneRepository.findById(dto.getMother().getId())
                .orElseThrow(() -> new SampleException("Person " + dto.getMother().getId() + " not found !"));
            enfant.setMere(mere);
        }
        return repository.save(enfant);
    }
}
