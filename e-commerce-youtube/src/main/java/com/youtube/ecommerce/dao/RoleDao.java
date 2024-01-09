package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE role_id IN (SELECT * FROM role WHERE role_name = :roleName)", nativeQuery = true)
    void deleteByRoleName(@Param("roleName") String roleName);
}
