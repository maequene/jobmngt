package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import jakarta.transaction.Transactional;


@Component
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AppUserDao AppUserDao;

    @Override
    public List<Company> listOfUsers() {
        return companyDao.findAll("appuser.mail", "ASC");
    }

    @Override
    @Transactional
    public void addCompany(String mail, String password, String denomination, String description, String city) {
        AppUser appUser = new AppUser();
        appUser.setMail(mail);
        appUser.setPassword(password);
        appUser.setCity(city);
        AppUserDao.persist(appUser);
        
        Company aNewCompany = new Company();
        aNewCompany.setId(appUser.getId()); // Associer explicitement l'ID
        aNewCompany.setDenomination(denomination);
        aNewCompany.setDescription(description);
        appUser.setCompany(aNewCompany);
        companyDao.persist(aNewCompany);
    }

    @Override
    @Transactional
    public Company getCompany(Integer id) {
        return companyDao.findById(id);
    }

    @Override
    public boolean emailExist(String mail) {
        return AppUserDao.findByMail(mail).isEmpty();
    }

    @Override
    @Transactional 
    public void updateCompany(Company company, String denomination, String description, String city){
        company.setDenomination(denomination);
        company.setDescription(description);
        company.getAppuser().setCity(city);
    }
}