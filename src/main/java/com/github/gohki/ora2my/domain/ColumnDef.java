package com.github.gohki.ora2my.domain;

import lombok.Data;

@Data
public class ColumnDef {
    private String name;
    private String type;
    private String length1;
    private String length2;
    private boolean notNull = false;
    private String _default;
    private String comment;
}
