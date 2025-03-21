package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;

public interface CandidateService {

    public List<Candidate> listOfCandidate();

    public void addCandidate(String mail, String password, String firstname, String lastname, String city);

    public Candidate getCandidate(Integer id);

    public boolean emailExist(String mail);

    public void updateCandidate(Candidate candidate, String firstname, String lastname, String city);
    
}

