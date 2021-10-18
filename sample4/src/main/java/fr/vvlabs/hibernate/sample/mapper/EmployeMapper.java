package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.EmployeDTO;
import fr.vvlabs.hibernate.sample.model.Employe;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(target = "employer", source = "id.employeur")
    @Mapping(target = "person", source = "id.personne")
    EmployeDTO mapToDto(Employe model);

    @InheritInverseConfiguration
    Employe mapToModel(EmployeDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Employe model, EmployeDTO dto);
}
