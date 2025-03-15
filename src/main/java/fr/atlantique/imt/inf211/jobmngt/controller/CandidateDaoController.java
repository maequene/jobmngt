package fr.atlantique.imt.inf211.jobmngt.controller;

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
import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/candidates")
public class CandidateDaoController {
    @Autowired
    private CandidateService candidateServ;

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


    /* 
    // Modify information about a candidate
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate replaceCandidate(@PathVariable int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            candidate.getAppuser().setMail("atlantique@imt.fr");
            candidate.getAppuser().setPassword("5678");
            candidate.setFirstname("FIP");
            candidate.setLastname("2A");

            return candidateDao.merge(candidate);
        }
        return null;
    }

    // Delete a candidate
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public void deleteCandidate(@PathVariable int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            AppUser appUser = candidate.getAppuser();
            candidateDao.remove(candidate);
            appUserDao.remove(appUser);
        }
    }*/
}