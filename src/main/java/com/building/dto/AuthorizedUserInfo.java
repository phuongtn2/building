package com.building.dto;

import com.building.services.Role;

import java.io.Serializable;
import java.util.*;

public class AuthorizedUserInfo implements Serializable {
	public static final int ADMIN = 1;
	private int userId;
	private String loginName;
	private String fullName;
	private String mail;
	private String roleGroupName;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	@SuppressWarnings("unchecked")
	private Set<Role> roleSet = (Set<Role>) Collections.EMPTY_SET;
	private String token;
	private Date createAt;
	private boolean isNew;
	/** google access_token */
	private String googleToken;

	public String getGoogleToken() {
		return googleToken;
	}

	public void setGoogleToken(String googleToken) {
		this.googleToken = googleToken;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Collection<Role> roleCol) {
		this.roleSet = Collections.unmodifiableSet(new HashSet<Role>(roleCol));
	}

	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getRoleGroupName() {
		return roleGroupName;
	}
	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
	@Override
	public String toString() {
		return "AuthorizedUserInfo [userId=" + userId + ", loginName=" + loginName + ", fullName=" + fullName
				+ ", mail=" + mail + ", roleGroupName=" + roleGroupName
				+ ", roleSet=" + roleSet + ", token=" + token + ", createAt=" + createAt + ", isNew=" + isNew  + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createAt == null) ? 0 : createAt.hashCode());
		result = prime * result + userId;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (isNew ? 1231 : 1237);
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((roleGroupName == null) ? 0 : roleGroupName.hashCode());
		result = prime * result + ((roleSet == null) ? 0 : roleSet.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorizedUserInfo other = (AuthorizedUserInfo) obj;
		if (createAt == null) {
			if (other.createAt != null)
				return false;
		} else if (!createAt.equals(other.createAt))
			return false;
		if (userId != other.userId)
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (isNew != other.isNew)
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (roleGroupName == null) {
			if (other.roleGroupName != null)
				return false;
		} else if (!roleGroupName.equals(other.roleGroupName))
			return false;
		if (roleSet == null) {
			if (other.roleSet != null)
				return false;
		} else if (!roleSet.equals(other.roleSet))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	public AuthorizedUserInfo deepClone()  {
		final AuthorizedUserInfo c = new AuthorizedUserInfo();
		c.setCreateAt(cloneDate(this.getCreateAt()));
		c.setUserId(this.getUserId());
		c.setFullName(this.getFullName());
		c.setLoginName(this.getLoginName());
		c.setMail(this.getMail());
		c.setNew(this.isNew());
		c.setRoleGroupName(this.getRoleGroupName());
		c.setRoleSet(new HashSet<Role>(this.getRoleSet()));
		c.setToken(this.getToken());
		c.setFullName(this.getFullName());
		c.setGoogleToken(this.getGoogleToken());
		return c;
	}

	private static Date cloneDate(Date d) {
		return d==null ? null : new Date(d.getTime());
	}
}
