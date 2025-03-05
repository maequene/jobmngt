package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Component
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;

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
}
