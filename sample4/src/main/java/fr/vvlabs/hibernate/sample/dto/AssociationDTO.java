package fr.vvlabs.hibernate.sample.dto;

import java.util.Set;
import lombok.Data;

@Data
public class AssociationDTO {

    private int id;
    private String name;
    private Set<SimpleIdNameDTO> members;
}
