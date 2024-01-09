package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Role;
import com.youtube.ecommerce.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = :userId", nativeQuery = true)
    void findRolesByUserId(@Param("userId") Long userId);

    // Thêm một phương thức xóa Role theo Id (để sử dụng trong trường hợp cần)
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE role_id = :roleId", nativeQuery = true)
    void deleteRoleById(@Param("roleId") Long roleId);

}
