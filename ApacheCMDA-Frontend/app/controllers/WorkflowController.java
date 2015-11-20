package controllers;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.metadata.Workflow;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;

public class WorkflowController extends Controller {
	
	final static Form<Workflow> workflowForm = Form.form(Workflow.class);

	public static Result home(int page) {
		return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
	}
	
	public static Result workflow(long id) {
		return ok(workflow.render(Workflow.one(id), null));
	}
	
	public static Result addWorkflow() {
		return ok(addWorkflow.render(workflowForm));
	}
	
	public static Result newWorkflow() {
		Form<Workflow> dc = workflowForm.bindFromRequest();
		ObjectNode jsonData = Json.newObject();
		try {
			jsonData.put("author", "zhongchuan");
			jsonData.put("authorId", "1");
			
			jsonData.put("name", dc.field("Name").value());
			jsonData.put("purpose", dc.field("Purpose").value());			
			jsonData.put("input", dc.field("Input").value());
			jsonData.put("output", dc.field("Output").value());
			jsonData.put("contributors", dc.field("Contributors").value());
			jsonData.put("linksInstructions", dc.field("Links_and_Instructions").value());
			jsonData.put("versionNo", dc.field("Version").value());
			jsonData.put("dataset", dc.field("Dataset").value());
			jsonData.put("otherWorkflows", dc.field("OtherWorkflows").value());
			jsonData.put("userSet", dc.field("User_Set").value());
			jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());

	        MultipartFormData body = request().body().asMultipartFormData();
	        File image = body.getFile("Image").getFile();
			byte[] imageBytes = Files.readAllBytes(image.toPath());
	        jsonData.put("image", new String(Base64.encodeBase64(imageBytes)));
			JsonNode response = Workflow.create(jsonData);
			Application.flashMsg(response);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall
					.createResponse(ResponseType.CONVERSIONERROR));
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return redirect("/workflow/new/workflow");
	}
	
}
