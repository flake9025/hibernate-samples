package fr.vvlabs.hibernate.sample.model;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "persons")
@Data
@EqualsAndHashCode(of = "id")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    private String prenom;
    @Column(name = "lastname")
    private String nom;
    @Column(name = "birthday")
    private ZonedDateTime dateNaissance;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicule vehicule;

    @OneToMany(mappedBy = "pere")
    private Set<Enfant> enfants;

    @ManyToMany
    @JoinTable(name= "associations_persons",
        joinColumns = {@JoinColumn(name = "person_id")},
        inverseJoinColumns = {@JoinColumn(name= "association_id")})
    private Set<Association> associations;

}
