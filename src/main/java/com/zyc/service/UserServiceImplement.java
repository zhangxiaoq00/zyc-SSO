package com.zyc.service;

import com.zyc.mapper.UserMapper;
import com.zyc.model.User;
import com.zyc.model.UserExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userServiceImplement")
@Transactional
public class UserServiceImplement implements UserService{

	@Resource
	public UserMapper userMapper;

    @Override
	@Transactional(readOnly=true,propagation= Propagation.REQUIRED)
	public String findUsername(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		return user.getUsername();
	}
	@Override
	@Transactional(readOnly=true,propagation= Propagation.REQUIRED)
	public User findUser(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}
	@Override
	@Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
	public int insertuUser(User user) {

		//注册用户
        int result = userMapper.insert(user);
		return result;
	}
	@Override
	@Transactional(readOnly=true,propagation= Propagation.REQUIRED)
	public User logIn(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(user.getUsername()).andUserpasswordEqualTo(user.getUserpassword());
		return userMapper.selectByExample(userExample).get(0);
	}
	@Override
	@Transactional(readOnly=true,propagation= Propagation.REQUIRED)
    public User findByName(String name) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(name);
		List<User> result =  userMapper.selectByExample(userExample);
		User user = null;
		if(result.size()>0){
			user = result.get(0);
		}else{
			user = null;
		}
		return user;
	}

	@Override
	public void modifyUserInfo(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<User> findUserByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsermailEqualTo(email);
		return userMapper.selectByExample(userExample);
	}

}
