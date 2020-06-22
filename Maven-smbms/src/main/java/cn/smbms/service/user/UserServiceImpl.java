package cn.smbms.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
    private UserMapper userMapper;

	@Override
	public User login(String userCode, String userPassword) throws Exception {
		User user = null;
		user = userMapper.getLoginUser(userCode);
		//匹配密码
		/*if(null != user){
			if(!user.getUserPassword().equals(userPassword))
			    user = null;
		}*/
		return user;
	}

	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		
		int count = 0;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		count = userMapper.getUserCount(queryUserName,queryUserRole);
		return count;
	}
	
	public List<User> getUserList(String queryUserName, int queryUserRole,
			int currentPageNo, int pageSize) {
		int beginIndex = (currentPageNo-1)*pageSize;
		return userMapper.getUserList(queryUserName, queryUserRole, beginIndex,
				pageSize);
	}

	@Override
	public int adduser(User user) {
		return userMapper.adduser(user);
	}

	@Override
	public User getUserById(String id) {
		return userMapper.getUserById(id);
	}

	@Override
	public Boolean modify(User user) {
		Boolean flag=false;
		if(userMapper.modify(user)>0){
          flag=true;
		}
		return flag;
	}

	@Override
	public User selectUserCodeExist(String userCode){
		try {
			return userMapper.getLoginUser(userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int del(String id) {
		return userMapper.del(id);
	}

	@Override
	public User getPwdByUserId(String userCode) {
		return userMapper.getPwdByUserId(userCode);
	}


}
