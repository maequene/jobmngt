package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

@RestController
@RequestMapping(value = "/api/joboffer")
public class TestJobOfferDaoController {

    @Autowired
    private JobOfferDao JobOfferDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired 
    private SectorDao sectorDao;

    // Lister toutes les offres d'emploi existantes
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobOffer> getAllJobOffers() {
        return JobOfferDao.findAll("title","ASC");  // Assurez-vous que findAll() est défini dans JobOfferDao
    }

    // Create a job offer
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JobOffer newJobOffer() {
        Company company = companyDao.findById(7);
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(3);
        Sector sector = sectorDao.findById(19);
        Set<Sector> sectors = new HashSet<>();
        sectors.add(sector);
        
        JobOffer joboffer = new JobOffer();
        joboffer.setTitle("CDI fullstack dev");
        joboffer.setTaskdescription("Développeur view.js++");
        joboffer.setPublicationdate(new Date());
        joboffer.setCompany(company);
        joboffer.setQualificationlevel(qualificationLevel);
        joboffer.setSectors(sectors);
        JobOfferDao.persist(joboffer);
        return joboffer;
    }

    @RequestMapping(value = "/joboffers/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobOffer> getJobOffersBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return JobOfferDao.findBySectorAndQualification(sectorId, qualificationLevelId);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JobOffer updateJobOffer(@PathVariable int id) {
        // Récupérer l'offre d'emploi existante avec l'ID de l'offre d'emploi
        JobOffer existingJobOffer = JobOfferDao.findById(id);
        if (existingJobOffer != null) {
            existingJobOffer.setTitle("Développeur Java Fullstack");
            existingJobOffer.setTaskdescription("Développement Java Fullstack en agile");
            existingJobOffer.setPublicationdate(new Date());
            JobOfferDao.merge(existingJobOffer);
        }
        return existingJobOffer;
    }
 }