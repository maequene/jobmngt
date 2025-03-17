package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import java.util.Set;

import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

public interface JobOfferService {

    public List<JobOffer> listOfJobOffer();

    public int addJobOffer(Company company, int qualificationlevelid, String title, String taskdescription, List<Integer> sectors);

    public JobOffer getJobOfferById(Integer id);

    public void removeJoboffer(JobOffer joboffer);

    public List<JobOffer> findJobOffersBySectorsAndQualification(Set<Sector> sectors, int qualificationlevelid);
    
}

