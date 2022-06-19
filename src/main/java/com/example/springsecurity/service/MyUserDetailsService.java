package com.example.springsecurity.service;

import com.example.springsecurity.dao.SysUsersDao;
import com.example.springsecurity.model.SysUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        sysUsers.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(sysUsers.getRoles()));
        return sysUsers;
    }
}
