package logr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class SearchResults {
    private final Map<String, Integer> processedFiles;
    private final List<Pair<String, String>> lines;
}
