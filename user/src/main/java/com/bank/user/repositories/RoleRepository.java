package com.bank.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
