package sk.stuba.fei.uim.asos.shared_file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.asos.shared_file.controller.body.PostBody;
import sk.stuba.fei.uim.asos.shared_file.controller.body.SharedFileBody;
import sk.stuba.fei.uim.asos.shared_file.controller.body.SharedFilesBody;
import sk.stuba.fei.uim.asos.shared_file.model.SharedFile;
import sk.stuba.fei.uim.asos.shared_file.service.interfaces.ISharedFileService;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("zadanie-ehealth-api/asos/sharedFile")
//@CrossOrigin("http://localhost:63343")
public class SharedFileController {

    @Autowired
    private ISharedFileService sharedFileService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("getFiles/{userId}/{token}")
    public ResponseEntity<SharedFilesBody> getFiles(@PathVariable("userId") Long userId,
                                                    @PathVariable("token") String token) {
        SharedFilesBody sharedFilesBody = new SharedFilesBody(new ArrayList<>(), new ArrayList<>());
        List<SharedFile> sharedFiles = sharedFileService.getSharedFilesTo(userId, token);
        if(sharedFiles != null) {
            for(SharedFile tmp : sharedFiles) {
                sharedFilesBody.getFilesContents().add(tmp.getFileContent());
                sharedFilesBody.getFilesFrom().add(userRepository.findById(tmp.getSharedFromUserId()).get().getEmail());
            }
        }
        return new ResponseEntity<SharedFilesBody>(sharedFilesBody, HttpStatus.OK);
    }

    @PostMapping("postFile/{userId}/{token}")
    public ResponseEntity<PostBody> postFile(@PathVariable("userId") Long userId,
                                             @PathVariable("token") String token,
                                             @RequestBody SharedFileBody sharedFileBody) {
        PostBody postBody = new PostBody();
        Boolean postedSuccessful = sharedFileService.postSharedFile(userId, token, sharedFileBody.getFileContent(), sharedFileBody.getToUserId());
        postBody.setFilePostSuccessful(postedSuccessful);
        return new ResponseEntity<PostBody>(postBody, HttpStatus.OK);
    }
}
