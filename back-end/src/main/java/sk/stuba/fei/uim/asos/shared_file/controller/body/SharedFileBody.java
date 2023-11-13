package sk.stuba.fei.uim.asos.shared_file.controller.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SharedFileBody {

    private Long toUserId;

    private String fileContent;
}
