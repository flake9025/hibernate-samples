package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.PersonnePostDTO;
import fr.vvlabs.hibernate.sample.dto.VehiculeDTO;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import jdk.jfr.Name;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "model", source = "modele")
    VehiculeDTO mapToDto(Vehicule model);

    @InheritInverseConfiguration
    Vehicule mapToModel(VehiculeDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Vehicule model, VehiculeDTO dto);
}
