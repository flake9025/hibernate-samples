package fr.vvlabs.hibernate.sample.dto;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class PersonneDTO {

    private int id;
    private String firstName;
    private String lastName;
    private ZonedDateTime birthDay;
    private VehiculeDTO vehicule;
}
