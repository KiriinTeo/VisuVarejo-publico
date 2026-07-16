package io.github.kiriinteo.visuvarejo.core.domain;

import java.time.LocalDate;
import java.io.Serializable;

public class Period implements Serializable {

    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Invalid period");
        }

        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
