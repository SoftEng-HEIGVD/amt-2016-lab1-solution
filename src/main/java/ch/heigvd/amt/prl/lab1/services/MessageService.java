package ch.heigvd.amt.prl.lab1.services;

import java.util.ArrayDeque;
import java.util.Queue;
import javax.ejb.Singleton;

/**
 * Implementation of messaging service
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Singleton
public class MessageService implements IMessageService {
  private final Queue<String> messageQueue = new ArrayDeque<>();

  @Override
  public void addMessage(String message) {
    messageQueue.add(message);
  }

  @Override
  public boolean hasMessages() {
    return !messageQueue.isEmpty();
  }

  @Override
  public String pop() {
    return messageQueue.poll();
  }
}
