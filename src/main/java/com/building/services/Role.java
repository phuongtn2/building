package com.building.services;

import com.building.util.str.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum Role {
	MEMBER()
	, ADMIN();

	private Role() {
		;
	}

	public static List<Role> toRoleList(List<String> roleStrList) {
		final ArrayList<Role> al = new ArrayList<Role>(roleStrList.size());
		for(final String roleStr : roleStrList) {
			try {
				al.add(Role.valueOf(roleStr));
			} catch (Exception e) {
				//LOG 存在しないロール定数
			}
		}
		return al;
	}

	public static String toHeaderRoleListValue(Collection<Role> roleCol) {
		if(roleCol==null || roleCol.isEmpty()) {
			return "";
		}
		final ArrayList<String> roleIdList = new ArrayList<String>(roleCol.size());
		for(final Role role : roleCol) {
			roleIdList.add(role.toString());
		}
		return StringUtil.join(roleIdList, ",");
	}
}
