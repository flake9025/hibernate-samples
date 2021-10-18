package fr.vvlabs.hibernate.sample.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "persons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Personne implements BaseNameEntity {

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

    @Override
    public String getName() {
        return String.format("%s %s", prenom, nom);
    }

    @PrePersist
    @PreUpdate
    public void formatNames(){
        prenom = StringUtils.capitalize(prenom);
        nom = StringUtils.capitalize(nom);
    }
}
