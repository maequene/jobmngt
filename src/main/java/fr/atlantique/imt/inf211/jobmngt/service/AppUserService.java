package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import java.util.List;
import java.util.Optional;

public interface AppUserService {

    public List<AppUser> listOfUsers();

    public AppUser getUserapp(Integer id);

    public Long nbUsers();

    public Optional<AppUser> checkLogin(AppUser u);

    public void removeAppUserCompany(AppUser u);

    public void removeAppUserCandidate(AppUser u);
}
