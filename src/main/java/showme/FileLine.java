package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
    public class FileLine {
    private final List<FileLineFragment> fragments;
}
