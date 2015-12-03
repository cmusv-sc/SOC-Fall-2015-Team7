package controllers;

import com.google.gson.Gson;
import models.User;
import models.UserGroup;
import models.UserGroupRepository;
import models.UserRepository;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class UserGroupController extends Controller {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    // We are using constructor injection to receive a repository to support our
    // desire for immutability.
    @Inject
    public UserGroupController(final UserGroupRepository userGroupRepository,
                               final UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    public Result getAllGroups(String format) {
        Iterable<UserGroup> groupIterable = userGroupRepository.findAll();
        List<UserGroup> groupList = new ArrayList<UserGroup>();
        for (UserGroup group : groupIterable) {
            groupList.add(group);
        }
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(groupList);
        }
        return ok(result);
    }

// 	public Result getPageWorkflows(String format, int page, int size) {

// 		int pageStartFrom1 = page-1;
// 		Page<Workflow> workflowPage = workflowRepository.findAll(new PageRequest(pageStartFrom1, size));

// 		List<Workflow> workflowList = workflowPage.getContent();

// 		String workflows = null;
// 		if (format.equals("json")) {
// 			workflows = new Gson().toJson(workflowList);
// 		}

// 		return ok(workflows);
// 	}

// 	public Result getOneWorkflow(String format, long id) {
// 		List<Workflow> result = workflowRepository.findById(id);

// 		String workflow = null;
// 		if (format.equals("json")) {
// 			workflow = new Gson().toJson(result.get(0));
// 		}

// 		return ok(workflow);
// 	}

// //Modified by yiming.
// //TODO: Return json of List of workflows. Now we return id string, for testing.
// 	public Result getWorkflowsOfUser(long id) {
// 		List<Workflow> workflowList = workflowRepository.findById(id);
// 		System.out.println(workflowList.size());
// 		System.out.println(workflowList.toString());
// 		List<String> result = new ArrayList<>();
// 		List<Workflow> workflowResult = new ArrayList<>();
// 		// StringBuffer workflowIds = new StringBuffer();
// 		// String splitter = "";
// 		for (Workflow i:workflowList) {
// 			Workflow newflow = new Workflow();
// 			newflow.setName(i.getName());
// 			newflow.setCreateTime(i.getCreateTime());
// 			// result.add(Long.toString(i.getId()));
// 			workflowResult.add(newflow);
// 		}

// 		return ok(new Gson().toJson(workflowResult));
// 		// return ok(result.toString());
// 	}

// 	public Result getWorkflowByAuthorId(int id) {
// 		List<Workflow> result = workflowRepository.findByAuthorId(id);
// 		String workflow = new Gson().toJson(result);
// 		return ok(workflow);
// 	}

// 	public Result getNumEntry() {
// 		JsonObject rtn = new JsonObject();
// 		rtn.addProperty("numEntry", workflowRepository.count());
// 		return ok(rtn.toString());
// 	}

    // Add user group. First test if a group exists with this name.
    // If not, add. Otherwise, don't add.
    public Result addUserGroup(String groupName, long authorId) {
        List<UserGroup> groupWithSameName = userGroupRepository
                .findByGroupName(groupName);
        if (!groupWithSameName.isEmpty()) {
            System.out.println("Group with this name exists. Group not " +
                    "created.");
            return badRequest("Group with this name exists. Group not created" +
                    ".");
        }
        try {
            String author = userRepository.findById(authorId).getUserName();
            UserGroup newGroup = new UserGroup(author, groupName, authorId);
            // Author is by default administrator.
            newGroup.getAdminList().add(userRepository.findById(authorId));
            userGroupRepository.save(newGroup);
            System.out.println("UserGroup saved: " + newGroup.getId());
            return created(new Gson().toJson(newGroup.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("UserGroup not saved: ");
            return badRequest("UserGroup not saved: ");
        }
    }

    public Result addAdminToGroup(long groupId, long userId) {
        try {
            UserGroup targetGroup = userGroupRepository.findById(groupId);
            User thisUser =  userRepository.findById(userId);
            System.out.println("##########admin list size:" + targetGroup
                    .getAdminList().size());
            if (targetGroup.getAdminList().contains(thisUser)) {
                System.out.println("User not added as admin. Already admin.");
                return badRequest("User not added as admin. Already admin.");
            }
            if (targetGroup.getMemberList().contains(thisUser)) {
                targetGroup.getMemberList().remove(thisUser);
                targetGroup.getAdminList().add(thisUser);
                System.out.println(userId + " has been promoted to admin in " +
                        "group " + groupId);
            } else {
                targetGroup.getAdminList().add(thisUser);
                System.out.println(userId + "has been added to group " +
                        groupId + " as admin");
            }

            System.out.println("##########admin list size after adding:" +
                    targetGroup.getAdminList().size());
            userGroupRepository.save(targetGroup);
            return created("Success");
        } catch (Exception e) {
            return badRequest("User Not set as admin");
        }
    }

// 	public Result addWorkflow() {
// 		JsonNode json = request().body().asJson();
// 		if (json == null) {
// 			System.out.println("Workflow not created, expecting Json data");
// 			return badRequest("Workflow not created, expecting Json data");
// 		}

// 		// Parse JSON file
// 		String author = json.path("author").asText();
// 		int authorId = json.path("authorId").asInt();
// 		String name = json.path("name").asText();
// 		String purpose = json.path("purpose").asText();
// 		Date createTime = new Date();
// 		String imageStr = json.path("image").asText();
// 		byte[] image = Base64.decodeBase64(imageStr);
// 		String input = json.path("input").asText();
// 		String output = json.path("output").asText();
// 		String contributors = json.path("contributors").asText();
// 		String linksInstructions = json.path("linksInstructions").asText();
// 		String versionNo = json.path("versionNo").asText();
// 		String dataset = json.path("dataset").asText();
// 		String otherWorkflows = json.path("otherWorkflows").asText();
// 	    String userIdSet = json.path("userSet").asText();
// 	    String climateServiceIdSet = json.path("climateServiceSet").asText();
// 	    int isQuestion = json.path("isQuestion").asInt();
// 	    int answerId = 0; //json.path("answerId").asInt();

// 	    List<String> userIdList = Arrays.asList(userIdSet.split("\\s*,\\s*"));
// 	    List<String> climateServiceIdList = Arrays.asList(climateServiceIdSet.split("\\s*,\\s*"));

// 	    List<User> userSetList = new ArrayList<User>();
// 	    List<ClimateService> climateServiceSetList = new ArrayList<ClimateService>();

// 	    for (String userId : userIdList) {
// 	    	userSetList.add(userRepository.findOne(Long.parseLong(userId)));
// 	    }
// 	    for (String climateServiceId : climateServiceIdList) {
// 	    	climateServiceSetList.add(climateServiceRepository.findOne(Long.parseLong(climateServiceId)));
// 	    }

// 		try {
// 			if (workflowRepository.findByName(name).size()>0) {
// 				System.out.println("Workflow exist in database " + name);
// 				return badRequest("Workflow exist in database");
// 			}

// 			Workflow workflow = new Workflow(author, authorId, name, purpose, input, output, image, contributors, linksInstructions, createTime,
// 					versionNo, dataset, otherWorkflows, userSetList, climateServiceSetList,isQuestion, answerId) ;
// 			workflowRepository.save(workflow);
// 			System.out.println("Workflow saved: " + workflow.getId());
// 			return created(new Gson().toJson(workflow.getId()));
// 		} catch (PersistenceException pe) {
// 			pe.printStackTrace();
// 			System.out.println("Workflow not saved: " + name);
// 			return badRequest("Workflow not saved: " + name);
// 		}
// 	}

// 	public Result deleteWorkflowById(long id) {
// 		Workflow workflow = workflowRepository.findOne(id);
// 		if (workflow == null) {
// 			System.out.println("Workflow not found with id: " + id);
// 			return notFound("Workflow not found with id: " + id);
// 		}

// 		workflowRepository.delete(workflow);
// 		System.out.println("Workflow is deleted: " + id);
// 		return ok("Workflow is deleted: " + id);
// 	}

// 	public Result markAnswer(){
// 		JsonNode json = request().body().asJson();
// 		if (json == null) {
// 			System.out.println("Workflow not created, expecting Json data");
// 			return badRequest("Workflow not created, expecting Json data");
// 		}

// 		long workflowId = json.path("workflowId").asLong();
// 		int commentId = json.path("commentId").asInt();
// 		Workflow w = workflowRepository.findOne(workflowId);
// 		w.setAnswerId(commentId);
// 		workflowRepository.save(w);
// 		return ok("Mark answer success");
// 	}
}
