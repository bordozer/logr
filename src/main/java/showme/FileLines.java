package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FileLines {
    private final File file;
    private final List<Pair<Integer, String>> lines;
}
