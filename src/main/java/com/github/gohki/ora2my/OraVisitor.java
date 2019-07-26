package com.github.gohki.ora2my;

import com.github.gohki.ora2my.domain.ColumnDef;
import com.github.gohki.ora2my.domain.TableDef;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.table.CreateTable;

@Slf4j
public class OraVisitor extends StatementVisitorAdapter {


    @Override
    public void visit(CreateTable createTable) {
        super.visit(createTable);

//        log.info("CreateTable");

        TableDef table = new TableDef();
        table.setName(createTable.getTable().getName());

        createTable.getColumnDefinitions().stream().forEachOrdered(s -> {
            ColumnDef col = new ColumnDef();
            col.setName(s.getColumnName());
            col.setType(s.getColDataType().getDataType());
            if (s.getColDataType().getArgumentsStringList().size() > 0) {
                col.setLength1(s.getColDataType().getArgumentsStringList().get(0));
            }
            if (s.getColDataType().getArgumentsStringList().size() > 1) {
                col.setLength2(s.getColDataType().getArgumentsStringList().get(1));
            }
//            col.setNotNull(true);
//            s.getColumnSpecStrings().
//            log.info(col.toString());
            table.getColumnMap().put(col.getName(), col);
            table.getColumnNameList().add(col.getName());
        });

//        log.info(table.toString());

        App.tables.put(table.getName(), table);
    }

    @Override
    public void visit(Comment comment) {
        super.visit(comment);

        String tableName = comment.getColumn().getTable().getName();

        TableDef table = App.tables.get(tableName);
        if (table == null) return;

        table.getColumnMap().get(comment.getColumn().getColumnName()).setComment(comment.getComment().getValue());
    }

    @Override
    public void visit(Alter alter) {
        super.visit(alter);

        // NOT NULL
        //
    }
}
