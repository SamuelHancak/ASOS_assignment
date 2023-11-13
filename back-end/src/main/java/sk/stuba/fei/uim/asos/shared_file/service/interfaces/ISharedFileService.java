package sk.stuba.fei.uim.asos.shared_file.service.interfaces;

import sk.stuba.fei.uim.asos.shared_file.model.SharedFile;

import java.util.List;

public interface ISharedFileService {

    List<SharedFile> getSharedFilesTo(Long userId, String password);

    Boolean postSharedFile(Long sharedFromUserId, String sharedFromPassword, String fileContent, Long sharedToUserId);
}
