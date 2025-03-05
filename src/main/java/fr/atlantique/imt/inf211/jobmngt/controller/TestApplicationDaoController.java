package fr.atlantique.imt.inf211.jobmngt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

@RestController
@RequestMapping(value = "/api/application")
public class TestApplicationDaoController {

    @Autowired
    private ApplicationDao ApplicationDao;

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired
    private SectorDao sectorDao;

    @RequestMapping(value = "/listall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Application> getAllApplication() {
        return ApplicationDao.findAll("id","");  // Assurez-vous que findAll() est défini dans JobOfferDao
    }

    // Create an application
    //curl -X GET "http://localhost:8080/api/application/create
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Application newApplication() throws ParseException {
        // Récupérer un candidat existant
        Candidate candidate = candidateDao.findById(8);
        // Récupérer un niveau de qualification existant
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(3);
        // Récupérer un secteur existant 
        Sector sector = sectorDao.findById(3);
        Set<Sector> sectors = new HashSet<>();
        sectors.add(sector);
        // Créer une nouvelle application avec des valeurs prédéfinies
        Application newApplication = new Application();
        newApplication.setCandidate(candidate);
        newApplication.setQualificationlevel(qualificationLevel);
        newApplication.setSectors(sectors);
        newApplication.setCv("cv_par_defaut.pdf");
        newApplication.setAppdate(new SimpleDateFormat("yyyy-mm-dd").parse("2025-03-02"));
        // Sauvegarder l'application
        ApplicationDao.persist(newApplication);
        return newApplication;
    }


    // Get information of an applicattion by sector and qualification level
    @RequestMapping(value = "/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Application> getJobOffersBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return ApplicationDao.getApplicationsBySectorAndQualification(sectorId, qualificationLevelId);
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Application updateApplication(@PathVariable int id) throws ParseException {
         Application existingApplication = ApplicationDao.findById(id);
        // Récupérer un niveau de qualification existant 
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(1);
        // Mettre à jour les informations de la candidature
        existingApplication.setCv("nouveau_cv.pdf"); // Nouveau CV en dur
        existingApplication.setAppdate(new SimpleDateFormat("yyyy-mm-dd").parse("2024-08-02")); // Nouvelle date
        existingApplication.setQualificationlevel(qualificationLevel);
        // Enregistrer les modifications
        ApplicationDao.merge(existingApplication);
        return existingApplication;
    }
}