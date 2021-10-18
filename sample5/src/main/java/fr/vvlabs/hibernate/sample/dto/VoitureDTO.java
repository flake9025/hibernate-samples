package fr.vvlabs.hibernate.sample.dto;

import lombok.Data;

@Data
public class VoitureDTO {

    private int id;
    private String model;
    private SimpleIdNameDTO owner;
}
