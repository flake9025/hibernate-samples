package fr.vvlabs.hibernate.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "children")
@Data
@EqualsAndHashCode(of = "id")
public class Enfant implements BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname")
    private String prenom;

    @ManyToOne
    @JoinColumn(name ="father_id")
    private Personne pere;

    @ManyToOne
    @JoinColumn(name= "mother_id")
    private Personne mere;

    @Override
    public String getName() {
        return prenom;
    }

    @PrePersist
    @PreUpdate
    public void formatNames(){
        prenom = StringUtils.capitalize(prenom);
    }
}
