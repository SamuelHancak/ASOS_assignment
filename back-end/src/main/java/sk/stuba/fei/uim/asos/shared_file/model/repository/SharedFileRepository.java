package sk.stuba.fei.uim.asos.shared_file.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.asos.shared_file.model.SharedFile;

import java.util.List;

@Repository
public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {

    List<SharedFile> findBySharedToUserId(Long sharedToUserId);
}
