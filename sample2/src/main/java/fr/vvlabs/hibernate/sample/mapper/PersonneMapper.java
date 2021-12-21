package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.model.Personne;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = VehiculeMapper.class)
public interface PersonneMapper {

    @Mapping(target = "firstName", source = "prenom")
    @Mapping(target = "lastName", source = "nom")
    @Mapping(target = "birthDay", source = "dateNaissance")
    PersonneDTO mapToDto(Personne model);

    @InheritInverseConfiguration
    Personne mapToModel(PersonneDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Personne model, PersonneDTO dto);
}
