/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models.metadata;


import com.fasterxml.jackson.databind.JsonNode;
import util.APICall;
import util.Constants;
import java.util.*;

public class User {

	private long id;

	private static final String GET_ONE_USER_CALL_TO_BACKEND = Constants.NEW_BACKEND+"users/";
	private static final String GET_ALL_WORKFLOWS_OF_A_USER_FROM_BACKEND = Constants.NEW_BACKEND+"workflow/getWorkflowsOfUser/";
	private static final String GET_ALL_SUBSCRIPTER_PART1 = Constants.NEW_BACKEND+"subscription/getSubscriptionByUserID/id/";
	private static final String GET_ALL_SUBSCRIPTER_PART2 = "/target/";
	private static final String GET_ALL_SUBSCRIPTEE_PART1 = Constants.NEW_BACKEND+"subscription/getSubscriptionByTargetID/targetid/";
	private static final String GET_ALL_SUBSCRIPTEE_PART2 = "/target/";

	public static List<Subscription> getSubscripteeList(Long targetID, String targetClass) {
		System.out.println("######getSubscripteeList called");
		JsonNode subscriptionList;
		subscriptionList = APICall.callAPI(GET_ALL_SUBSCRIPTEE_PART1 + String.valueOf(targetID) + GET_ALL_SUBSCRIPTEE_PART2 + targetClass);

		ArrayList<Subscription> resultList = new ArrayList<>();

		if (subscriptionList == null || subscriptionList.has("error")) {
			System.out.println("########Error OR Null json");
			return resultList;
		}

		for (int i = 0; i < subscriptionList.size(); i++) {
			JsonNode json = subscriptionList.path(i);
		// 	Workflow newWorkflow = new Workflow();
			Subscription newSub = new Subscription();
			newSub.setSubscriptTargetClass(json.path("subscriptTargetClass").asText());
			newSub.setTargetId(json.path("targetId").asLong());
			newSub.setUserId(json.path("userId").asLong());
			resultList.add(newSub);
		}
		return resultList;
	}

	public static List<Subscription> getSubscripterList(Long id, String targetClass) {
		System.out.println("######getSubscripterList called");
		JsonNode subscriptionList;
		subscriptionList = APICall.callAPI(GET_ALL_SUBSCRIPTER_PART1 + String.valueOf(id) + GET_ALL_SUBSCRIPTER_PART2 + targetClass);

		ArrayList<Subscription> resultList = new ArrayList<>();

		if (subscriptionList == null || subscriptionList.has("error")) {
			System.out.println("########Error OR Null json");
			return resultList;
		}

		for (int i = 0; i < subscriptionList.size(); i++) {
			JsonNode json = subscriptionList.path(i);
		// 	Workflow newWorkflow = new Workflow();
			Subscription newSub = new Subscription();
			newSub.setSubscriptTargetClass(json.path("subscriptTargetClass").asText());
			newSub.setTargetId(json.path("targetId").asLong());
			newSub.setUserId(json.path("userId").asLong());
			resultList.add(newSub);
		}
		return resultList;
	}

	public static User one(Long id) {
		JsonNode json;
		// Long id = Long.parseLong(idStr);
		json = APICall.callAPI(GET_ONE_USER_CALL_TO_BACKEND+String.valueOf(id));

		if (json == null || json.has("error")
				|| json.isArray()) {
			// return null;
			User newUser = new User();
			newUser.setFirstName(String.valueOf(id));
			return newUser;
		}
		
		User newUser = new User();
		newUser.setId(json.path("id").asLong());
		newUser.setFirstName(json.path("firstName").asText());
		newUser.setLastName(json.path("lastName").asText());
		newUser.setMiddleInitial(json.path("middleInitial").asText());
		newUser.setAffiliation(json.path("affiliation").asText());
		newUser.setTitle(json.path("title").asText());
		newUser.setEmail(json.path("email").asText());
		newUser.setMailingAddress(json.path("email").asText());
		newUser.setPhoneNumber(json.path("phoneNumber").asText());
		newUser.setFaxNumber(json.path("faxNumber").asText());
		newUser.setResearchFields(json.path("researchFields").asText());
		newUser.setHighestDegree(json.path("highestDegree").asText());
		return newUser;
		// User obj = new Gson.fromJson(json.toString(), User.class);
		// return obj;
	}


	//TODO: update to return List<Workflow>
	public static List<Workflow> getUserWorkflows(Long id) {
		// JsonNode ids;
		// ids = APICall.callAPI(GET_ALL_WORKFLOWS_OF_A_USER_FROM_BACKEND + String.valueOf(id));
		// if (ids == null || ids.has("error") || ids.isArray()) {
		// 	return "None";
		// }
		// return ids.toString();
		// System.out.println("###########getUserWorkflows called");
		JsonNode workflowList;
		List<Workflow> resultList = new ArrayList<Workflow>();
		workflowList = APICall.callAPI(GET_ALL_WORKFLOWS_OF_A_USER_FROM_BACKEND + String.valueOf(id));
		if (workflowList == null || workflowList.has("error")) {
			return resultList;
		}

		for (int i = 0; i < workflowList.size(); i++) {
			JsonNode json = workflowList.path(i);
			Workflow newWorkflow = new Workflow();
			newWorkflow.setName(json.path("name").asText());
			newWorkflow.setCreateTime(json.path("createTime").asText());
			// newService.setId(json.path("id").asText());
			// newService.setClimateServiceName(json.get(
			// 		"name").asText());
			// newService.setPurpose(json.path("purpose").asText());
			// newService.setUrl(json.path("url").asText());
			// //newService.setCreateTime(json.path("createTime").asText());
			// newService.setScenario(json.path("scenario").asText());
			// newService.setVersion(json.path("versionNo").asText());
			// newService.setRootservice(json.path("rootServiceId").asText());
			resultList.add(newWorkflow);
		}
		return resultList;
	}

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String affiliation;
	private String title;
	private String email;
	private String mailingAddress;
	private String phoneNumber;
	private String faxNumber;
	private String researchFields;
	private String highestDegree;

	// @OneToMany(mappedBy = "user", cascade={CascadeType.ALL})
	// private Set<ClimateService> climateServices = new
	// HashSet<ClimateService>();

	public User() {
	}

	public User(String userName, String password, String firstName,
			String lastName, String middleInitial, String affiliation,
			String title, String email, String mailingAddress,
			String phoneNumber, String faxNumber, String researchFields,
			String highestDegree) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.affiliation = affiliation;
		this.title = title;
		this.email = email;
		this.mailingAddress = mailingAddress;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.researchFields = researchFields;
		this.highestDegree = highestDegree;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public String getTitle() {
		return title;
	}

	public String getEmail() {
		return email;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public String getResearchFields() {
		return researchFields;
	}

	public String getHighestDegree() {
		return highestDegree;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public void setResearchFields(String researchFields) {
		this.researchFields = researchFields;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", middleInitial=" + middleInitial
				+ ", affiliation=" + affiliation + ", title=" + title
				+ ", email=" + email + ", mailingAddress=" + mailingAddress
				+ ", phoneNumber=" + phoneNumber + ", faxNumber=" + faxNumber
				+ ", researchFields=" + researchFields + ", highestDegree="
				+ highestDegree + "]";
	}
}

