package controllers;

import java.io.File;
import java.nio.file.Files;

import models.metadata.Workflow;
import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.metadata.UserGroup;
import models.metadata.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;

public class UserGroupController extends Controller {

	final static Form<UserGroup> groupForm = Form.form(UserGroup.class);

	public static Result home() {
//		return ok(views.html.climate.userGroups.render(UserGroup.all()));
		return ok(userGroups.render(UserGroup.all(), groupForm));
	}

	public static Result group(long id) {
        boolean a = group(id, 111);
        System.out.println(a);
		return ok(userGroup.render(UserGroup.one(id), groupForm));
	}

	public static boolean group(long id, long userId) {
        UserGroup ug = UserGroup.one(id);
        for (User u : ug.getMemberList()) {
            if (u.getId() == userId)
                return true;
        }

        for (User u : ug.getAdminList()) {
            if (u.getId() == userId)
                return true;
        }
        return false;
	}

	public static Result newAdmin(Long groupId) {
//		long groupId = Long.parseLong(groupIdStr);
		Form<UserGroup> dc = groupForm.bindFromRequest();
		long id = Long.parseLong(dc.field("newAdminId").value());

		//Determine whether he/she has the access to add admin.
		if (session().get("id") == null) {
			System.out.println("Have to log in before adding admins");
			return ok(error.render("Have to log in before adding admins"));
		}

		JsonNode response = UserGroup.addNewAdmin(groupId, id);
		Application.flashMsg(response);
		return redirect("/groups/id/"+groupId);
	}

	public static Result newMember(Long groupId) {
		Form<UserGroup> dc = groupForm.bindFromRequest();
		long id = Long.parseLong(dc.field("newMemberId").value());

		//Determine whether he/she has the access to add admin.
		if (session().get("id") == null) {
			System.out.println("Have to log in before adding members");
			return ok(error.render("Have to log in before adding members"));
		}

		JsonNode response = UserGroup.addNewMember(groupId, id);
		Application.flashMsg(response);
		return redirect("/groups/id/"+groupId);
	}

	public static Result newGroup() {
		System.out.println("newGroup visited");
		Form<UserGroup> dc = groupForm.bindFromRequest();
		String groupName = dc.field("GroupName").value();
		System.out.println(groupName);
		if (session().get("id") == null) {
			System.out.println("Have to log in before creating groups");
			return ok(error.render("Have to log in before creating groups"));
		}
		long id = Long.parseLong(session().get("id"));
		JsonNode response = UserGroup.create(groupName, id);
		System.out.println("Created");
 		Application.flashMsg(response);
		return redirect("/groups");
//		try {
//			jsonData.put("author", session("userName"));
//			jsonData.put("authorId", session("id"));
//			jsonData.put("name", dc.field("Name").value());
//			jsonData.put("purpose", dc.field("Purpose").value());
//			jsonData.put("input", dc.field("Input").value());
//			jsonData.put("output", dc.field("Output").value());
//			jsonData.put("contributors", dc.field("Contributors").value());
//			jsonData.put("linksInstructions", dc.field("Links_and_Instructions").value());
//			jsonData.put("versionNo", dc.field("Version").value());
//			jsonData.put("dataset", dc.field("Dataset").value());
//			jsonData.put("otherWorkflows", dc.field("OtherWorkflows").value());
//			jsonData.put("userSet", dc.field("User_Set").value());
//			jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());
//
//			MultipartFormData body = request().body().asMultipartFormData();
//			File image = body.getFile("Image").getFile();
//			byte[] imageBytes = Files.readAllBytes(image.toPath());
//			jsonData.put("image", new String(Base64.encodeBase64(imageBytes)));
//			JsonNode response = Workflow.create(jsonData);
//			Application.flashMsg(response);
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//			Application.flashMsg(APICall
//					.createResponse(ResponseType.CONVERSIONERROR));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
//		}
	}


	// public static Result home(int page) {
//	 	return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
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
	// 		jsonData.put("author", session("userName"));
	// 		jsonData.put("authorId", session("id"));
	// 		jsonData.put("name", dc.field("Name").value());
	// 		jsonData.put("purpose", dc.field("Purpose").value());
	// 		jsonData.put("input", dc.field("Input").value());
	// 		jsonData.put("output", dc.field("Output").value());
	// 		jsonData.put("contributors", dc.field("Contributors").value());
	// 		jsonData.put("linksInstructions", dc.field("Links_and_Instructions").value());
	// 		jsonData.put("versionNo", dc.field("Version").value());
	// 		jsonData.put("dataset", dc.field("Dataset").value());
	// 		jsonData.put("otherWorkflows", dc.field("OtherWorkflows").value());
	// 		jsonData.put("userSet", dc.field("User_Set").value());
	// 		jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());

	//         MultipartFormData body = request().body().asMultipartFormData();
	//         File image = body.getFile("Image").getFile();
	// 		byte[] imageBytes = Files.readAllBytes(image.toPath());
	//         jsonData.put("image", new String(Base64.encodeBase64(imageBytes)));
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

 //    public static Result getImage(long id) {
 //        Workflow tmp = Workflow.one(id);
 //        return ok(tmp.getImage()).as("image/png");
    // }
}
