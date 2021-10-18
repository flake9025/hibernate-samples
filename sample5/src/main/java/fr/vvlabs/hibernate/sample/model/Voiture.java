package fr.vvlabs.hibernate.sample.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CAR")
@Data
@EqualsAndHashCode(of = "id")
public class Voiture extends Vehicule {
}
