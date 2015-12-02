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

import java.util.ArrayList;
import java.util.List;

import models.Subscription;
import models.SubscriptionRepository;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class SubscriptionController extends Controller {

	private final SubscriptionRepository subscriptionRepository;

	// // We are using constructor injection to receive a repository to support our
	// // desire for immutability.
	@Inject
	public SubscriptionController(final SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	public Result test() {
		System.out.println("test called");
		return badRequest("TEST SUCCESSFUL");
	}

	public Result addSubscription() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Subscription not added, expecting Json data");
			return badRequest("Subscription not added, expecting Json data");
		}

		// Parse JSON file
		long userId = json.path("userId").asLong();
		String subscriptTargetClass = json.path("subscriptTargetClass").asText();
		long targetId = json.path("targetId").asLong();

		try {
			// if (subscriptionRepository.findByUserIdAndSubscriptTargetClassAndTargetId(userId, subscriptTargetClass, targetId)>0) {
			// 	System.out.println("Subscription has already recorded before");
			// 	return badRequest("Duplicated request");
			// }
			Subscription subs = new Subscription(userId, subscriptTargetClass, targetId);
			subscriptionRepository.save(subs);
			System.out.println("Subscription saved");
			return created(new Gson().toJson(subs.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Subscription not saved: " + userId + " " + subscriptTargetClass + " " + targetId);
			return badRequest("Subscription not saved: " + userId + " " + subscriptTargetClass + " " + targetId);
		}
	}

	//Returns a JSON Array
	public Result getSubscriptionByUserID(Long id, String targetClass, String format) {
		if (id == null || targetClass == null) {
			System.out.println("User id/TargetClass is null or empty!");
			return badRequest("User id/TargetClass is null or empty!");
		}

		if (!targetClass.equals("Workflow") && !(targetClass.equals("User"))) {
			System.out.println("TargetClass is not valid!");
			return badRequest("TargetClass is not valid!");
		}

		List<Subscription> subs = subscriptionRepository.findByUserIdAndSubscriptTargetClass(id, targetClass);

		if (subs.size() == 0) {
			System.out.println("Subscription not found");
			return notFound("Subscription not found");
		}
		String result = new String();
		if (format.equals("json")) {
			// result = new Gson().toJson(subs.get(0));
			result = new Gson().toJson(subs);
		}
		return ok(result);
	}

	public Result getSubscriptionByTargetID(Long targetID, String targetClass, String format) {
		if (targetID == null || targetClass == null) {
			System.out.println("Target id/TargetClass is null or empty!");
			return badRequest("Target id/TargetClass is null or empty!");
		}

		if (!targetClass.equals("Workflow") && !(targetClass.equals("User"))) {
			System.out.println("TargetClass is not valid!");
			return badRequest("TargetClass is not valid!");
		}

		List<Subscription> subs = subscriptionRepository.findBySubscriptTargetClassAndTargetId(targetClass, targetID);

		if (subs.size() == 0) {
			System.out.println("Subscription not found");
			return notFound("Subscription not found");
		}
		String result = new String();
		if (format.equals("json")) {
			// result = new Gson().toJson(subs.get(0));
			result = new Gson().toJson(subs);
		}
		return ok(result);
	}
	
}
