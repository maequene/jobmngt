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
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;

import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/application")
public class TestApplicationDaoController {

    @Autowired
    private JobOfferDao JobOfferDao;

    @Autowired
    private ApplicationService applicationServ;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired 
    private SectorDao sectorDao;

    // Lister toutes les candidatures existantes
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("application/applicationList.html");
        List<Application> list = applicationServ.listOfApplication();
        mav.addObject("applicationlist", list);
        return mav;
    }

    // Affichage du form de création d'une nouvelle candidature
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView PrintnewApplicationForm(){
        ModelAndView mav = new ModelAndView("application/applicationForm.html");
        List<QualificationLevel> qualificationLevels = qualificationLevelDao.findAll("id", "ASC");
        List<Sector> sectors = sectorDao.findAll("id", "ASC");
        mav.addObject("qualificationLevels", qualificationLevels);
        mav.addObject("sectors", sectors);
        return mav;
    }

    // Création des données d'une nouvelle candidature
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public void newApplicationData(@RequestParam int qualificationlevelid, @RequestParam String cv, @RequestParam List<Integer> sectors, HttpServletRequest request, HttpServletResponse response) throws IOException{
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        if (appUser != null) {
            Candidate candidate = candidateDao.findById(appUser.getId());
            applicationServ.addApplication(candidate, qualificationlevelid, cv, sectors);
            response.sendRedirect("/candidates/application_viewcandidate"); 
        } else {
            response.sendRedirect("/login");
        }
    }

    //Affichage d'une candidature par son ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getApplicationById(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("application/applicationView.html");
        Application application = applicationDao.findById(id);
        mav.addObject("application", application);
        return mav;
    }

    @RequestMapping(value = "/application/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET)
    public List<Application> getApplicationBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return applicationDao.getApplicationsBySectorAndQualification(sectorId, qualificationLevelId);
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