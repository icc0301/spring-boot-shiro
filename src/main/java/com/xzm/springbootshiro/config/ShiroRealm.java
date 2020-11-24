package com.xzm.springbootshiro.config;

import com.xzm.springbootshiro.entity.PermissionEntity;
import com.xzm.springbootshiro.entity.RoleEntity;
import com.xzm.springbootshiro.entity.UserEntity;
import com.xzm.springbootshiro.repository.PermissionRepository;
import com.xzm.springbootshiro.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public ShiroRealm(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("222222222222222222222222222");
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println(username);
        // 通过username获取userRoleEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        Optional<UserEntity> userInfo = userRepository.findOne(Example.of(userEntity));
        if(!userInfo.isPresent())throw new IncorrectCredentialsException("用户不存在！");
        System.out.println(userInfo.get().getUserRoleEntity());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(userInfo.get().getUserRoleEntity().size() > 0){
            userInfo.get().getUserRoleEntity().forEach(roleInfo->{
                // 这里把角色ID当成名字存入了
                authorizationInfo.addRole(roleInfo.getRole_id().toString());
                // addStringPermission代码部分......
                // 获取权限名,第一步通过角色ID去获取权限List
                System.out.println(roleInfo.getRole_id());
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setId(roleInfo.getRole_id());
                Optional<RoleEntity> roleOne = roleRepository.findOne(Example.of(roleEntity));
                if(!roleOne.isPresent())throw new IncorrectCredentialsException("角色不存在！");
                System.out.println(roleOne.get().getRolePermissionEntities());
                roleOne.get().getRolePermissionEntities().forEach(val->{
                    PermissionEntity permissionEntity = new PermissionEntity();
                    permissionEntity.setId(val.getPermission_id());
                    Optional<PermissionEntity> permissionInfo = permissionRepository.findOne(Example.of(permissionEntity));
                    permissionInfo.ifPresent(entity -> {
                        System.out.println("--------------------------------------");
                        System.out.println(entity.getName());
                        authorizationInfo.addStringPermission(entity.getName());
                    });
                });

            });
        }
        System.out.println("用户权限:"+ authorizationInfo);
        return authorizationInfo;
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
