package sk.stuba.fei.uim.asos.file_encryptor.service.utils;

public class FileContentIntegrityViolatedException extends Exception {

    public FileContentIntegrityViolatedException() {
        super("Integrity of file content was violated.");
    }

}
