package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;

@RestController
@RequestMapping(value = "/api/joboffer")
public class TestJobOfferDaoController {

    @Autowired
    private JobOfferDao JobOfferDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @RequestMapping(value = "/listall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobOffer> getAllJobOffers() {
        return JobOfferDao.findAll("title",null);  // Assurez-vous que findAll() est défini dans JobOfferDao
    }

    // Create a job offer
    // curl -X POST localhost:8080/api/companies/joboffer -H \
    // 'Content-type:application/json' -d \
    // '{"title": "Développeur Java", "taskdescription": "Développer des applications Java dans un environnement agile.", 
    // "publicationdate": "2025-03-05", 
    // "company": {"id": 1}, 
    // "qualificationlevel": {"id": 2}, 
    // "sectors": [{"id": 1}, {"id": 3}]}'
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JobOffer newJobOffer(@RequestBody JobOffer joboffer) {
        JobOfferDao.persist(joboffer);
        return joboffer;
    }

    @RequestMapping(value = "/joboffers/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobOffer> getJobOffersBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return JobOfferDao.findBySectorAndQualification(sectorId, qualificationLevelId);
    }

    @RequestMapping(value = "/joboffers/update", method = RequestMethod.POST)
    public JobOffer updateJobOffer(@RequestBody JobOffer updatedJobOffer) {
        // Récupérer l'offre d'emploi existante avec l'ID de l'offre d'emploi
        JobOffer existingJobOffer = JobOfferDao.findById(updatedJobOffer.getId());
        // Mettre à jour les informations de l'offre d'emploi existante
        existingJobOffer.setTitle(updatedJobOffer.getTitle());
        existingJobOffer.setTaskdescription(updatedJobOffer.getTaskdescription());
        existingJobOffer.setPublicationdate(updatedJobOffer.getPublicationdate());
        // Mettre à jour l'entreprise associée
        Company company = companyDao.findById(updatedJobOffer.getCompany().getId());
        existingJobOffer.setCompany(company);
        // Mettre à jour le niveau de qualification
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(updatedJobOffer.getQualificationlevel().getId());
        existingJobOffer.setQualificationlevel(qualificationLevel);
        // Mettre à jour les secteurs associés
        Set<Sector> sectors = existingJobOffer.getSectors();
        existingJobOffer.setSectors(sectors);
        // Enregistrer les modifications dans la base de données
        JobOfferDao.merge(existingJobOffer);
        // Retourner l'offre d'emploi mise à jour
        return existingJobOffer;
    }
}