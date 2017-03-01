package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.domain.User;

/**
 * Created by dima on 28.02.17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
