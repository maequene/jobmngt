package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import java.util.List;
import java.util.Optional;
import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface CandidateService {

    public List<Candidate> listOfCandidate();

    public void addCandidate(String mail, String password, String firstname, String lastname, String city);

    public Candidate getCandidate(Integer id);
    
}

