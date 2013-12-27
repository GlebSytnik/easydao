// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
package ${e.packageOfJavaDao};

import ${e.packageOfJavaModel}.${t.javaName};

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ${t.javaName}${e.daoSuffix}.
 * <br>${t.comment}
 * <br>Generated from ${t.dbName} database table.
 * <br>Created on: ${e.database.modelCreationDate}
 * <br>Database name: ${e.database.name}
 * <br>Generated by ${appname} v${appversion}
 */
@Repository
<#if t.compositePk>
public class ${t.javaName}${e.daoSuffix} implements Dao<${t.javaName}, ${t.javaName}.Pk>, RowMapper<${t.javaName}> {
<#else>
public class ${t.javaName}${e.daoSuffix} implements Dao<${t.javaName}, ${t.pkField.javaTypeAsString}>, RowMapper<${t.javaName}> {
</#if>

    static final protected String SELECTED_FIELDS = "<#list t.fieldList as field>${field.dbName}<#if field_has_next>, </#if></#list>";

    protected SimpleJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("${e.database.name}") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public ${t.javaName} mapRow(ResultSet rs, int rowNum) throws SQLException {
        String tmp;
        <#list t.fieldList as field>
        <#if field.readAsString>
        ${field.javaTypeAsString} ${field.javaName} = (tmp = rs.getString("${field.dbName}")) != null ? new ${field.javaTypeAsString}(tmp) : null;
        <#else>
        ${field.javaTypeAsString} ${field.javaName} = rs.get${field.javaTypeAsString}("${field.dbName}");
        </#if>
        </#list>
        return new ${t.javaName} (<#list t.fieldList as field>${field.javaName}<#if field_has_next>, </#if></#list>);
    }

    /**
     * Reads a domain object with the specified primary key from the datastore 
     <#list t.pkFields as field>
     * @param ${field.javaName} ${field.comment}
     </#list>
     * @return ${t.javaName} instance
     */
    @Override
    <#if t.compositePk>
    public ${t.javaName} read(${t.javaName}.Pk pk) {
    <#else>
    public ${t.javaName} read(${t.pkField.javaTypeAsString} ${t.pkField.javaName}) {
    </#if>
        <#if t.compositePk>
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where <#list t.pkFields as field>${field.dbName} = ?<#if field_has_next> and </#if></#list>";
        ${t.javaName} retVal = this.jdbcTemplate.queryForObject(query, this, <#list t.pkFields as field> pk.get${field.javaName?cap_first}()<#if field_has_next>, </#if></#list>);
        <#else>
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where ${t.pkField.dbName} = ?";
        ${t.javaName} retVal = this.jdbcTemplate.queryForObject(query, this, ${t.pkField.javaName});
        </#if>
        return retVal;
    }

    /**
     * Reads all instances of the domain object from the datastore
     * @return All instances of the domain object
     */
    @Override
    public List<${t.javaName}> readAll() {
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName}";
        List<${t.javaName}> retVal = this.jdbcTemplate.query(query, this);
        return retVal;
    }

}
