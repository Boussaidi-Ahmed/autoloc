package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEquipement;

    String libelle;

    @ManyToMany(mappedBy = "equipements")
    @JsonIgnoreProperties("equipements")
    private List<Vehicule> vehicules = new ArrayList<>();

    public Long getId() {
        return idEquipement;
    }

    public void setId(Long id) {
        this.idEquipement = id;
    }
}