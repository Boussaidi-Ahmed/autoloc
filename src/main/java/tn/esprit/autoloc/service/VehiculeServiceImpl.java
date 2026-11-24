package tn.esprit.autoloc.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repositories.EquipementRepository;
import tn.esprit.autoloc.repositories.VehiculeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final EquipementRepository equipementRepository;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository,
                               EquipementRepository equipementRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.equipementRepository = equipementRepository;
    }

    @Override
    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        vehicule.setId(id);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteById(Long id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable : " + idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable : " + idEquipement));

        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable : " + idVehicule));

        vehicule.getEquipements().removeIf(e -> e.getId().equals(idEquipement));
        return vehiculeRepository.save(vehicule);
    }


    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void actualiserEtatVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("=== Actualisation des véhicules ===");
        System.out.println("Nombre total de véhicules : " + vehicules.size());
        System.out.println("Date et heure d'actualisation : " + LocalDateTime.now().format(FORMAT));
    }


    @Scheduled(cron = "0 0 9 * * MON")
    public void genererRapportVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        int total = vehicules.size();
        System.out.println("========== RAPPORT HEBDOMADAIRE DES VÉHICULES ==========");
        System.out.println("Nombre total de véhicules : " + total);
        System.out.println("Généré le : " + LocalDateTime.now().format(FORMAT));
        System.out.println("========================================================");
    }
}