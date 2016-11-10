package ch.heigvd.amt.prl.lab1.models;

/**
 * Force all the models to return a numeric identifier
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public interface IModel {
  /**
   * @return Supposed to be unique numerical identifier
   */
  long getId();
}
