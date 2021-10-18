package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.EnfantDTO;
import fr.vvlabs.hibernate.sample.dto.PersonneDTO;
import fr.vvlabs.hibernate.sample.model.Enfant;
import fr.vvlabs.hibernate.sample.model.Personne;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnfantMapper {

    @Mapping(target = "firstName", source = "prenom")
    @Mapping(target = "father", source = "pere")
    @Mapping(target = "mother", source = "mere")
    EnfantDTO mapToDto(Enfant model);

    @InheritInverseConfiguration
    Enfant mapToModel(EnfantDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Enfant model, EnfantDTO dto);
}
