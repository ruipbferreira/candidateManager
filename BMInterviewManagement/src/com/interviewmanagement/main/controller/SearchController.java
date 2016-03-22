package com.interviewmanagement.main.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;
import com.interviewmanagement.main.model.RefData;
import com.interviewmanagement.main.model.User;
import com.interviewmanagement.main.service.CandidateService;
import com.interviewmanagement.main.utils.LanguageEditor;
import com.interviewmanagement.main.utils.LocalityEditor;
import com.interviewmanagement.main.utils.UserEditor;

@Controller
public class SearchController {

	private @Autowired LocalityEditor localityEditor;
	private @Autowired LanguageEditor languageEditor;
	private @Autowired UserEditor userEditor;

	private CandidateService candidateService;

	@Autowired(required=true)
	@Qualifier(value="candidateService")
	public void setCandidateService(CandidateService ps){
		this.candidateService = ps;
	}

	@RequestMapping(value = "/manageCV", method = RequestMethod.GET)
	public String loginPage(Model model, Principal principal, HttpServletRequest request) {
		Candidate candidate = new Candidate();
		if(request.getSession().getAttribute("isFromRemove") != null &&
				((Boolean)request.getSession().getAttribute("isFromRemove"))) {
			request.getSession().setAttribute("isFromRemove", false);
			List<Candidate> result = candidateService.listCandidate(model.asMap().get("candidate") == null ? candidate : (Candidate) model.asMap().get("candidate"));
			model.addAttribute("result", result);
		}

		if(request.getSession().getAttribute("isFromSearch") != null &&
				((Boolean)request.getSession().getAttribute("isFromSearch"))) {
			request.getSession().setAttribute("isFromSearch", false);
			List<Candidate> result = (List<Candidate>) request.getSession().getAttribute("searchResult");
			request.getSession().setAttribute("searchResult", null);

			
			model.addAttribute("result", result);
		}

		model.addAttribute("title", "Manage CV");
		model.addAttribute("candidate", request.getSession().getAttribute("candidate") == null ? candidate : request.getSession().getAttribute("candidate"));
		model.addAttribute("result", model.asMap().get("result") == null ? new ArrayList<Candidate>() : model.asMap().get("result"));
		List<RefData> listOfLevels = candidateService.listRefData("LEVEL");
		model.addAttribute("listLevels", listOfLevels);
		
		List<Locality> listOfLocals = candidateService.lislLocalities();
		model.addAttribute("listOfILocals", listOfLocals);

		List<Language> listOfLanguages = candidateService.lisLanguages();
		model.addAttribute("listOfILanguages", listOfLanguages);

		List<User> listOfUsers = candidateService.listUsers();
		model.addAttribute("listOfUsers", listOfUsers);

		return "manageCVPage";
	}

	@RequestMapping(value = "/manageCV", method = RequestMethod.POST)
	public String executeSearch(Model model, @ModelAttribute("candidate") Candidate c, HttpServletRequest request) {
		request.getSession().setAttribute("isFromSearch", true);
		List<Candidate> result = candidateService.listCandidate(c);
		model.addAttribute("candidate", c);
		request.getSession().setAttribute("searchResult", result);
		request.getSession().setAttribute("candidate", c);
		return "redirect:/manageCV";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

		binder.registerCustomEditor(Locality.class, this.localityEditor);
		binder.registerCustomEditor(Language.class, this.languageEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
	}

	@RequestMapping("/remove/{id}")
	public String removeCandidate(Model model, @PathVariable("id") int id, HttpServletRequest request){

		this.candidateService.removeCandidate(id);
		request.getSession().setAttribute("isFromRemove", true);

		return "redirect:/manageCV";
	}
}
