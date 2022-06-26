package com.example.springsecurity.service;

import com.example.springsecurity.dao.SysUsersDao;
import com.example.springsecurity.model.SysUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUsersDao sysUsersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUsers sysUsers = sysUsersDao.findByUserName(username);
        if (sysUsers == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        sysUsers.setAuthorities(generateAuthoritys(sysUsers.getRoles()));
        return sysUsers;
    }

    private List<GrantedAuthority> generateAuthoritys(String roles){
        List<GrantedAuthority> list = new ArrayList<>();
        if (roles != null && !"".equals(roles)) {
            for (String role : roles.split(",")) {
                list.add(new SimpleGrantedAuthority(role));
            }
        }
        return list;
    }

}
