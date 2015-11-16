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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import util.APICall;
import util.Constants;

public class User {
	private String email;
	private String firstName;
	private String lastName;
	private String id;

	private static final String ADD_USER_CALL = Constants.NEW_BACKEND+"users/add";
	private static final String AUTH_USER_CALL = Constants.NEW_BACKEND+"users/isUserValid";
	private static final String GET_USER_CALL = Constants.NEW_BACKEND+"users/102";
	
	/**
	*
	*/
	public static User  getUser(){
		List<User> userList = new ArrayList<User>();

		JsnNode userJson =  APICall.callAPI(GET_USER_CALL);
		User newUser = new User();
		newUser.firstName = userJson.path("firstName").asText();
		newUser.lastName = userJson.path("lastName").asText();
		newUser.email = userJson.path("email").asText();
		userList.add(newUser)；
		return newUser;
	}
	/**
	 * Create a new user
	 *
	 * @param jsonData
	 * @return the response from the API server
	 */
	public static JsonNode create(JsonNode jsonData) {
		return APICall.postAPI(ADD_USER_CALL, jsonData);
	}

	/**
	 * Authenticate a user
	 *
	 * @param jsonData
	 * @return the response from the API server
	 */
	public static JsonNode auth(JsonNode jsonData) {
		return APICall.postAPI(AUTH_USER_CALL, jsonData);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
}

