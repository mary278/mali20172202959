package cn.smbms.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> getRoleList() {
		return  roleMapper.getRoleList();
	}

	@Override
	public List<Role> getRoleList1(String roleName) {
		return  roleMapper.getRoleList1(roleName);
	}
	
}
