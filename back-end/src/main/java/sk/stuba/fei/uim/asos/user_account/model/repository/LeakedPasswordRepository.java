package sk.stuba.fei.uim.asos.user_account.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.asos.user_account.model.LeakedPassword;

import java.util.List;

@Repository
public interface LeakedPasswordRepository extends JpaRepository<LeakedPassword, Long> {

    List<LeakedPassword> findByPassword(String password);
}
