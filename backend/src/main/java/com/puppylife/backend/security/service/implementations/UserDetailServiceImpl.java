package com.puppylife.backend.security.service.implementations;

import com.puppylife.backend.security.persistance.entity.UserEntity;
import com.puppylife.backend.security.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByUsernameOrEmail(username, username).orElseThrow(
                () -> new UsernameNotFoundException("User no found by the username/email")
        );

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        userEntity.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
        );

        userEntity.getRoles()
                .stream()
                .flatMap(roleEntity -> roleEntity.getPermissions().stream())
                .forEach( permissionEntity ->
                        authorities.add(new SimpleGrantedAuthority(permissionEntity.getName().name()))
                );
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }

    public void login() {

    }

    public void register() {

    }
}
