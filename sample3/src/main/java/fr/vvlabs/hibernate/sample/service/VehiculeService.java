package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.dto.VehiculeDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.VehiculeMapper;
import fr.vvlabs.hibernate.sample.model.Personne;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import fr.vvlabs.hibernate.sample.repository.PersonneRepository;
import fr.vvlabs.hibernate.sample.repository.VehiculeRepository;
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
public class VehiculeService {

    private final VehiculeRepository repository;
    private final VehiculeMapper mapper;
    private final PersonneRepository personneRepository;

    @Transactional(readOnly = true)
    public long countAll() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<VehiculeDTO> findAll(Pageable page) {
        return repository.findAll(page).map(mapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public VehiculeDTO findById(Integer id) throws SampleException {
        return repository.findById(id).map(mapper::mapToDto).orElseThrow(() -> new SampleException("not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Transactional
    public Integer create(final VehiculeDTO dto) throws SampleException {
        Vehicule vehicule = mapper.mapToModel(dto);
        // a cet instant, les liens entités liées ne sont pas chargées
        return updateLinks(dto, repository.save(vehicule)).getId();
    }

    @Transactional
    public Integer update(final Integer id, final VehiculeDTO dto) throws SampleException {
        Optional<Vehicule> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Vehicule vehicule = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(vehicule, dto);
            // a cet instant, les liens entités liées ne sont pas chargées
            updateLinks(dto, vehicule);
        } else {
            throw new SampleException("vehicle " + id + " not saved");
        }
        return id;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }

    private Vehicule updateLinks(VehiculeDTO dto, Vehicule vehicule) throws SampleException {
        // chargement du proprietaire
        if(dto.getOwner() != null && dto.getOwner().getId() > 0) {
            Personne proprietaire = personneRepository.findById(dto.getOwner().getId())
                .orElseThrow(() -> new SampleException("Person " + dto.getOwner().getId() + " not found !"));
            // Personne est MAITRE de la relation Personne>Vehicule
            proprietaire.setVehicule(vehicule);
            personneRepository.save(proprietaire);
            // utile seulement afin de garder l'entité java a jour
            vehicule.setProprietaire(proprietaire);
        }
        return repository.save(vehicule);
    }
}
