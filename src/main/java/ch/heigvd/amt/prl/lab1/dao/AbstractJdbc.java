package ch.heigvd.amt.prl.lab1.dao;

import ch.heigvd.amt.prl.lab1.dao.exceptions.FilterValidationException;
import ch.heigvd.amt.prl.lab1.dao.exceptions.NoResultException;
import ch.heigvd.amt.prl.lab1.dao.exceptions.NonUniqueResultException;
import ch.heigvd.amt.prl.lab1.dao.exceptions.QueryException;
import ch.heigvd.amt.prl.lab1.dao.filtering.Filter;
import ch.heigvd.amt.prl.lab1.dao.filtering.FilterUtils;
import ch.heigvd.amt.prl.lab1.models.IModel;
import ch.heigvd.amt.prl.lab1.utils.StringUtils;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * An abstraction to manipulate the database
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 * 
 * @param <T> The model type managed by the class
 */
public abstract class AbstractJdbc<T extends IModel> {
  @Resource(lookup = "java:/jdbc/amt")
  private DataSource dataSource;

  /**
   * Execute an insert query with the given parameters.
   * 
   * Do not use this method for bulk insert.
   * 
   * @param query The query
   * @param params The parameters of the query
   * @return The id generated for the record inserted
   */
  protected long executeInsertQuery(String query, Object ... params) {
    // Setup the connection and prepare the query
    try (Connection connection = dataSource.getConnection(); 
      PreparedStatement stmt = 
        configureStatement(connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), params)) {
      
      // Check there is one row inserted
      if (stmt.executeUpdate() == 1) {
        // Check we retrieved the inserted id
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
          return generatedKeys.getLong(1);
        }
        else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }
      else {
        throw new SQLException("Creating user failed, no rows affected.");
      }
    } catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }

  /**
   * Execute a write query (insert/update/delete) with the given parameters.
   * 
   * You can use this query for single row insert/update/delete or bulk operations.
   * 
   * In case of insert, you will not be able to retrieve the inserted IDs.
   * 
   * @param query The query
   * @param params The parameters of the query
   * @return The number of records touched by the write query
   */
  protected int executeWriteQuery(String query, Object ... params) {
    // Setup the connection and prepare the query
    try (Connection connection = dataSource.getConnection(); 
      PreparedStatement stmt = configureStatement(connection.prepareStatement(query), params)) {
      return stmt.executeUpdate();
    } catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Execute a read query (select) with the given parameters.
   * 
   * The result set is expected to return 0 or more records.
   * 
   * @param query The query
   * @param params The parameters of the query
   * @return The result set from the database
   */
  protected List<T> executeReadQuery(String query, Object ... params) {
    // Setup the connection and prepare the query
    try (Connection connection = dataSource.getConnection(); 
      PreparedStatement stmt = configureStatement(connection.prepareStatement(query), params)) {
      return readList(stmt.executeQuery());
    } catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Execute a read query (select) with the given parameters and an additional dynamic filter.
   * 
   * The result set is expected to return 0 or more records.
   * 
   * @param query The query
   * @param filter the filter to add to the query
   * @param params The parameters of the query
   * @return The result set from the database
   */
  protected List<T> executeReadQuery(String query, Filter filter, Object ... params) {
    // We enforce the presence of a filter when this method is used
    if (filter == null) {
      throw new IllegalArgumentException("The filter cannot be null");
    }
    
    // Check the filter
    boolean absentValue = StringUtils.isEmpty(filter.getValue());
    boolean invalidFieldName = !FilterUtils.isFilterNameValid(filter);
    
    if (absentValue || invalidFieldName) {
      throw new FilterValidationException(invalidFieldName, absentValue, filter);
    }
    
    // Make sure we add WHERE or AND in the appropriate situation
    String completeQuery = query.toLowerCase();
    if (completeQuery.contains("where")) {
      completeQuery += " and ";
    }
    else {
      completeQuery += " where ";
    }
    
    // Add the filter through the prepared statement mechanism
    completeQuery +=  FilterUtils.getCorrespondingDbFieldName(filter) + " like ?";

    // Setup the connection and prepare the query
    try (Connection connection = dataSource.getConnection(); 
      PreparedStatement stmt = configureStatement(connection.prepareStatement(completeQuery), params)) {

      // We set the value of the filter (% is a wildcard in SQL not only MySQL)
      // Notice: the +1 is because the statement parameters starts at index 1
      stmt.setString(params.length + 1, "%" + filter.getValue() + "%");
      
      // Execute the query and return the results
      return readList(stmt.executeQuery());
    } catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Execute a read query (select) with the given parameters.
   * 
   * The result set is expected to have only one record returned.
   * 
   * @param query The query
   * @param params The parameters of the query
   * @return The result set from the database
   */
  protected T executeReadOneQuery(String query, Object ... params) {
    // Setup the connection and prepare the query
    try (Connection connection = dataSource.getConnection(); 
      PreparedStatement stmt = configureStatement(connection.prepareStatement(query), params)) {
      return readOne(stmt.executeQuery());
    } catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Set the ID value to a model
   * 
   * @param model The model supposed to contain an ID field
   * @param id The ID value to set to the model
   */
  protected void setId(Object model, long id) {
    // We use the Java reflexion to set the id field as it is not exposed (and should not be exposed).
    try {
      // Retrieve the id field on the object class
      Field idField = model.getClass().getDeclaredField("id");

      // Keep track of the field accessibility
      boolean accessible = idField.isAccessible();
      
      // Make it accessible if required
      if (!accessible) {
        idField.setAccessible(true);
      }
      
      // Set the value to the field
      idField.setLong(model, id);
      
      // Make the field not accessible again if required
      if (!accessible) {
        idField.setAccessible(false);
      }
    }
    catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
      throw new QueryException(e);
    }
  }
  
  /**
   * Set the parameters to a prepared statement
   * 
   * @param stmt The statement to set the parameters
   * @param params The parameters to set
   * @return The prepared statement parameterized
   */
  private PreparedStatement configureStatement(PreparedStatement stmt, Object ... params) throws SQLException {
    // We set the parameters in the received order
    for (int i = 1; i <= params.length; i++) {
      // Notice: the -1 is because the statement parameters starts at index 1
      stmt.setObject(i, params[i - 1]);
    }
    
    return stmt;
  }

  /**
   * Count the number of rows
   * 
   * @param resultSet The results from the database
   * @return The number of rows in the results set
   * @throws SQLException When there is a problem with the query or the database
   */
  private int count(ResultSet resultSet) throws SQLException {
    int count = 0;
    
    // Try to retrieve the number of rows
    if (resultSet.last()) {
      count = resultSet.getRow();
      resultSet.beforeFirst();
    }
    
    return count;
  }
  
  /**
   * Read a list of results from a read query
   * 
   * @param resultSet The results from the database
   * @return The list of records
   */
  private List<T> readList(ResultSet resultSet) {
    try {
      return readResults(resultSet);
    }
    catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Read exactly one result from a result set. It is especially well suited to be 
   * used for queries that retrieves a record from a primary key for example.
   * 
   * @param resultSet The result set from the database query execution
   * @return The record retrieved from the query
   * @throws NonUniqueResultException When more than one result
   * @throws NoResultException When no result
   */
  private T readOne(ResultSet resultSet) throws NonUniqueResultException, NoResultException {
    try {
      int count = count(resultSet);
      
      /**
       * We expect to have exactly one result. If there is more or less than one result we throw appropriate
       * exception. Otherwise, we prepare the model from the result set (first row).
       */
      if (count > 1) {
        throw new NonUniqueResultException("More than one result was retrieved from the database");
      }
      else if (count == 0) {
        throw new NoResultException("Expected to get one result and there was no result form the database");
      }
      else {
        resultSet.next();
        T record = readResult(resultSet);
        resultSet.beforeFirst(); // We reset the cursor
        return record;
      }
    }
    catch (SQLException sqle) {
      throw new QueryException(sqle);
    }
  }
  
  /**
   * Read the results from the database and prepare a list of records from these results
   * 
   * @param resultSet The results from the database
   * @return The list of records
   * @throws SQLException Any issue with the query
   */
  abstract protected List<T> readResults(ResultSet resultSet) throws SQLException;
  
  /**
   * Read the results from the database and prepare a record from these results.
   * 
   * It is supposed that the result set is already positioned on a valid row.
   * 
   * @param resultSet The results from the database
   * @return The record
   * @throws SQLException Any issue with the query
   */
  abstract protected T readResult(ResultSet resultSet) throws SQLException;
}
