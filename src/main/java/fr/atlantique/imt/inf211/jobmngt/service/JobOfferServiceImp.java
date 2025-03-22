package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import jakarta.transaction.Transactional;


@Component
public class JobOfferServiceImp implements JobOfferService {

    @Autowired
    private JobOfferDao jobofferDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired
    private SectorDao sectorDao;

    @Override
    public List<JobOffer> listOfJobOffer() {
        return jobofferDao.findAll("id", "ASC");
    }

    @Override
    @Transactional
    public int addJobOffer(Company company, int qualificationlevelid, String title, String taskdescription, List<Integer> sectors){
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
        return joboffer.getId();
    }

    @Override
    @Transactional
    public JobOffer getJobOfferById(Integer id) {
        return jobofferDao.findById(id);
    }

    @Override
    @Transactional
    public void removeJoboffer(JobOffer joboffer) {
        jobofferDao.remove(joboffer);
    }

    @Override
    @Transactional
    public List<JobOffer> findJobOffersBySectorsAndQualification (Set<Sector> sectors, int qualificationlevelid) {
        List<JobOffer> joboffers = new ArrayList<>();
        for (Sector sector : sectors) {
            List<JobOffer> sectorJoboffers = jobofferDao.findBySectorAndQualification(sector.getId(), qualificationlevelid);
            for (JobOffer joboffer : sectorJoboffers) {
                joboffers.add(joboffer);
            }
        }
        return joboffers;
    }

    @Override
    @Transactional 
    public void updateJobOffer(JobOffer joboffer, int qualificationlevelid, String title, String taskdescription, List<Integer> sectors){
        joboffer.setQualificationlevel(qualificationLevelDao.findById(qualificationlevelid));
        joboffer.setTitle(title);
        joboffer.setTaskdescription(taskdescription);
        Set<Sector> sectorSet = new HashSet<>();
        for (Integer sectorid : sectors) {
            sectorSet.add(sectorDao.findById(sectorid));
        }
        joboffer.setSectors(sectorSet);
    }
}