package sk.stuba.fei.uim.asos.shared_file.controller.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharedFilesBody {

    private List<String> filesFrom;

    private List<String> filesContents;
}
