package utils;

import java.util.List;

public class ListAndVersion<T> {

    final private List<T> entries;
    final private int version;

    public ListAndVersion(List<T> entries, int version) {
        this.entries = entries;
        this.version = version;
    }
}
