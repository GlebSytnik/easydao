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

/**
 * Interface for model builder configuration.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public interface IModelBuilderConfig {

    /**
     * Return java type.
     * @param dbType field type string from database
     * @return java class name
     * @throws IllegalArgumentException java class not found for dbType
     */
    public String getJavaType(String dbType) throws IllegalArgumentException;

    /**
     * Sql query for table list, result: TABLE_NAME, COMMENT fields
     * @return SQL query, TABLE_NAME, COMMENT
     */
    public String getSelectForTableList();
    
    /**
     * Sql query for index list of a table, result: UNIQUENESS, INDEX_NAME, TABLE_NAME, COLUMN_NAME fields
     * @return SQL query, UNIQUENESS, INDEX_NAME, TABLE_NAME, COLUMN_NAME
     */
    public String getSelectForIndexList();

    /**
     * Sql query for field list by table name, result: COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENT
     * @return SQL query, COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENT
     */
    public String getSelectForFieldList();

    /**
     * Sql query for primary key field name list, result: COLUMN_NAME
     * @return SQL query, COLUMN_NAME
     */
    public String getSelectForPrimaryKeyFieldNameList();
    
    /**
     * Sql query for checking if a sequence exists, result: the number of sequence found (practically 0 or 1)
     * @return SQL query, SEQUENCE_NAME
     */
    public String getSelectForSequenceCheck();
}
