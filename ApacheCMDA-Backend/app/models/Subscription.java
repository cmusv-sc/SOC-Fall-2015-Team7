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
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long userId;
	private String subscriptTargetClass;
	private long targetId;

	// private List<ClimateService> climateServiceSet;
	
	public Subscription() {
	}
	
	public Subscription(long userId, String subscriptTargetClass, long targetId) {
		super();
		this.userId = userId;
		this.subscriptTargetClass = subscriptTargetClass;
		this.targetId = targetId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSubscriptTargetClass() {
		return subscriptTargetClass;
	}

	public void setSubscriptTargetClass(String subscriptTargetClass) {
		this.subscriptTargetClass = subscriptTargetClass;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
 
	@Override
	public String toString() {
		return "SubscriptionRelationship: [id: " + id + ", userId:" + userId +" subscribed to " 
		+ subscriptTargetClass + " (id:" + targetId +")]";
	}
	
	
}