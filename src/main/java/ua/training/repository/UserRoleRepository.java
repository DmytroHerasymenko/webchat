package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.training.domain.Role;
import ua.training.domain.UserRole;

/**
 * Created by dima on 02.03.17.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT urole FROM ua.training.domain.UserRole urole WHERE urole.role = :role")
    UserRole getUserRoleByRole(@Param("role") Role role);
}
