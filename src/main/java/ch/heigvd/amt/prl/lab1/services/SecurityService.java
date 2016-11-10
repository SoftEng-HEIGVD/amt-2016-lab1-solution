package ch.heigvd.amt.prl.lab1.services;

import ch.heigvd.amt.prl.lab1.dao.IUserDao;
import ch.heigvd.amt.prl.lab1.models.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Implementation of the security service
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Stateless
public class SecurityService implements ISecurityService {
  @EJB
  private IUserDao userDao;

  @Override
  public User checkCredentials(String username, String password) {
    User user = userDao.find(username);
    
    if (user != null && hashPassword(password).equals(user.getHashedPassword())) {
      return user;
    }
    
    return null;
  }

  @Override
  public String hashPassword(String password) {
    return DigestUtils.sha512Hex(password);
  }
}
