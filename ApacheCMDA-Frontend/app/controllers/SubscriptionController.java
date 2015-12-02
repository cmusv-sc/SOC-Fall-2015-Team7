package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.metadata.ClimateService;
import models.metadata.Workflow;
import models.metadata.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;
import java.util.List;
import models.metadata.Subscription;
import java.util.ArrayList;


public class SubscriptionController extends Controller {
	
	public static Result getSubscripterList(Long userID) {
		
		List<Subscription> workflowSub = User.getSubscripterList(userID, "Workflow");
		List<Subscription> userSub = User.getSubscripterList(userID, "User");
		List<Subscription> newList = new ArrayList<>(workflowSub.size() + userSub.size());
		newList.addAll(workflowSub);
		newList.addAll(userSub);
		return ok(subscription.render(User.one(userID), newList));
		// return ok(userProfile.render(User.one(userID), User.getUserWorkflows(userID)));
	}

	public static Result getSubscripteeList(Long userID) {
		return ok(userProfile.render(User.one(userID), User.getUserWorkflows(userID)));
		// return ok(subscription.render(User.one(userID), Subsciption.getSubscripteeList(UserID)));
	}

	// public static Result home(int page) {
	// 	return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
	// }
	
	// public static Result workflow(long id) {
	// 	return ok(workflow.render(Workflow.one(id), null));
	// }
	
	// public static Result addWorkflow() {
	// 	return ok(addWorkflow.render(workflowForm));
	// }
	
	// public static Result newWorkflow() {
	// 	Form<Workflow> dc = workflowForm.bindFromRequest();
	// 	ObjectNode jsonData = Json.newObject();
	// 	try {

	// 		String originalName = dc.field("Name").value();
	// 		String newName = originalName.replace(' ', '-');

	// 		if (newName != null && !newName.isEmpty()) {
	// 			jsonData.put("name", newName);
	// 		}
	// 		jsonData.put("purpose", dc.field("Purpose").value());
	// 		jsonData.put("versionNo", dc.field("Version").value());
	// 		jsonData.put("rootWorkflowId", dc.field("Root_Service").value());
	// 		jsonData.put("userSet", dc.field("User_Set").value());
	// 		jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());

	// 		JsonNode response = Workflow.create(jsonData);
	// 		Application.flashMsg(response);
	// 	} catch (IllegalStateException e) {
	// 		e.printStackTrace();
	// 		Application.flashMsg(APICall
	// 				.createResponse(ResponseType.CONVERSIONERROR));
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
	// 	}
	// 	return redirect("/workflow/new/workflow");
	// }
}
