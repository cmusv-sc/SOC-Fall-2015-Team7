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

package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.metadata.User;
import util.APICall;
import play.mvc.*;
import play.libs.Json;
import views.html.*;
import play.data.DynamicForm;

public class UserController extends Controller {

    public static Result registerPage() {
    	return ok(registerus.render("test"));
    }
    public static Result loginPage() {
    	return ok(login.render("test"));
    }

    public static Result registerUser(){
	    DynamicForm df = DynamicForm.form().bindFromRequest();
	    String email = df.field("email").value();
	    String password = df.field("password").value();
	    String firstName = df.field("firstName").value();
	    String lastName = df.field("lastName").value();

		ObjectNode jsonData = Json.newObject();
		jsonData.put("userName", email);
		jsonData.put("email", email);
		jsonData.put("password", password);
		jsonData.put("firstName", firstName);
		jsonData.put("lastName", lastName);
		JsonNode response = User.create(jsonData);
		Application.flashMsg(response);
	
	System.out.println(response.toString());

	    return ok(registerus.render("test"));
    }

    public static Result loginUser(){
	    DynamicForm df = DynamicForm.form().bindFromRequest();
	    String email = df.field("email").value();
	    String password = df.field("password").value();
		ObjectNode jsonData = Json.newObject();
		jsonData.put("email", email);
		jsonData.put("password", password);

		JsonNode response = User.auth(jsonData);
		Application.flashMsg(response);

		return ok(userprofile.render(email));
	}
    	//return ok(login.render("test"));    }
}
