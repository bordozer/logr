package logr;

import com.google.common.collect.ImmutableList;
import logr.model.FileContainer;
import logr.model.FileContainer.FileRow;
import logr.model.Highlight;
import logr.model.Parameters;
import logr.model.SearchResults;
import logr.utils.Logger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;
import static logr.Colorizer.buildColorizedString;
import static logr.utils.StrUtils.formatRowNumber;

@RequiredArgsConstructor
public final class FileProcessor {

    private final Parameters parameters;
    private final Logger logger;

    public SearchResults process(final List<File> files, final List<Highlight> highlights) {
        final List<FileContainer> colorizedLines = LinesCollector.collect(files, highlights, parameters);
        final Map<String, Integer> processedFiles = newHashMap();
        final List<Pair<String, String>> collect = getCollect(colorizedLines, processedFiles);
        return new SearchResults(processedFiles, collect);
    }

    private List<Pair<String, String>> getCollect(final List<FileContainer> colorizedLines, final Map<String, Integer> processedFiles) {
        return colorizedLines.stream()
                .map(fl -> {
                    final File file = fl.getFile();
                    if (fl.getDirectory()) {
                        logger.fileInfo(String.format("  %s is a directory - skipped", file.getAbsolutePath()));
                        return List.<Pair<String, String>>of();
                    }
                    final List<FileRow> fileRows = fl.getLines();

                    final Integer maxRowNumber = fileRows.stream().max(Comparator.comparing(FileRow::originalRowNumber))
                            .map(FileRow::originalRowNumber)
                            .orElse(0);

//                    logger.fileInfo(String.format("  %s (%s)", file.getAbsolutePath(), fileRows.size()));
                    processedFiles.put(file.getAbsolutePath(), fileRows.size());
                    final List<Pair<String, String>> list = fileRows.stream()
                            .map(pair -> {
                                final String rowNumber = formatRowNumber(pair.originalRowNumber(), maxRowNumber);
                                final String colorizedString = buildColorizedString(pair.fragments());
                                return Pair.of(rowNumber, colorizedString);
                            })
                            .toList();
                    return ImmutableList.<Pair<String, String>>builder()
                            .add(Pair.of(StringUtils.EMPTY, "%s%s%s".formatted(Logger.SYSTEM2, fl.getFile().getPath(), Logger.RESET)))
                            .addAll(list)
                            .build();
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
