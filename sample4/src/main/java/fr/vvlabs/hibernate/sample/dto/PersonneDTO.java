package fr.vvlabs.hibernate.sample.dto;

import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class PersonneDTO {

    private int id;
    private String firstName;
    private String lastName;
    private ZonedDateTime birthDay;
    private SimpleIdNameDTO vehicle;
    private Set<SimpleIdNameDTO> children;
    private Set<SimpleIdNameDTO> associations;
}
