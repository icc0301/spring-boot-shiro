package com.xzm.springbootshiro.config;

import com.xzm.springbootshiro.entity.UserEntity;
import com.xzm.springbootshiro.repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.Optional;

// 自定义实现 ShiroRealm，包含认证和授权两大模块

@Component
public class ShiroRealm extends AuthorizingRealm {

    private final UserRepository userRepository;

    public ShiroRealm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("222222222222222222222222222");
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println(username);
        // 通过username获取uid
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        Optional<UserEntity> userInfo = userRepository.findOne(Example.of(userEntity));
        if(!userInfo.isPresent())throw new IncorrectCredentialsException("用户不存在！");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();




        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("111111111111111111111");
        // 获取用户输入的用户名和密码
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        System.out.println(username);
        System.out.println(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        Optional<UserEntity> res = userRepository.findOne(Example.of(userEntity));
        if(!res.isPresent() || !res.get().getPassword().equals(password)){
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        System.out.println(res);
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
