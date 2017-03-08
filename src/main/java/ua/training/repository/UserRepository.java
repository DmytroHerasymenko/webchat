package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.domain.User;

/**
 * Created by dima on 28.02.17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM ua.training.domain.User user WHERE user.login = :login")
    User getUserByLogin(@Param("login") String login);
}
