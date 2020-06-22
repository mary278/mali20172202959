package cn.smbms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;

import javax.validation.constraints.Past;

public interface UserMapper {
	/**
	 * 通过userCode获取User
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(@Param("userCode") String userCode)throws Exception;

	/**
	 * 通过条件查询-用户表记录数
	 *
	 * @param userName
	 * @param userRole
	 * @return @
	 */
	public int getUserCount(@Param("userName") String userName, @Param("userRole") int userRole);
	
	/**
	 * 通过条件查询-userList
	 *
	 * @param userName
	 * @param userRole
	 * @return @
	 */
	public List<User> getUserList(@Param("userName") String userName,
                                  @Param("userRole") int userRole,
                                  @Param("currentPageNo") int currentPageNo,
                                  @Param("pageSize") int pageSize);

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int adduser(User user);

	public User getUserById(@Param("id") String id);

	public int modify(User user);

	public int del(@Param("id") String id);

    public User getPwdByUserId(@Param("userCode") String userCode);


}
