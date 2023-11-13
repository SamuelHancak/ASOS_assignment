package sk.stuba.fei.uim.asos.file_encryptor.controller.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AsymmetricKeyBody {

    private String publicKey;

    private String privateKey;
}
