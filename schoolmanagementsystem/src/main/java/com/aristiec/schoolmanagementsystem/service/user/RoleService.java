package com.aristiec.schoolmanagementsystem.service.user;

import java.util.List;

import com.aristiec.schoolmanagementsystem.modal.user.Role;

public interface RoleService {
    
 Role createRole(Role role);
 Role getRoleById(Long id);
 Role updateRole(Role role);
 void deleteRole(Long id);
 List<Role> getAllRoles();
 Role getRoleByName(String name);

}
