package com.example.demo.service;

import com.example.demo.entity.Authority;
import com.example.demo.entity.User;
import com.example.demo.enumeration.UserAuthority;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Authority adminAuthority = new Authority(UserAuthority.ADMIN);
        adminAuthority = authorityRepository.save(adminAuthority);

        Authority userAuthority = new Authority(UserAuthority.USER);
        userAuthority = authorityRepository.save(userAuthority);

        User admin = new User();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setAuthorities(Collections.singletonList(adminAuthority));
        saveUser(admin);

        User user = new User();
        user.setId(2);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setAuthorities(Collections.singletonList(userAuthority));
        saveUser(user);
    }

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
    }

    public UserDetails saveUser(User user) {
        if (userRepository.existsById(user.getId())) {
            return null;
        }
        return userRepository.save(user);
    }
}
