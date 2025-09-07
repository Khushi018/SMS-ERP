package com.aristiec.schoolmanagementsystem.repository.user;

import com.aristiec.schoolmanagementsystem.modal.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
        Role findByName(String name);
}