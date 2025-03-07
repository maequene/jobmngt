package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;

@RestController
@RequestMapping(value = "/api/candidates")
public class TestCandidateDaoController {
    
    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private AppUserDao appUserDao;

    //Lister tous les candidats existants 
    @GetMapping
    public List<Candidate> all() {
        List<Candidate> list = candidateDao.findAll("lastname", "ASC");
        return list;
    }

    //Créer un nouveau candidat 
    //curl -X GET "http://localhost:8080/api/candidates/create
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate newCandidate() {
        AppUser appUser = new AppUser();
        appUser.setMail("mae@imt.fr");
        appUser.setPassword("564");
        appUser.setCity("Rennes");
        appUserDao.persist(appUser);

        Candidate aNewCandidate = new Candidate();
        aNewCandidate.setId(appUser.getId()); // Associer explicitement l'ID
        aNewCandidate.setAppuser(appUser);
        aNewCandidate.setLastname("Mae");
        aNewCandidate.setFirstname("Quen");
        
        candidateDao.persist(aNewCandidate);
        return aNewCandidate;
    }

    // Get information of a candidate by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate one(@PathVariable int id) {
        return candidateDao.findById(id);
    }

    // Modify information about a candidate
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate replaceCandidate(@PathVariable int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            candidate.getAppuser().setMail("atlantique4576857@imt.fr");
            candidate.getAppuser().setPassword("5678");
            candidate.getAppuser().setCity("Brest");
            candidate.setLastname("Picaud");
            candidate.setFirstname("Clement");

            return candidateDao.merge(candidate);
        }
        return null;
    }

    // Delete a candidate by id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCandidate(@PathVariable int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            candidateDao.remove(candidate);
        }
    }
}