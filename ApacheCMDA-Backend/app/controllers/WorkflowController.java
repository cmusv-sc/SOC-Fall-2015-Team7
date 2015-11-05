package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import models.ClimateService;
import models.ClimateServiceRepository;
import models.User;
import models.UserRepository;
import models.Workflow;
import models.WorkflowRepository;
import play.mvc.Controller;
import play.mvc.Result;

@Named
@Singleton
public class WorkflowController extends Controller{

	private final WorkflowRepository workflowRepository;
	private final ClimateServiceRepository climateServiceRepository;
	private final UserRepository userRepository;
	
	// We are using constructor injection to receive a repository to support our
	// desire for immutability.
	@Inject
	public WorkflowController(final WorkflowRepository workflowRepository, ClimateServiceRepository climateServiceRepository,
			UserRepository userRepository) {
		this.workflowRepository = workflowRepository;
		this.climateServiceRepository = climateServiceRepository;
		this.userRepository = userRepository;
	}
	
	public Result getAllWorkflows(String format) {
		Iterable<Workflow> workflowIterable = workflowRepository.findAll();
		List<Workflow> workflowList = new ArrayList<Workflow>();
		for (Workflow workflow : workflowIterable) {
			workflowList.add(workflow);
		}
		String result = new String();
		if (format.equals("json")) {
			result = new Gson().toJson(workflowList);
		}
		return ok(result);
	}
	
	public Result getPageWorkflows(String format, int page, int size) {

		int pageStartFrom1 = page-1;
		Page<Workflow> workflowPage = workflowRepository.findAll(new PageRequest(pageStartFrom1, size));
		
		List<Workflow> workflowList = workflowPage.getContent();

		String workflows = null;
		if (format.equals("json")) {
			workflows = new Gson().toJson(workflowList);
		}
		
		return ok(workflows);
	}
	
	public Result getOneWorkflow(String format, long id) {
		List<Workflow> result = workflowRepository.findById(id);
		
		String workflow = null;
		if (format.equals("json")) {
			workflow = new Gson().toJson(result.get(0));
		}
		
		return ok(workflow);
	}
	
	public Result getNumEntry() {
		JsonObject rtn = new JsonObject();
		rtn.addProperty("numEntry", workflowRepository.count());
		return ok(rtn.toString());
	}
	
	public Result addWorkflow() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Workflow not created, expecting Json data");
			return badRequest("Workflow not created, expecting Json data");
		}

		// Parse JSON file
		String name = json.path("name").asText();
		String purpose = json.path("purpose").asText();
		Date createTime = new Date();
		String versionNo = json.path("versionNo").asText();
		long rootWorkflowId = json.path("rootWorkflowId").asLong();
	    String userIdSet = json.path("userSet").asText();
	    String climateServiceIdSet = json.path("climateServiceSet").asText();

	    List<String> userIdList = Arrays.asList(userIdSet.split("\\s*,\\s*"));
	    List<String> climateServiceIdList = Arrays.asList(climateServiceIdSet.split("\\s*,\\s*"));
	    
	    List<User> userSetList = new ArrayList<User>();
	    List<ClimateService> climateServiceSetList = new ArrayList<ClimateService>();
	    
	    for (String userId : userIdList) {
	    	userSetList.add(userRepository.findOne(Long.parseLong(userId)));
	    }
	    for (String climateServiceId : climateServiceIdList) {
	    	climateServiceSetList.add(climateServiceRepository.findOne(Long.parseLong(climateServiceId)));
	    }
	    
		try {
			if (workflowRepository.findByName(name).size()>0) {
				System.out.println("Workflow exist in database " + name);
				return badRequest("Workflow exist in database");
			}
			
			Workflow workflow = new Workflow(name, purpose, createTime,
					versionNo, rootWorkflowId, userSetList, climateServiceSetList) ;	
			workflowRepository.save(workflow);
			System.out.println("Workflow saved: " + workflow.getId());
			return created(new Gson().toJson(workflow.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Workflow not saved: " + name);
			return badRequest("Workflow not saved: " + name);
		}
	}
	
	public Result deleteWorkflowById(long id) {
		Workflow workflow = workflowRepository.findOne(id);
		if (workflow == null) {
			System.out.println("Workflow not found with id: " + id);
			return notFound("Workflow not found with id: " + id);
		}

		workflowRepository.delete(workflow);
		System.out.println("Workflow is deleted: " + id);
		return ok("Workflow is deleted: " + id);
	}
}
