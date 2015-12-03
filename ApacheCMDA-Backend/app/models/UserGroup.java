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
import java.util.ArrayList;

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
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String author;
	private String groupName;
	private long authorId;

	//	private long rootWorkflowId;
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	private List<User> adminList;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndClimateService", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "climateServiceId", referencedColumnName = "id") })
	private List<User> memberList;
	
	public UserGroup() {
	}
	
	public UserGroup(String author, String groupName, long authorId) {
		super();
		this.author = author;
		this.groupName = groupName;
		this.authorId = authorId;
		this.adminList = new ArrayList<>();
		this.memberList = new ArrayList<>();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long id) {
		this.authorId = id;
	}

	public List<User> getAdminList() {
		return adminList;
	}

	public List<User> getMemberList() {
		return memberList;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserGroup: [id=" + id + ", author=" + author + ", authorId=" + authorId);
		sb.append(", Admininstrator number=").append(adminList.size()).append(",");
		sb.append(", Member number=").append(memberList.size()).append("]");
		return sb.toString();
	}
	
}