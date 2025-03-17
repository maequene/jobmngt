package fr.atlantique.imt.inf211.jobmngt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.atlantique.imt.inf211.jobmngt.service.CompanyService;
import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;

@Controller
public class PagesController {

	@Autowired
	private CompanyService companyServ;

	@Autowired
	private CandidateService candidateServ;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView modelAndView = new ModelAndView("index");
		int nbr_company = companyServ.listOfUsers().size();
		int nbr_candidate = candidateServ.listOfCandidate().size();
		modelAndView.addObject("countCompanies", nbr_company);
		modelAndView.addObject("countCandidates", nbr_candidate);

		return modelAndView;

	}
}