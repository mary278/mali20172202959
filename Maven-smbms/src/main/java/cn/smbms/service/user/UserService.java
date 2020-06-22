package cn.smbms.service.user;

import java.util.List;

import cn.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public User login(String userCode, String userPassword) throws Exception;

	public int getUserCount(String queryUserName, int _queryUserRole);
	
	/**
	 * 根据条件查询用户列表
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

	public int adduser(User user);

	public User getUserById(String id);

	public Boolean modify(User user);

	public User selectUserCodeExist(String userCode);

	public int del(String id);

	public User getPwdByUserId(String userCode);
}
