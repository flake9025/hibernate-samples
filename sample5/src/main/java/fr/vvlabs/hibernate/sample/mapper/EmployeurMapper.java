package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.EmployeurDTO;
import fr.vvlabs.hibernate.sample.model.Employeur;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeurMapper {

    @Mapping(target = "name", source = "nom")
    EmployeurDTO mapToDto(Employeur model);

    @InheritInverseConfiguration
    Employeur mapToModel(EmployeurDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Employeur model, EmployeurDTO dto);
}
