package fr.vvlabs.hibernate.sample.model;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="vehicle_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("VEHICLE")
@Data
@EqualsAndHashCode(of = "id")
public class Vehicule implements BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "model")
    private String modele;
    @OneToOne(mappedBy = "vehicule")
    private Personne proprietaire;

    @Override
    public String getName() {
        return modele;
    }
}
