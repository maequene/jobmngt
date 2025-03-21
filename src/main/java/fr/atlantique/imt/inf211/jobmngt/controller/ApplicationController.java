package fr.atlantique.imt.inf211.jobmngt.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;
import fr.atlantique.imt.inf211.jobmngt.service.QualificationLevelService;
import fr.atlantique.imt.inf211.jobmngt.service.SectorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/application")
public class ApplicationController {

    private static final Logger logger = Logger.getLogger(ApplicationController.class.getName());

    @Autowired
    private ApplicationService applicationServ;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private SectorService sectorServ;

    @Autowired
    private QualificationLevelService qualificationLevelServ;

    @Autowired
    private CandidateService candidateService;

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
        List<QualificationLevel> qualificationLevels = qualificationLevelServ.listOfQualificationLevels();
        List<Sector> sectors = sectorServ.listOfSectors();
        mav.addObject("qualificationLevels", qualificationLevels);
        mav.addObject("sectors", sectors);
        return mav;
    }

    // Création des données d'une nouvelle candidature
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public String newApplicationData(@RequestParam int qualificationlevelid, @RequestParam String cv, @RequestParam List<Integer> sectors, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException{
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        if (appUser != null) {
            if (sectors.size() == 1 && sectors.contains(0)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Sélectionner au moins un secteur");
                return "redirect:/application/create";
            } else {
                sectors.removeIf(s -> s == 0);
                Candidate candidate = candidateService.getCandidate(appUser.getId());
                int id = applicationServ.addApplication(candidate, qualificationlevelid, cv, sectors);
                return "redirect:/application/" + id;
            }
        } else {
            return "redirect:/login";
        }
    }

    //Affichage d'une candidature par son ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getApplicationById(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("application/applicationView.html");
        Application appli = applicationServ.getApplicationById(id);
        logger.log(Level.INFO, "Recuperation de la candidature avec le CV: {0}", appli.getCv());
        mav.addObject("applic", appli);
        return mav;
    }

    @RequestMapping(value = "/application/sector/{sectorId}/qualification/{qualificationLevelId}", method = RequestMethod.GET)
    public List<Application> getApplicationBySectorAndQualification(@PathVariable("sectorId") int sectorId, @PathVariable("qualificationLevelId") int qualificationLevelId) {
        return applicationDao.getApplicationsBySectorAndQualification(sectorId, qualificationLevelId);
    }

    // Suppression d'une candidature par son ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void deleteApplication(@PathVariable int id, HttpServletResponse response) throws IOException {
        Application application = applicationServ.getApplicationById(id);
        applicationServ.removeApplication(application);
        response.sendRedirect("/candidates/application_viewcandidate");
    }

    // Afficher le formulaire avec les valeurs existantes
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView showUpdateForm(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("candidate/candidateApplication-Update.html");
        Application application = applicationServ.getApplicationById(id);
        mav.addObject("applic", application);
        List<QualificationLevel> qualificationLevels = qualificationLevelServ.listOfQualificationLevels();
        List<Sector> sectors = sectorServ.listOfSectors();
        mav.addObject("qualificationLevels", qualificationLevels);
        mav.addObject("sectors", sectors);
        return mav;
    }

    // Mise à jour des données d'une candidature
    @RequestMapping(value = "/updateData/{id}", method = RequestMethod.GET) 
    public String updateApplication(@PathVariable int id, @RequestParam int qualificationlevelid, @RequestParam String cv, @RequestParam List<Integer> sectors, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException{
        Application existingApplication = applicationServ.getApplicationById(id);
        if (sectors.size() == 1 && sectors.contains(0)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sélectionner au moins un secteur");
            return "redirect:/application/update/" + id;
        } else {
            sectors.removeIf(s -> s == 0);
            applicationServ.updateApplication(existingApplication, qualificationlevelid, cv, sectors);
            return "redirect:/candidates/application_viewcandidate";
        }
    }
}
