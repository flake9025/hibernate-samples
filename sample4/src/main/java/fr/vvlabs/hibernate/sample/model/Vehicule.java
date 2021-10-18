package fr.vvlabs.hibernate.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "vehicles")
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
