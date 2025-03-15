package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import jakarta.transaction.Transactional;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class ApplicationServiceImp implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @Autowired
    private SectorDao sectorDao;

    @Override
    public List<Application> listOfApplication() {
        return applicationDao.findAll("id", "ASC");
    }

    @Transactional
    public void addApplication(Candidate candidate, int qualificationlevelid, String cv, List<Integer> sectors){
        Application application = new Application();
        application.setCandidate(candidate);
        application.setQualificationlevel(qualificationLevelDao.findById(qualificationlevelid));
        application.setCv(cv);
        Set<Sector> sectorSet = new HashSet<>();
        for (Integer sectorid : sectors) {
            sectorSet.add(sectorDao.findById(sectorid));
        }
        application.setSectors(sectorSet);
        application.setAppdate(new java.util.Date());
        applicationDao.persist(application);
    }

    @Transactional
    public Application getApplicationById(Integer id) {
        return applicationDao.findById(id);
    }


}