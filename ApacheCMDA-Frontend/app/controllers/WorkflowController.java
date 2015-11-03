package controllers;

import models.metadata.Workflow;
import play.mvc.*;
import views.html.climate.*;

public class WorkflowController extends Controller {

	public static Result home(int page) {
		return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
	}
}
