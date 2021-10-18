package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.AssociationDTO;
import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.dto.SimpleIdNameDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.PersonneMapper;
import fr.vvlabs.hibernate.sample.model.Association;
import fr.vvlabs.hibernate.sample.model.Enfant;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import fr.vvlabs.hibernate.sample.repository.AssociationRepository;
import fr.vvlabs.hibernate.sample.repository.EnfantRepository;
import fr.vvlabs.hibernate.sample.repository.PersonneRepository;
import fr.vvlabs.hibernate.sample.repository.VehiculeRepository;
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
public class PersonneService {

    private final PersonneRepository repository;
    private final PersonneMapper mapper;
    private final VehiculeRepository vehiculeRepository;
    private final AssociationRepository associationRepository;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<PersonneDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PersonneDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public PersonneDTO findById(Integer id) throws SampleException {
        return repository.findById(id).map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Transactional
    public Integer create(final PersonneDTO dto) throws SampleException{
        Personne personne = mapper.mapToModel(dto);
        // a cet instant, les liens entités liées ne sont pas chargées
        return updateLinks(dto, repository.save(personne)).getId();
    }

    @Transactional
    public Integer update(final Integer id, final PersonneDTO dto) throws SampleException {
        Optional<Personne> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Personne personne = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(personne, dto);
            // a cet instant, les liens entités liées ne sont pas chargées
            updateLinks(dto, personne);
        } else {
            throw new SampleException("person " + id + " not saved");
        }
        return id;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }

    private Personne updateLinks(PersonneDTO dto, Personne personne) throws SampleException {
        // chargement du vehicule
        if(dto.getVehicle() != null && dto.getVehicle().getId() > 0) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getVehicle().getId())
                .orElseThrow(() -> new SampleException("Vehicle " + dto.getVehicle().getId() + " not found !"));
            personne.setVehicule(vehicule);
        }
        // chargement des associations
        personne.setAssociations(new HashSet<>());
        for(SimpleIdNameDTO associationDto : CollectionUtils.emptyIfNull(dto.getAssociations())) {
            Association association = associationRepository.findById(associationDto.getId())
                .orElseThrow(() -> new SampleException("Association " + associationDto.getId() + " not found !"));
            personne.getAssociations().add(association);
        }
        return repository.save(personne);
    }
}
