package com.interviewmanagement.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
public class CandidateController {

	private @Autowired LocalityEditor localityEditor;
	private @Autowired LanguageEditor languageEditor;
	private @Autowired UserEditor userEditor;
	private CandidateService candidateService;

	private static final int BUFFER_SIZE = 4096;

	@Value("${repository}")
	private String repository;

	@Autowired(required=true)
	@Qualifier(value="candidateService")
	public void setCandidateService(CandidateService ps){
		this.candidateService = ps;
	}

	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String openCandidatePage(Model model, Principal principal, HttpServletRequest request) {
		if(request.getParameter("id") != null) {
			Integer id = Integer.valueOf(request.getParameter("id"));
			Candidate result = candidateService.getCandidateById(id);
			model.addAttribute("candidate", result);
			model.addAttribute("title", result.getName());

		} else {
			model.addAttribute("candidate", new Candidate());
			model.addAttribute("title", "Novo");
		}

		List<RefData> listOfSituations = candidateService.listRefData("SITUATION");
		model.addAttribute("listSituations", listOfSituations);

		List<RefData> listOfLevels = candidateService.listRefData("LEVEL");
		model.addAttribute("listLevels", listOfLevels);

		List<Locality> listOfLocals = candidateService.lislLocalities();
		model.addAttribute("listOfILocals", listOfLocals);

		List<Language> listOfLanguages = candidateService.lisLanguages();
		model.addAttribute("listOfILanguages", listOfLanguages);

		List<User> listOfUsers = candidateService.listUsers();
		List<User> users = new ArrayList<User>();
		for (User user : listOfUsers) {
			if(user.getRole().equals("ROLE_ADMIN")) {
				continue;
			}
			users.add(user);
		}
		model.addAttribute("listOfUsers", users);

		return "candidatePage";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCandidate(@ModelAttribute("candidate") @Validated Candidate c, 
			BindingResult result, Model model, @RequestParam(value = "file", required = false) MultipartFile file) {
		Candidate oldCandidate = null;
		if(c.getId() != null) {
			oldCandidate = this.candidateService.getCandidateById(c.getId());
		}
		if(oldCandidate != null) {
			if((file == null || file.getSize() == 0) && oldCandidate.getFilename() != null) {
				c.setFilename(oldCandidate.getFilename());
			}
		}

		Integer id = this.candidateService.createOdUpdateCandidate(c);
		if(file != null && file.getSize() > 0) {
			try {
				String filename = saveCV(id + "." + FilenameUtils.getExtension(file.getOriginalFilename()), file);
				Candidate candidate = this.candidateService.getCandidateById(id);

				candidate.setFilename(filename);
				this.candidateService.createOdUpdateCandidate(candidate);
			} catch (Exception e) {
			}
		}
		return "redirect:/candidate?id=" + id;
	}

	@RequestMapping(value="/download/{id}", method = RequestMethod.GET)
	public void doDownload(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getServletContext();

		Candidate c = this.candidateService.getCandidateById(id);
		if(c != null && c.getFilename() != null) {

			// construct the complete absolute path of the file
			String fullPath = repository + System.getProperty("file.separator") + c.getFilename();      
			File downloadFile = new File(fullPath);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					c.getName() + "." + FilenameUtils.getExtension(c.getFilename()));
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		}
	}

	private String saveCV(String filename, MultipartFile fileToSave)
			throws RuntimeException, IOException {
		try {
			File file = new File(repository + System.getProperty("file.separator")
			+ filename);
			FileUtils.writeByteArrayToFile(file, fileToSave.getBytes());
			return filename;
		} catch (IOException e) {
			throw e;
		}

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		binder.registerCustomEditor(Locality.class, this.localityEditor);
		binder.registerCustomEditor(Language.class, this.languageEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
	}

}
