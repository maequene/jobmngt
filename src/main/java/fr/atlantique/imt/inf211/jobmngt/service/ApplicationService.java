package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import java.util.Set;

import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

public interface ApplicationService {

    public List<Application> listOfApplication();

    public int addApplication(Candidate candidate, int qualificationlevelid, String cv, List<Integer> sectors);

    public Application getApplicationById(Integer id);

    public void removeApplication(Application application);

    public List<Application> findApplicationsBySectorsandQualificationLevel(Set<Sector> sectors, int qualificationlevelid);
    
    public void updateApplication(Application application, int qualificationlevelid, String cv, List<Integer> sectors);
}

