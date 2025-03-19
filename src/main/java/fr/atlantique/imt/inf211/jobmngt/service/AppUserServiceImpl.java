package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Component
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private JobOfferDao jobofferdao;

    @Autowired
    private CompanyDao companydao;

    @Override
    public List<AppUser> listOfUsers() {
        return appUserDao.findAll("name", "ASC");
    }

    @Override
    public AppUser getUserapp(Integer id) {
        return appUserDao.findById(id);
    }

    @Override
    public Long nbUsers() {
        return appUserDao.count();
    }

    @Override
    public Optional<AppUser> checkLogin(AppUser u) {
        return appUserDao.checkLogin(u);
    }

    @Transactional
    public void removeAppUser(AppUser u) {
        AppUser appuser_db = appUserDao.findById(u.getId());
        for (JobOffer joboffer : appuser_db.getCompany().getJoboffers()) {
            jobofferdao.remove(joboffer);
        }
        companydao.remove(appuser_db.getCompany());
        appUserDao.remove(appuser_db);
    }

}
