package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;

public interface ApplicationService {

    public List<Application> listOfApplication();

    public void addApplication(Candidate candidate, int qualificationlevelid, String cv, List<Integer> sectors);

    public Application getApplicationById(Integer id);
    
}

