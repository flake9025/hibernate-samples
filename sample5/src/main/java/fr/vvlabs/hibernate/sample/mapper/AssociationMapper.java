package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.AssociationDTO;
import fr.vvlabs.hibernate.sample.dto.EnfantDTO;
import fr.vvlabs.hibernate.sample.model.Association;
import fr.vvlabs.hibernate.sample.model.Enfant;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssociationMapper {

    @Mapping(target = "name", source = "nom")
    AssociationDTO mapToDto(Association model);

    @InheritInverseConfiguration
    Association mapToModel(AssociationDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Association model, AssociationDTO dto);
}
