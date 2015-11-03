package models.metadata;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import util.APICall;
import util.Constants;

public class Workflow {

	private static final int PAGESIZE = 10;//There is a hardcoded pagesize in backend routes file
	private static final String GET_ALL_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/getAllWorkflows/json";
	private static final String GET_PAGE_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/getPageWorkflows/json";
	private static final String GET_NUM_ENTRY = Constants.NEW_BACKEND+"workflow/getNumEntry";
	private String id;
	private String workflowName;
	private String purpose;
	private String createTime;
	private String versionNo;
	private long rootWorkflowId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public long getRootWorkflowId() {
		return rootWorkflowId;
	}

	public void setRootWorkflowId(long rootWorkflowId) {
		this.rootWorkflowId = rootWorkflowId;
	}

	public static List<Workflow> all() {
		List<Workflow> workflows = new ArrayList<Workflow>();

		JsonNode workflowsNode = APICall
				.callAPI(GET_ALL_WORKFLOW_CALL);

		if (workflowsNode == null || workflowsNode.has("error")
				|| !workflowsNode.isArray()) {
			return workflows;
		}
		
		for (int i = 0; i < workflowsNode.size(); i++) {
			JsonNode json = workflowsNode.path(i);
			Workflow newWorkflow = new Workflow();
			newWorkflow.setId(json.path("id").asText());
			newWorkflow.setWorkflowName(json.get(
					"name").asText());
			newWorkflow.setPurpose(json.path("purpose").asText());
			newWorkflow.setCreateTime(json.path("createTime").asText());
			newWorkflow.setVersionNo(json.path("versionNo").asText());
			newWorkflow.setRootWorkflowId(json.path("rootWorkflowId").asLong());
			workflows.add(newWorkflow);
		}
		
		return workflows;
	}
	
	public static List<Workflow> page(int page) {
		List<Workflow> workflows = new ArrayList<Workflow>();
		JsonNode workflowsNode;
		workflowsNode = APICall.callAPIParameter(GET_PAGE_WORKFLOW_CALL,"page",String.valueOf(page));
		if (workflowsNode == null || workflowsNode.has("error")
				|| !workflowsNode.isArray()) {
			return workflows;
		}
		
		for (int i = 0; i < workflowsNode.size(); i++) {
			JsonNode json = workflowsNode.path(i);
			Workflow newWorkflow = new Workflow();
			newWorkflow.setId(json.path("id").asText());
			newWorkflow.setWorkflowName(json.get(
					"name").asText());
			newWorkflow.setPurpose(json.path("purpose").asText());
			newWorkflow.setCreateTime(json.path("createTime").asText());
			newWorkflow.setVersionNo(json.path("versionNo").asText());
			newWorkflow.setRootWorkflowId(json.path("rootWorkflowId").asLong());
			workflows.add(newWorkflow);
		}
		return workflows;
	}
	
	public static int getNumPage() {
		JsonNode node = APICall.callAPI(GET_NUM_ENTRY);
		int numPage = (int) Math.ceil((double)node.path("numEntry").asLong() / PAGESIZE);
		return numPage;
	}
}
