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
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.service.JobOfferService;
import fr.atlantique.imt.inf211.jobmngt.service.CompanyService;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyServ;

    @Autowired
    private AppUserService appuserServ;

    @Autowired
    private JobOfferService jobofferServ;

    @Autowired
    private ApplicationService applicationServ;

    //Lister toutes les entreprises existantes
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("company/companyList.html");
        List<Company> list = companyServ.listOfUsers();
        mav.addObject("companieslist", list);
        return mav;
    }

    // Affichage du form de création d'une nouvelle entreprise
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView PrintnewCompanyForm(){
        ModelAndView mav = new ModelAndView("company/companyForm.html");
        return mav;
    }
    
    // Création des données d'une nouvelle entreprise
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public String newCompanyData(@RequestParam String mail, @RequestParam String password, @RequestParam String denomination, @RequestParam String description, @RequestParam String city, RedirectAttributes redirectAttributes) {
        if (!companyServ.emailExist(mail)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cette adresse e-mail est déjà utilisée.");
            return "redirect:/companies/create"; // Redirige vers la page de création avec un message d'erreur
        } else {
            companyServ.addCompany(mail, password, denomination, description, city);
            return "redirect:/companies";
        }
    }

    // Get information of a company by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView ShowCompany(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("company/companyView.html");
        Company company = companyServ.getCompany(id);
        mav.addObject("company", company);
        return mav;
    }

    @RequestMapping(value = "/joboffers_viewcompany", method = RequestMethod.GET)
    public ModelAndView showCompanyOffers(HttpServletRequest request) {
        // Récupère la session HTTP
        HttpSession session = request.getSession();
        
        // Vérifie si l'utilisateur est connecté (s'il existe un attribut "user" dans la session)
        AppUser appUser = (AppUser) session.getAttribute("user");
        
        if (appUser != null) {
            // L'utilisateur est connecté, afficher la page des offres de l'entreprise
            ModelAndView mav = new ModelAndView("company/companyJobOfferList.html");
            Company company = companyServ.getCompany(appUser.getId());
            mav.addObject("company", company);
            // Vous pouvez ajouter des objets au modèle ici si nécessaire (ex: offres d'entreprise)
            return mav;
        } else {
            // L'utilisateur n'est pas connecté, rediriger vers la page d'accueil
            return new ModelAndView("redirect:/");
        }
    }

    //Mise en correspondance une offre d'emploi avec une application
    @RequestMapping(value = "/joboffer-candidate/{joboffer_id}", method = RequestMethod.GET)
    public ModelAndView matchJobOfferWithCandidate(@PathVariable int joboffer_id) {
        ModelAndView mav = new ModelAndView("company/companyJobOffer-Application.html");
        JobOffer JobOffer = jobofferServ.getJobOfferById(joboffer_id);
        List<Application> applis = applicationServ.findApplicationsBySectorsandQualificationLevel(JobOffer.getSectors(), JobOffer.getQualificationlevel().getId());
        mav.addObject("joboffer", JobOffer);
        mav.addObject("Applications", applis);
        return mav;
    }

    // Suppression d'une company
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void deleteCompany(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        AppUser appuser = (AppUser) session.getAttribute("user");
        appuserServ.removeAppUserCompany(appuser);
        response.sendRedirect("/logout");
    }
}