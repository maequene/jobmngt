package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;

public interface JobOfferService {

    public List<JobOffer> listOfJobOffer();

    public void addJobOffer(Company company, int qualificationlevelid, String title, String taskdescription, List<Integer> sectors);

    public JobOffer getJobOfferById(Integer id);
    
}

