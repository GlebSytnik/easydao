/*
 * The MIT License
 *
 * Copyright 2013 Vanio Informatika Kft.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hu.vanio.easydao.modelbuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * PostgreSQL 9 configuration.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class PostgreSql9ModelBuilderConfig extends ModelBuilderConfig implements IModelBuilderConfig {

    /* Sql query for table list, result: TABLE_NAME, COMMENTS fields */
    final String selectForTableList = "select c.relname as TABLE_NAME, obj_description(c.oid) as COMMENTS from pg_catalog.pg_class c"
            + " where c.relname like '%' and c.relname not like 'sql_%' and c.relname not like 'pg_%' and c.relkind = 'r' order by TABLE_NAME;";
    /* Sql query for field list by table name, result: COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENTS */
    final String selectForFieldList = "select a.attname as COLUMN_NAME,"
            + " 0 as VIRTUAL,"
            + " pg_catalog.format_type(a.atttypid, a.atttypmod) as DATA_TYPE,"
            + " a.attnotnull as NOT_NULL,"
            + " a.attndims as ARRAY_DIM_SIZE,"
            + " a.atthasdef as HAS_DEFAULT_VALUE,"
            + " col_description(c.oid, a.attnum) as COMMENTS"
            + " from pg_catalog.pg_class c, pg_catalog.pg_attribute a"
            + " where c.relname = ?"
            + " and c.relkind = 'r'"
            + " and a.attrelid = c.oid"
            + " and a.attnum > 0"
            + " and a.attisdropped is false"
            + " order by a.attnum";
    /* Sql query for primary key field name list, result: COLUMN_NAME */
    final String selectForPrimaryKeyFieldNameList = "select a.attname as COLUMN_NAME "
            + "from pg_catalog.pg_constraint cn, pg_catalog.pg_attribute a, pg_catalog.pg_class c "
            + "where c.relname = ?  "
            + "  and c.relkind = 'r' "
            + "  and a.attrelid = c.oid "
            + "  and a.attnum > 0  "
            + "  and a.attisdropped is false "
            + "  and cn.conrelid = c.oid and cn.contype = 'p' "
            + "  and a.attnum = any(cn.conkey)";

    final String selectForIndexList = 
            "SELECT idx.indisunique as UNIQUENESS,\n" +
            "       i.relname as INDEX_NAME, " +
            "       idx.indrelid::regclass::text as TABLE_NAME, " +
            "       (SELECT array_to_string( " +
            "         ARRAY(\n" +
            "           SELECT pg_get_indexdef(idx.indexrelid, k + 1, true) " +
            "           FROM generate_subscripts(idx.indkey, 1) as k " +
            "           ORDER BY k " +
            "       ), ',')) as COLUMN_NAMES " +
            "FROM   pg_index as idx " +
            "JOIN   pg_class as i ON i.oid = idx.indexrelid " +
            "JOIN   pg_namespace as ns ON ns.oid = i.relnamespace AND ns.nspname = ANY(current_schemas(false)) " +
            "WHERE  idx.indrelid::regclass::text = ? " +
            "order by TABLE_NAME, idx.indkey";
    
    final String selectForSequenceCheck = "SELECT COUNT(relname) FROM pg_class WHERE relkind = 'S' AND relname = ?";
    
    /* Data type mapping: database -> java */
    public static final Map<String, String> JAVA_TYPE_MAP = new HashMap<>();

    /* see: http://www.postgresql.org/docs/current/static/datatype.html */
    static {
        JAVA_TYPE_MAP.put("boolean", Boolean.class.getName());
        JAVA_TYPE_MAP.put("boolean\\[\\]", boolean[].class.getName());
        JAVA_TYPE_MAP.put("bytea", byte[].class.getName());
        JAVA_TYPE_MAP.put("character", String.class.getName());
        JAVA_TYPE_MAP.put("character\\([\\d]+\\)", String.class.getName());
        JAVA_TYPE_MAP.put("character\\([\\d]+\\)\\[\\]", String[].class.getName());
        JAVA_TYPE_MAP.put("character\\[\\]", String[].class.getName());
        JAVA_TYPE_MAP.put("character varying", String.class.getName());
        JAVA_TYPE_MAP.put("character varying\\([\\d]+\\)", String.class.getName());
        JAVA_TYPE_MAP.put("character varying\\([\\d]+\\)\\[\\]", String[].class.getName());
        JAVA_TYPE_MAP.put("date", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("double precision|float8", Double.class.getName());
        JAVA_TYPE_MAP.put("integer|int|int4|smallint|smallserial|serial", Integer.class.getName());
        JAVA_TYPE_MAP.put("bigint|int8|bigserial|money", Long.class.getName());
        JAVA_TYPE_MAP.put("json", String.class.getName());

        JAVA_TYPE_MAP.put("numeric", Integer.class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1-9]\\)", Integer.class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1-9],[0]\\)", Integer.class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1][0-8]\\)", Long.class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1][0-8],[0]\\)", Long.class.getName());
        JAVA_TYPE_MAP.put("numeric\\((19|[2-9]\\d|\\d{3,})\\)", String.class.getName());
        JAVA_TYPE_MAP.put("numeric\\((19|[2-9]\\d|\\d{3,}),[0]\\)", String.class.getName());
        JAVA_TYPE_MAP.put("numeric\\([\\d]+,[1-9]+\\)", Double.class.getName());

        JAVA_TYPE_MAP.put("numeric\\[\\]", int[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1-9]\\)\\[\\]", int[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1-9],[0]\\)\\[\\]", int[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1][0-8]\\)\\[\\]", long[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\([1][0-8],[0]\\)\\[\\]", long[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\((19|[2-9]\\d|\\d{3,})\\)\\[\\]", String[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\((19|[2-9]\\d|\\d{3,}),[0]\\)\\[\\]", String[].class.getName());
        JAVA_TYPE_MAP.put("numeric\\([\\d]+,[1-9]+\\)\\[\\]", double[].class.getName());

        JAVA_TYPE_MAP.put("timestamp without time zone", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp with time zone", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("text", String.class.getName());
        JAVA_TYPE_MAP.put("uuid", String.class.getName());
        JAVA_TYPE_MAP.put("xml", String.class.getName());
    }

    public PostgreSql9ModelBuilderConfig() {
    }

    @Override
    public String getSelectForTableList() {
        return this.selectForTableList;
    }
    
     @Override
    public String getSelectForIndexList() {
        return this.selectForIndexList;
    }

    @Override
    public String getSelectForFieldList() {
        return this.selectForFieldList;
    }

    @Override
    public String getSelectForPrimaryKeyFieldNameList() {
        return this.selectForPrimaryKeyFieldNameList;
    }

    @Override
    public String getSelectForSequenceCheck() {
        return this.selectForSequenceCheck;
    }
    
    @Override
    public String getJavaType(String dbType) throws IllegalArgumentException {
        return convertToJavaType(JAVA_TYPE_MAP, dbType);
    }

}
