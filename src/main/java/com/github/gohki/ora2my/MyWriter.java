package com.github.gohki.ora2my;

import com.github.gohki.ora2my.domain.ColumnDef;
import com.github.gohki.ora2my.domain.TableDef;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * NUMBER(10-19,0) >> BIGINT Long
 * NUMBER(5-9,0) >> INT Integer
 * NUMBER(2-4,0) >> SMALLINT Short
 * NUMBER(1,0) >> TINYINT(1) Boolean
 *
 * NUMBER(m,n) >> DECIMAL(m,n)
 * NUMBER(*,0) >> DECIMAL(38,0)
 *
 * VARCHAR2 >> VARCHAR   字符串长度：500以上>>TEXT
 * DATE >> DATETIME
 * DEFAULT(xxx) >> DEFAULT xxx   current_timestamp() on update current_timestamp(6)
 *
 */
@Slf4j
public class MyWriter {
    public static void output(Map<String, TableDef> tables) {
        tables.keySet().stream().sorted().forEachOrdered(s -> {
            TableDef table = tables.get(s);

            String tableName = table.getName().replaceAll("\"", "`");
            log.info("DROP TABLE IF EXISTS " + tableName + ";");
            log.info("CREATE TABLE IF NOT EXISTS " + tableName + " (");

            table.getColumnNameList().stream().forEachOrdered(c -> {
                ColumnDef col = table.getColumnMap().get(c);
                String columnName = col.getName().replaceAll("\"", "`");
                StringBuilder sb = new StringBuilder();
                sb.append(columnName);
                sb.append(" ");
                sb.append(col.getType());
                if (StringUtils.isNotBlank(col.getLength1())) {
                    sb.append("(");
                    sb.append(col.getLength1());
                    if (StringUtils.isNotBlank(col.getLength2())) {
                        sb.append(",");
                        sb.append(col.getLength2());
                    }
                    sb.append(")");
                }
                if (col.isNotNull()) {
                    sb.append(" NOT NULL");
                }
                if (StringUtils.isNotBlank(col.getComment())) {
                    sb.append(" COMMENT '");
                    sb.append(col.getComment());
                    sb.append("'");
                }
                if (c != table.getColumnNameList().get(table.getColumnNameList().size() - 1)) {
                    sb.append(",");
                }

                log.info(sb.toString());
            });

            log.info(");");
        });
    }
}
