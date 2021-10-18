package fr.vvlabs.hibernate.sample.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("MOTORBIKE")
@Data
@EqualsAndHashCode(of = "id")
public class Moto extends Vehicule {
}
