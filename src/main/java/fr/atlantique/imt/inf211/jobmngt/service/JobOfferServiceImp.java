package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import jakarta.transaction.Transactional;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class JobOfferServiceImp implements JobOfferService {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private AppUserDao AppUserDao;

    @Autowired
    private JobOfferDao jobofferDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired
    private SectorDao sectorDao;

    @Override
    public List<JobOffer> listOfJobOffer() {
        return jobofferDao.findAll("id", "ASC");
    }

    @Transactional
    public void addJobOffer(Company company, int qualificationlevelid, String title, String taskdescription, List<Integer> sectors){
        JobOffer joboffer = new JobOffer();
        joboffer.setCompany(company);
        joboffer.setQualificationlevel(qualificationLevelDao.findById(qualificationlevelid));
        joboffer.setTitle(title);
        joboffer.setTaskdescription(taskdescription);
        Set<Sector> sectorSet = new HashSet<>();
        for (Integer sectorid : sectors) {
            sectorSet.add(sectorDao.findById(sectorid));
        }
        joboffer.setSectors(sectorSet);
        joboffer.setPublicationdate(new java.util.Date());
        jobofferDao.persist(joboffer);
    }

    @Transactional
    public JobOffer getJobOfferById(Integer id) {
        return jobofferDao.findById(id);
    }


}