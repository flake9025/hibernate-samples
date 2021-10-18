package fr.vvlabs.hibernate.sample.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PersonneDTO {

    private int id;
    private String firstName;
    private String lastName;
    private ZonedDateTime birthDay;
}
