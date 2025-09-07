package com.aristiec.schoolmanagementsystem.serviceImpl.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.modal.user.Role;
import com.aristiec.schoolmanagementsystem.repository.user.RoleRepository;
import com.aristiec.schoolmanagementsystem.service.user.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    
}
