package logr;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static logr.Colorizer.buildColorizedString;
import static logr.StrUtils.formatRowNumber;

@RequiredArgsConstructor
public final class FileProcessor {

    private final Logger logger;

    public void process(final List<File> files, final List<Highlight> highlights) {
        final var start = System.currentTimeMillis();
        final var colorizedLines = LinesCollector.collect(files, highlights);
        colorizedLines
                .forEach(fl -> {
                    final var file = fl.getFile();
                    if (fl.getDirectory()) {
                        logger.fileInfo(String.format("  %s is a directory - skipped", file.getAbsolutePath()));
                        return;
                    }
                    final var fileRows = fl.getLines();

                    final var maxRowNumber = fileRows.stream().max(Comparator.comparing(FileContainer.FileRow::getOriginalRowNumber))
                            .map(FileContainer.FileRow::getOriginalRowNumber)
                            .orElse(0);

                    logger.fileInfo(String.format("  %s (%s)", file.getAbsolutePath(), fileRows.size()));
                    fileRows.forEach(pair -> {
                        final var rowNumber = formatRowNumber(pair.getOriginalRowNumber(), maxRowNumber);
                        final var colorizedString = buildColorizedString(pair.getFragments());
                        logger.info(String.format("%s%s%s %s\n", Logger.ROW_NUMBER, rowNumber, Logger.RESET, colorizedString));
                    });
                });

        final var total = colorizedLines.stream()
                .map(FileContainer::getLines)
                .mapToLong(Collection::size)
                .sum();
        final var end = System.currentTimeMillis();
        logger.summary(String.format("  Total: %s line(s) in %s file(s) in %s"
                , total
                , files.size()
                , Duration.of(end - start, ChronoUnit.MILLIS).toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase()
                )
        );
    }
}
