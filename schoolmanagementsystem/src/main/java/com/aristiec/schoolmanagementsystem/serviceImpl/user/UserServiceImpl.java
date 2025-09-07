package com.aristiec.schoolmanagementsystem.serviceImpl.user;

import com.aristiec.schoolmanagementsystem.dto.LoginRequest;
import com.aristiec.schoolmanagementsystem.modal.user.Role;
import com.aristiec.schoolmanagementsystem.modal.user.User;
import com.aristiec.schoolmanagementsystem.modal.user.UserDTO;
import com.aristiec.schoolmanagementsystem.utils.JwtUtil;
import com.aristiec.schoolmanagementsystem.repository.UserRepo;
import com.aristiec.schoolmanagementsystem.repository.user.RoleRepository;
import com.aristiec.schoolmanagementsystem.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNo(userDTO.getMobileNo());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true); // explicitly set active to true

        Set<Role> userRoles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            userRoles.add(role);
        }

        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, UserDTO updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMobileNo(updatedUser.getMobileNo());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        Set<Role> userRoles = new HashSet<>();
        for (String roleName : updatedUser.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            userRoles.add(role);
        }
        existingUser.setRoles(userRoles);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = getUserById(id);
        userRepository.delete(existingUser);
    }

    @Override
    public String loginUser(LoginRequest user) {
        User u = userRepository.findByUsername(user.getUserName());
        if (Boolean.FALSE.equals(u.getActive())) {
            throw new RuntimeException("User account is not active.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(u.getUsername(), user.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return jwtUtil.generateToken(userDetails.getUsername(), roles);
    }

    @Override
    @Transactional
    public void blockUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}
