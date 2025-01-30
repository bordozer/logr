package logr;

import logr.FileContainer.FileRow;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static logr.Colorizer.buildColorizedString;
import static logr.StrUtils.formatRowNumber;

@RequiredArgsConstructor
public final class FileProcessor {

    private final Logger logger;

    public List<Pair<String, String>> process(final List<File> files, final List<Highlight> highlights) {
        final List<FileContainer> colorizedLines = LinesCollector.collect(files, highlights);
        return colorizedLines.stream()
                .map(fl -> {
                    final File file = fl.getFile();
                    if (fl.getDirectory()) {
                        logger.fileInfo(String.format("  %s is a directory - skipped", file.getAbsolutePath()));
                        return List.<Pair<String, String>>of();
                    }
                    final List<FileRow> fileRows = fl.getLines();

                    final Integer maxRowNumber = fileRows.stream().max(Comparator.comparing(FileRow::getOriginalRowNumber))
                            .map(FileRow::getOriginalRowNumber)
                            .orElse(0);

                    logger.fileInfo(String.format("  %s (%s)", file.getAbsolutePath(), fileRows.size()));
                    return fileRows.stream()
                            .map(pair -> {
                                final String rowNumber = formatRowNumber(pair.getOriginalRowNumber(), maxRowNumber);
                                final String colorizedString = buildColorizedString(pair.getFragments());
                                return Pair.of(rowNumber, colorizedString);
                                //                        logger.info(String.format("%s%s%s %s\n", Logger.ROW_NUMBER, rowNumber, Logger.RESET, colorizedString));
                            })
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
