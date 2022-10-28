package com.orient.CrudOperation.service;

import com.orient.CrudOperation.model.Account;
import com.orient.CrudOperation.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository repository;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account=repository.findByEmail(email);
        if(account!=null){
            User.UserBuilder builder=org.springframework.security.core.userdetails.User.withUsername(email);

            builder.disabled(false);
            builder.password(account.getPassword());

            String[] authoritiesArr=new String[]{"ADMIN","USER","ROLE_USER"};
            builder.authorities(authoritiesArr);

            return builder.build();
        }else {
            throw new UsernameNotFoundException("Account Not Found");
        }
    }
}
