package com.github.gohki.ora2my.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TableDef {
    private String name;
    private List<String> columnNameList = new ArrayList<>();
    private Map<String, ColumnDef> columnMap = new HashMap<>();
    private String primaryKey;
}
