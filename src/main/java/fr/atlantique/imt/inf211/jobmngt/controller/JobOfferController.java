package fr.atlantique.imt.inf211.jobmngt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
public class JobOfferController {

    @Autowired
    private JobOfferService JobOfferServ;

    @Autowired
    private JobOfferService jobOfferServ;

    @Autowired
    private CompanyService companyServ;

    @Autowired
    private QualificationLevelService qualificationLevelServ;

    @Autowired
    private SectorService sectorServ;

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
            int id = jobOfferServ.addJobOffer(company, qualificationlevelid, title, taskdescription, sectors);
            response.sendRedirect("/joboffer/" + id); 
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

    // Suppression d'une offre de job par son ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void deleteJobOffer(@PathVariable int id, HttpServletResponse response) throws IOException {
        JobOffer joboffer = jobOfferServ.getJobOfferById(id);
        jobOfferServ.removeJoboffer(joboffer);
        response.sendRedirect("/companies/joboffers_viewcompany");
    }
 }