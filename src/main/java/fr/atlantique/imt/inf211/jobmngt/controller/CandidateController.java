package fr.atlantique.imt.inf211.jobmngt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;
import fr.atlantique.imt.inf211.jobmngt.service.AppUserService;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.JobOfferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateServ;

    @Autowired
    private AppUserService appuserServ;

    @Autowired
    private ApplicationService applicationServ;

    @Autowired
    private JobOfferService jobofferServ;

    //Lister tous les candidats existants
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("candidate/candidateList.html");
        List<Candidate> list = candidateServ.listOfCandidate();
        mav.addObject("candidateslist", list);
        return mav;
    }

    // Affichage du form de création d'un nouveau candidat
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView PrintnewCandidateForm(){
        ModelAndView mav = new ModelAndView("candidate/candidateForm.html");
        return mav;
    }
    
    // Création des données d'un nouveau candidat
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public String newCandidateData(@RequestParam String mail, @RequestParam String password, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String city, RedirectAttributes redirectAttributes) {
        if (!candidateServ.emailExist(mail)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cette adresse e-mail est déjà utilisée.");
            return "redirect:/companies/create"; // Redirige vers la page de création avec un message d'erreur
        } else {
            candidateServ.addCandidate(mail, password, firstname, lastname, city);
            return "redirect:/candidates";
        }
    }

    // Get information of a candidate by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView ShowCandidate(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("candidate/candidateView.html");
        Candidate candidate = candidateServ.getCandidate(id);
        mav.addObject("candidate", candidate);
        return mav;
    }

    @RequestMapping(value = "/application_viewcandidate", method = RequestMethod.GET)
    public ModelAndView showCandidateApplication(HttpServletRequest request) {
        // Récupère la session HTTP
        HttpSession session = request.getSession();
        
        // Vérifie si l'utilisateur est connecté (s'il existe un attribut "user" dans la session)
        AppUser appUser = (AppUser) session.getAttribute("user");
        
        if (appUser != null) {
            // L'utilisateur est connecté, afficher la page des offres de l'entreprise
            ModelAndView mav = new ModelAndView("candidate/candidateApplicationList.html");
            Candidate candidate = candidateServ.getCandidate(appUser.getId());
            mav.addObject("candidate", candidate);
            // Vous pouvez ajouter des objets au modèle ici si nécessaire (ex: offres d'entreprise)
            return mav;
        } else {
            // L'utilisateur n'est pas connecté, rediriger vers la page d'accueil
            return new ModelAndView("redirect:/");
        }
    }

    //Mise en correspondance une offre d'emploi avec une application
    @RequestMapping(value = "/application-joboffer/{application_id}", method = RequestMethod.GET)
    public ModelAndView matchApplicationWithJobOffer(@PathVariable int application_id) {
        ModelAndView mav = new ModelAndView("candidate/candidateApplication-JobOffer.html");
        Application application = applicationServ.getApplicationById(application_id);
        List<JobOffer> joboffers = jobofferServ.findJobOffersBySectorsAndQualification(application.getSectors(), application.getQualificationlevel().getId());
        mav.addObject("appli", application);
        mav.addObject("JobOffers", joboffers);
        return mav;
    }

    // Suppression d'une company
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void deleteCandidate(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        AppUser appuser = (AppUser) session.getAttribute("user");
        appuserServ.removeAppUserCandidate(appuser);
        response.sendRedirect("/logout");
    }
}