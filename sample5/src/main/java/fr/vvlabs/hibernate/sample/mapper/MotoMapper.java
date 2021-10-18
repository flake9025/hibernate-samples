package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.dto.MotoDTO;
import fr.vvlabs.hibernate.sample.dto.VoitureDTO;
import fr.vvlabs.hibernate.sample.model.Moto;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import fr.vvlabs.hibernate.sample.model.Voiture;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MotoMapper {

    @Mapping(target = "model", source = "modele")
    @Mapping(target = "owner", source = "proprietaire")
    MotoDTO mapToDto(Moto model);

    @InheritInverseConfiguration
    Moto mapToModel(MotoDTO dto);

    @InheritConfiguration
    void updateModel(@MappingTarget Moto model, MotoDTO dto);
}
