package fr.atlantique.imt.inf211.jobmngt.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;

import fr.atlantique.imt.inf211.jobmngt.service.JobOfferService;
import fr.atlantique.imt.inf211.jobmngt.service.QualificationLevelService;
import fr.atlantique.imt.inf211.jobmngt.service.SectorService;
import fr.atlantique.imt.inf211.jobmngt.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/joboffer")
public class TestJobOfferDaoController {

    @Autowired
    private JobOfferService JobOfferServ;

    @Autowired
    private JobOfferService jobOfferServ;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyService companyServ;

    @Autowired
    private QualificationLevelService qualificationLevelServ;

    @Autowired
    private SectorService sectorServ;

    @Autowired 
    private SectorDao sectorDao;

    // Lister toutes les offres d'emploi existantes
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("joboffer/jobofferList.html");
        List<JobOffer> list = jobOfferServ.listOfJobOffer();
        mav.addObject("jobofferlist", list);
        return mav;
    }

    // Affichage du form de création d'une nouvelle offre
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView PrintnewJobOfferForm(){
        ModelAndView mav = new ModelAndView("joboffer/jobofferForm.html");
        List<QualificationLevel> qualificationLevels = qualificationLevelServ.listOfQualificationLevels();
        List<Sector> sectors = sectorServ.listOfSectors();
        mav.addObject("qualificationLevels", qualificationLevels);
        mav.addObject("sectors", sectors);
        return mav;
    }

    // Création des données d'une nouvelle offre
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public void newJobOfferData(@RequestParam int qualificationlevelid, @RequestParam String title, @RequestParam String taskdescription, @RequestParam List<Integer> sectors, HttpServletRequest request, HttpServletResponse response) throws IOException{
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        if (appUser != null) {
            Company company = companyServ.getCompany(appUser.getId());
            jobOfferServ.addJobOffer(company, qualificationlevelid, title, taskdescription, sectors);
            response.sendRedirect("/joboffer"); 
        } else {
            response.sendRedirect("/login");
        }
    }

    //Affichage d'une offre par son ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getJobOfferById(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("joboffer/jobofferView.html");
        JobOffer jobOffer = JobOfferServ.getJobOfferById(id);
        mav.addObject("joboffer", jobOffer);
        return mav;
    }
    /*
    @RequestMapping(value = "/joboffers/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET)
    public List<JobOffer> getJobOffersBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return JobOfferDao.findBySectorAndQualification(sectorId, qualificationLevelId);
    }

    /* 
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
    }*/
 }