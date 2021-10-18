package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.AssociationDTO;
import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.dto.SimpleIdNameDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.AssociationMapper;
import fr.vvlabs.hibernate.sample.mapper.AssociationMapper;
import fr.vvlabs.hibernate.sample.model.Association;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.repository.AssociationRepository;
import fr.vvlabs.hibernate.sample.repository.AssociationRepository;
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
public class AssociationService {

    private final AssociationRepository repository;
    private final AssociationMapper mapper;
    private final PersonneRepository personneRepository;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<AssociationDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AssociationDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public AssociationDTO findById(Integer id) throws SampleException {
        return repository.findById(id).map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(final AssociationDTO dto) throws SampleException{
        Association association = mapper.mapToModel(dto);
        // a cet instant, les liens entités liées ne sont pas chargées
        return updateLinks(dto, repository.save(association)).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer update(final Integer id, final AssociationDTO dto) throws SampleException {
        Optional<Association> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Association association = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(association, dto);
            // a cet instant, les liens entités liées ne sont pas chargées
            updateLinks(dto, association);
        } else {
            throw new SampleException("association " + id + " not saved");
        }
        return id;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }

    private Association updateLinks(AssociationDTO dto, Association association) throws SampleException {
        // chargement des membres
        association.setMembres(new HashSet<>());
        for(SimpleIdNameDTO personneDto : CollectionUtils.emptyIfNull(dto.getMembers())) {
            Personne membre = personneRepository.findById(personneDto.getId())
                .orElseThrow(() -> new SampleException("Person " + personneDto.getId() + " not found !"));
            // Personne est MAITRE de la relation Personne>Association
            membre.getAssociations().add(association);
            personneRepository.save(membre);
        }
        return repository.save(association);
    }
}
