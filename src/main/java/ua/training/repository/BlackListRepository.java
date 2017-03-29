package ua.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.training.domain.BlackList;

/**
 * Created by dima on 28.03.17.
 */
@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long>, BlackListRepositoryCustom {
}
