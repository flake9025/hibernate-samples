package fr.vvlabs.hibernate.sample.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@EqualsAndHashCode(of = "id")
public class Employe {

  @EmbeddedId
  private EmployeId id;

  @Column(name = "badge")
  private String badge;

  @Transient
  public Employeur getEmployeur() {
    return getId().getEmployeur();
  }

  @Transient
  public Personne getPersonne() {
    return getId().getPersonne();
  }

  @Embeddable
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class EmployeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employeur employeur;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Personne personne;
  }
}
