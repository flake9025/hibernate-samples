package fr.vvlabs.hibernate.sample.service;

import fr.vvlabs.hibernate.sample.dto.AssociationDTO;
import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.mapper.AssociationMapper;
import fr.vvlabs.hibernate.sample.mapper.AssociationMapper;
import fr.vvlabs.hibernate.sample.model.Association;
import fr.vvlabs.hibernate.sample.repository.AssociationRepository;
import fr.vvlabs.hibernate.sample.repository.AssociationRepository;
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
public class AssociationService {

    private final AssociationRepository repository;
    private final AssociationMapper mapper;

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

    @Transactional
    public Integer create(final AssociationDTO dto) {
        return repository.save(mapper.mapToModel(dto)).getId();
    }

    @Transactional
    public Integer update(final Integer id, final AssociationDTO dto) throws SampleException {
        Integer key = null;
        Optional<Association> optionalModel = repository.findById(id);
        if (optionalModel.isPresent()) {
            Association model = optionalModel.get();
            // avoid NPE or different id bug !
            dto.setId(id);
            // update model
            mapper.updateModel(model, dto);
            key = repository.save(model).getId();
        } else {
            throw new SampleException("association " + id + " not saved");
        }
        return key;
    }

    @Transactional
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }
}
