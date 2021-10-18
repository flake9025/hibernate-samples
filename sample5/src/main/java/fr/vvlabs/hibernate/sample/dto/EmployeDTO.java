package fr.vvlabs.hibernate.sample.dto;

import lombok.Data;

@Data
public class EmployeDTO {

    private SimpleIdNameDTO employer;
    private SimpleIdNameDTO person;
    private String badge;
}
