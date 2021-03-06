package com.framework.yjk.handler.impl;

import com.framework.yjk.DataReaderWriter;
import com.framework.yjk.DbfStatement;
import com.framework.yjk.dbfio.DbfDataReaderWriter;
import com.framework.yjk.handler.AbstractSqlRequestHandler;
import com.framework.yjk.sqlparser.CommonStatementVisitor;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import nl.knaw.dans.common.dbflib.Record;

import java.sql.SQLException;

/**
 * @author yujiakui
 * @version 1.0
 * Email: jkyu@haiyi-info.com
 * date: 2018/9/11 18:25
 * description：插入sql 请求处理
 **/
public class InsertSqlRequestHandler extends AbstractSqlRequestHandler {
    @Override
    protected void doHandle(DbfStatement dbfStatement,
                            DataReaderWriter dataReaderWriter,
                            Statement sqlStatement, String sql, String tableName) throws SQLException {
        Insert insertSql = (Insert) sqlStatement;

        CommonStatementVisitor myStatementVisitor = new CommonStatementVisitor((DbfDataReaderWriter) dataReaderWriter);
        insertSql.accept(myStatementVisitor);

        Record record = new Record(myStatementVisitor.getInsertParseResultMap());
        dataReaderWriter.insertRecord(record);
    }

    @Override
    protected void afterHandler(DbfStatement dbfStatement,
                                DataReaderWriter dataReaderWriter,
                                Statement sqlStatement, String sql) throws SQLException {
        dataReaderWriter.close();
    }
}
