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
package hu.vanio.easydao.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Database java representation.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Database {

    /* database name (dataSource) */
    private String name;
    /* model creation date */
    private Timestamp modelCreationDate = new Timestamp(System.currentTimeMillis());
    /** Database's tables */
    private List<Table> tableList;

    /**
     * Create database object with database name.
     * @param name database name. It will be set for @Qualifier(name=databaseName) in Dao classes as DataSource name!
     */
    public Database(String name) {
        this.name = name;
    }

    /**
     * Add table to database model.
     * @param table table model
     */
    public void addTable(Table table) {
        if (tableList == null) {
            tableList = new ArrayList<>();
        }
        tableList.add(table);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the modelCreationDate
     */
    public Timestamp getModelCreationDate() {
        return modelCreationDate;
    }

    /**
     * @return the tableList
     */
    public List<Table> getTableList() {
        return tableList;
    }

}
