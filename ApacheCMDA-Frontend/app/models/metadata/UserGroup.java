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


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import util.APICall;
import util.Constants;

public class UserGroup {

	private long id;
	private String author;
	private String groupName;
	private long authorId;

	private List<User> memberList;
	private List<User> adminList;

	private static final String GET_ALL_GROUP_CALL = Constants.NEW_BACKEND+"group/getAllGroups/json";
	
	public UserGroup() {
	}
	
	public UserGroup(long id, String author, String groupName, long authorId) {
		super();
		this.id = id;
		this.author = author;
		this.groupName = groupName;
		this.authorId = authorId;
		// this.adminList = new ArrayList<>();
		// this.memberList = new ArrayList<>();
	}

    public List<User> getAdminList() {
        return this.adminList;
    }

    public void setAdminList(List<User> adminList) {
        this.adminList = adminList;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public List<User> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<User> memberList) {
        this.memberList = memberList;
    }
//	public long getId() {
//		return this.id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getGroupName() {
//		return groupName;
//	}
//
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
//
//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}
//
//	public long getAuthorId() {
//		return authorId;
//	}
//
//	public void setAuthorId(long id) {
//		this.authorId = id;
//	}
//
//	// public List<User> getAdminList() {
//	// 	return adminList;
//	// }
//
//	// public List<User> getMemberList() {
//	// 	return memberList;
//	// }
//
//	public void setAdminList(String adminList) {
//		this.adminList = adminList;
//	}
//	public void setMemberList(String memberList) {
//		this.memberList = memberList;
//	}
//	public String getAdminList() {
//		return this.adminList;
//	}
//	public String getMemberList() {
//		return this.memberList;
//	}

	public static List<UserGroup> all() {
		List<UserGroup> groups = new ArrayList<UserGroup>();

		JsonNode jsonNode = APICall.callAPI(GET_ALL_GROUP_CALL);

		if (jsonNode == null || jsonNode.has("error")
				|| !jsonNode.isArray()) {
			return groups;
		}
		
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode json = jsonNode.path(i);
			UserGroup newGroup = new UserGroup(json.path("id").asLong(), json.path("author").asText(), 
				json.path("groupName").asText(), json.path("authorId").asLong());
			groups.add(newGroup);
		}
		
		return groups;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserGroup: [id=" + id + ", author=" + author + ", authorId=" + authorId);
		// sb.append(", Admininstrator number=").append(adminList.size()).append(",");
		// sb.append(", Member number=").append(memberList.size()).append("]");
		return sb.toString();
	}
	
}