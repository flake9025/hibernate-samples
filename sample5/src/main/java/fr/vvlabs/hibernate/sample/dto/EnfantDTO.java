package fr.vvlabs.hibernate.sample.dto;

import lombok.Data;

@Data
public class EnfantDTO {

    private int id;
    private String firstName;
    private SimpleIdNameDTO father;
    private SimpleIdNameDTO mother;
}
