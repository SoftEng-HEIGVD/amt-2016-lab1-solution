package ch.heigvd.amt.prl.lab1.services;

import javax.ejb.Local;

/**
 * Messaging service to be used in the JSP pages to show messages between two requests without
 * using the HTTP Session.
 * 
 * Use case example:
 * 
 * 1. User do the registration of pages/register -> do/register
 * 2. Registration is successful and the user is redirected (HTTP 302) to the pages/login page
 * 3. On the login page, a messages is shown to tell the user the registration is successful.
 * 
 * In this use case, we want to show only once the message to the user.
 * 
 * So this session bean is a school case to use the @Singleton annotation
 * 
 * @author Laurent Prevost, laurent.prevost@laurent.prevost@heig-vd.ch
 */
@Local
public interface IMessageService {
  /**
   * Add a new message on the queue of messages
   * 
   * @param message The message to add
   */
  void addMessage(String message);
  
  /**
   * @return Check if there is at least one message
   */
  boolean hasMessages();

  /**
   * @return The oldest message on the queue
   */
  String pop();
}
