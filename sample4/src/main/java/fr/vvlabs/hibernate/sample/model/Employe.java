package fr.vvlabs.hibernate.sample.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

  @ManyToOne
  @MapsId("employeurId")
  @JoinColumn(name = "employer_id")
  private Employeur employeur;

  @ManyToOne
  @MapsId("personneId")
  @JoinColumn(name = "person_id")
  private Personne personne;

  @Embeddable
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class EmployeId implements Serializable {

    @Column(name = "employer_id")
    private int employeurId;

    @Column(name = "person_id")
    private int personneId;
  }
}
