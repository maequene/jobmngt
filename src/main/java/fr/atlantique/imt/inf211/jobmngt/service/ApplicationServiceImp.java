package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import jakarta.transaction.Transactional;


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
    public int addApplication(Candidate candidate, int qualificationlevelid, String cv, List<Integer> sectors){
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
        return application.getId();
    }

    @Transactional
    public Application getApplicationById(Integer id) {
        return applicationDao.findById(id);
    }

    @Transactional
    public void removeApplication(Application application) {
        applicationDao.remove(application);
    }

    @Transactional
    public List<Application> findApplicationsBySectorsandQualificationLevel(Set<Sector> sectors, int qualificationlevelid) {
        List<Application> applications = new ArrayList<>();
        for (Sector sector : sectors) {
            List<Application> sectorApplications = applicationDao.getApplicationsBySectorAndQualification(sector.getId(), qualificationlevelid);
            for (Application application : sectorApplications) {
                applications.add(application);
            }
        }
        return applications;
    }

     @Transactional 
    public void updateApplication(Application application, int qualificationlevelid, String cv, List<Integer> sectors){
        application.setQualificationlevel(qualificationLevelDao.findById(qualificationlevelid));
        application.setCv(cv);
        Set<Sector> sectorSet = new HashSet<>();
        for (Integer sectorid : sectors) {
            sectorSet.add(sectorDao.findById(sectorid));
        }
        application.setSectors(sectorSet);
    }
}