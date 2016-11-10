package ch.heigvd.amt.prl.lab1.dao;

import ch.heigvd.amt.prl.lab1.dao.exceptions.NoResultException;
import ch.heigvd.amt.prl.lab1.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the Database operations for a User
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Stateless
public class UserDao extends AbstractJdbc<User> implements IUserDao {
  /**
   * Different queries used to perform the CRUD operations on the users
   */
  private static final String QUERY_INSERT = 
    "INSERT INTO users (username, hashed_password, first_name, last_name) VALUES (?, ?, ?, ?)";

  private static final String QUERY_UPDATE =
    "UPDATE users SET username = ?, hashed_password = ?, first_name = ?, last_name = ? WHERE id = ?";
  
  private static final String QUERY_DELETE = "DELETE users WHERE id = ?";
  private static final String QUERY_FINDALL = "SELECT * FROM users";
  private static final String QUERY_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
  private static final String QUERY_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
  
  @Override
  public User create(User user) {
    long id = executeInsertQuery(
      QUERY_INSERT, 
      user.getUsername(), 
      user.getHashedPassword(), 
      user.getFirstname(), 
      user.getLastname()
    );

    setId(user, id);
    
    return user;
  }
  
  @Override
  public boolean update(User user) {
    return executeWriteQuery(
      QUERY_UPDATE,
      user.getUsername(),
      user.getHashedPassword(),
      user.getFirstname(),
      user.getLastname(),
      user.getId()
    ) == 1;
  }

  @Override
  public boolean delete(User user) {
    return executeWriteQuery(QUERY_DELETE, user.getId()) == 1;
  }
  
  @Override
  public List<User> findAll() {
    return executeReadQuery(QUERY_FINDALL);
  }

  @Override
  public User find(long id) {
    return executeReadOneQuery(QUERY_FIND_BY_ID, id);
  }

  @Override
  public User find(String username) {
    try {
      return executeReadOneQuery(QUERY_FIND_BY_USERNAME, username);
    }
    catch (NoResultException nre) {
      return null;
    }
  }

  @Override
  protected List<User> readResults(ResultSet resultSet) throws SQLException {
    // List of users retrieved
    List<User> users = new LinkedList<>();
    
    // Iterates over all the results to build the users
    while (resultSet.next()) {
      users.add(readResult(resultSet));
    }

    return users;
  }
  
  @Override
  protected User readResult(ResultSet resultSet) throws SQLException {
    // Build a user from the result set
    User user = new User(
      resultSet.getString("username"),
      resultSet.getString("first_name"),
      resultSet.getString("last_name")
    );

    // Two fields to handle a bit differently
    setId(user, resultSet.getLong("id"));
    user.setHashedPassword(resultSet.getString("hashed_password"));
    
    return user;
  }
}
