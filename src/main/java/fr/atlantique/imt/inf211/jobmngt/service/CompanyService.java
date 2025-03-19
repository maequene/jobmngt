package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;

public interface CompanyService {

    public List<Company> listOfUsers();

    public void addCompany(String mail, String password, String denomination, String description, String city);

    public Company getCompany(Integer id);

    public boolean emailExist(String mail);

    public void removeCompany(Company company, AppUser appUser);
    
}
