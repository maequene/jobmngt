package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;

@RestController
@RequestMapping(value = "/api/application")
public class TestApplicationDaoController {

    @Autowired
    private ApplicationDao ApplicationDao;

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @RequestMapping(value = "/listall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Application> getAllApplication() {
        return ApplicationDao.findAll("id","");  // Assurez-vous que findAll() est défini dans JobOfferDao
    }

    // Create an application
    // curl -X POST localhost:8080/api/companies/joboffer -H \
    // 'Content-type:application/json' -d \
    // '{"cv": "monCV.pdf", 
    // "appdate": "2025-03-05", 
    // "candidate": {"id": 1}, 
    // "qualificationlevel": {"id": 2}, 
    // "sectors": [{"id": 1}, {"id": 3}]}'
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Application newApplication(@RequestBody Application application) {
        ApplicationDao.persist(application);
        return application;
    }

    // Get information of an applicattion by sector and qualification level
    @RequestMapping(value = "/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Application> getJobOffersBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return ApplicationDao.getApplicationsBySectorAndQualification(sectorId, qualificationLevelId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Application updateApplication(@RequestBody Application updatedApplication) {
        // Récupérer la candidature existante avec l'ID fourni
        Application existingApplication = ApplicationDao.findById(updatedApplication.getId());
        // Mettre à jour les informations de la candidature
        existingApplication.setCv(updatedApplication.getCv());
        existingApplication.setAppdate(updatedApplication.getAppdate());
        // Mettre à jour le candidat associé
        Candidate candidate = candidateDao.findById(updatedApplication.getCandidate().getId());
        existingApplication.setCandidate(candidate);
        // Mettre à jour le niveau de qualification
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(updatedApplication.getQualificationlevel().getId());
        existingApplication.setQualificationlevel(qualificationLevel);
        // Mettre à jour les secteurs associés
        existingApplication.setSectors(updatedApplication.getSectors());
        // Enregistrer les modifications dans la base de données
        ApplicationDao.merge(existingApplication);
        // Retourner la candidature mise à jour
        return existingApplication;
    }

}