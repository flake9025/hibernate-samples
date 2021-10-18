package fr.vvlabs.hibernate.sample.model;

import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "associations")
@Data
@EqualsAndHashCode(of = "id")
public class Association implements BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String nom;

    @ManyToMany(mappedBy = "associations")
    private Set<Personne> membres;

    @Override
    public String getName() {
        return nom;
    }
}
