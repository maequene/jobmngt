package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;

@RestController
@RequestMapping(value = "/api/companies")
public class TestCompanyDaoController {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AppUserDao appUserDao;

    //Lister toutes les entreprises existantes
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Company> all() {
        List<Company> list = companyDao.findAll("appuser.mail", "ASC");
        return list;
    }

    /*
     * Create a company with no joboffers
     * ("denomination": "myFirstCompany",
     * "description": "Desc of my new company",
     * "mail":"mnc@imt.fr", "password":"2580", "city": "Brest")
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company newCompany() {

        AppUser appUser = new AppUser();
        appUser.setMail("mnc@imt.fr");
        appUser.setPassword("2580");
        appUser.setCity("Brest");
        appUserDao.persist(appUser);
        //appUserDao.flush(); // Force l'enregistrement immédiat pour récupérer l'ID
        
        Company aNewCompany = new Company();
        aNewCompany.setId(appUser.getId()); // Associer explicitement l'ID
        aNewCompany.setAppuser(appUser);
        aNewCompany.setDenomination("myFirstCompany");
        aNewCompany.setDescription("Desc of my new company");

        companyDao.persist(aNewCompany);
        return aNewCompany;
    }

    // Get information of a company by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company one(@PathVariable int id) {
        return companyDao.findById(id);
    }

    // Modify information about a company
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company replaceCompany(@PathVariable int id) {
        Company company = companyDao.findById(id);
        if (company != null) {
            company.getAppuser().setMail("atlantique@imt.fr");
            company.getAppuser().setPassword("5678");
            company.setDenomination("IMT Atlantique");
            company.setDescription("Une école d\'ingénieurs généraliste");

            return companyDao.merge(company);
        }
        return null;
    }

    // Delete a company
    // curl -X DELETE localhost:8080/api/companies/7
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable int id) {
        Company company = companyDao.findById(id);
        if (company != null) {
            companyDao.remove(company);
        }
    }
}