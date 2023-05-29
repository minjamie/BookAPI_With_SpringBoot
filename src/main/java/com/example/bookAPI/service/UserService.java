package com.example.bookAPI.service;

import com.example.bookAPI.dto.UserDto;
import com.example.bookAPI.entity.Role;
import com.example.bookAPI.entity.User;
import com.example.bookAPI.repository.RoleRepository;
import com.example.bookAPI.repository.UserRepository;
import com.example.bookAPI.security.jwt.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto){
        if(userRepository.findOneWithRolesByEmail(userDto.getEmail()).orElse(null)!=null){
            throw new RuntimeException("existed user with email");
        }

        Role role = roleRepository.findByName("ROLE_USER").get();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .roles(Set.of(role))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles(String email){
        return userRepository.findOneWithRolesByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithRoles(){
        return SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneWithRolesByEmail);
    }
}
