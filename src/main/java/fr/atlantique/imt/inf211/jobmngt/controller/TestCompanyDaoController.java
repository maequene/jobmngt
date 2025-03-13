package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.service.CompanyService;

@Controller
@RequestMapping(value = "/companies")
public class TestCompanyDaoController {
    @Autowired
    private CompanyService companyServ;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AppUserDao appUserDao;

    //Lister toutes les entreprises existantes
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("company/companyList.html");
        List<Company> list = companyServ.listOfUsers();
        mav.addObject("companieslist", list);
        return mav;
    }

    // Affichage du form de création d'une nouvelle entreprise
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView PrintnewCompanyForm(){
        ModelAndView mav = new ModelAndView("company/companyForm.html");
        return mav;
    }
    
    // Création des données d'une nouvelle entreprise
    @RequestMapping(value = "/createdata", method = RequestMethod.GET)
    public String newCompanyData(@RequestParam String mail, @RequestParam String password, @RequestParam String denomination, @RequestParam String description, @RequestParam String city) {
        companyServ.addCompany(mail, password, denomination, description, city);
        return "redirect:/companies";
    }

    // Get information of a company by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView ShowCompany(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("company/companyView.html");
        Company company = companyServ.getCompany(id);
        mav.addObject("company", company);
        return mav;
    }

    /* 
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
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public void deleteCompany(@PathVariable int id) {
        Company company = companyDao.findById(id);
        if (company != null) {
            AppUser appUser = company.getAppuser();
            companyDao.remove(company);
            appUserDao.remove(appUser);
        }
    }*/
}