package com.smartprocessrefusao.erprefusao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.projections.UserDetailsProjection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = """
			SELECT tb_user.username AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
			FROM tb_user
			INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
			INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
			WHERE tb_user.username = :username
		""")
	List<UserDetailsProjection> searchUserAndRolesByUsername(@Param("username") String username);
	
	 boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);

}
