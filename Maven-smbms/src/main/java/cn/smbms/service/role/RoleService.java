package cn.smbms.service.role;

import java.util.List;

import cn.smbms.pojo.Role;

public interface RoleService {
	
	public List<Role> getRoleList();
	public List<Role> getRoleList1(String roleName);
	
}
