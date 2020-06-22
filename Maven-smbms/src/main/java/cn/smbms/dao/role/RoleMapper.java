package cn.smbms.dao.role;

import java.util.List;
import cn.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
	
	public List<Role> getRoleList();

	public List<Role> getRoleList1(@Param("roleName") String roleName);

}
