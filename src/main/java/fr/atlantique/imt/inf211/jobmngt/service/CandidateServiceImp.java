package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import jakarta.transaction.Transactional;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class CandidateServiceImp implements CandidateService {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private AppUserDao AppUserDao;

    @Override
    public List<Candidate> listOfCandidate() {
        return candidateDao.findAll("appuser.mail", "ASC");
    }

    @Transactional
    public void addCandidate(String mail, String password, String firstname, String lastname, String city) {
        AppUser appUser = new AppUser();
        appUser.setMail(mail);
        appUser.setPassword(password);
        appUser.setCity(city);
        AppUserDao.persist(appUser);

        Candidate aNewCandidate = new Candidate();
        aNewCandidate.setId(appUser.getId()); // Associer explicitement l'ID
        aNewCandidate.setAppuser(appUser);
        aNewCandidate.setLastname(lastname);
        aNewCandidate.setFirstname(firstname);
        appUser.setCandidate(aNewCandidate);
        
        candidateDao.persist(aNewCandidate);
    }

    @Transactional
    public Candidate getCandidate(Integer id) {
        return candidateDao.findById(id);
    }


}