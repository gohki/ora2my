package com.github.gohki.ora2my;

import com.github.gohki.ora2my.domain.TableDef;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statements;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class App {
    public static Map<String, TableDef> tables = new HashMap<>();

    public static void main(String[] args) throws JSQLParserException {

        String sql = "";

        Statements statements = CCJSqlParserUtil.parseStatements(sql);
        statements.accept(new OraVisitor());

        MyWriter.output(tables);
    }
}
