package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;

@RestController
@RequestMapping(value = "/api/candidates")
public class TestCandidateDaoController {
    
    @Autowired
    private CandidateDao candidateDao;

    //Lister tous les candidats existants 
    @GetMapping
    public List<Candidate> all() {
        List<Candidate> list = candidateDao.findAll("lastname", "ASC");
        return list;
    }

    //Créer un nouveau candidat 
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate newCandidate() {
        AppUser appUser = new AppUser();
        appUser.setMail("mnc@imt.fr");
        appUser.setPassword("2580");
        appUser.setCity("Brest");

        Candidate aNewCandidate = new Candidate();
        aNewCandidate.setAppuser(appUser);
        aNewCandidate.setLastname("myLastName");
        aNewCandidate.setFirstname("myFirstName");
        
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
            candidate.getAppuser().setMail("atlantique@imt.fr");
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