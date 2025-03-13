package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import jakarta.transaction.Transactional;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


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
        companyDao.persist(aNewCompany);
    }

    @Transactional
    public Company getCompany(Integer id) {
        return companyDao.findById(id);
    }


}