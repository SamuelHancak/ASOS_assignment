package sk.stuba.fei.uim.asos.file_encryptor.controller.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileBody {

    private String key;

    private String inputContent;

    private String outputContent;

    private Boolean isFileUnchanged;
}
