package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.model.Personne;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonneMapper {

    @Mapping(target = "firstName", source = "prenom")
    @Mapping(target = "lastName", source = "nom")
    @Mapping(target = "birthDay", source = "dateNaissance")
    @Mapping(target = "vehicle", source = "vehicule")
    @Mapping(target = "children", source = "enfants")
    PersonneDTO mapToDto(Personne model);

    @InheritInverseConfiguration
    Personne mapToModel(PersonneDTO dto);

    @InheritConfiguration
    @Mapping(target = "vehicle", ignore = true)
    void updateModel(@MappingTarget Personne model, PersonneDTO dto);
}
