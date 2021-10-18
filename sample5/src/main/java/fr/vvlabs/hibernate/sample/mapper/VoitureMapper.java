package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.VoitureDTO;
import fr.vvlabs.hibernate.sample.model.Voiture;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoitureMapper {

    @Mapping(target = "model", source = "modele")
    @Mapping(target = "owner", source = "proprietaire")
    VoitureDTO mapToDto(Voiture model);

    @InheritInverseConfiguration
    Voiture mapToModel(VoitureDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Voiture model, VoitureDTO dto);
}
