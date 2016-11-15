package ch.heigvd.amt.prl.lab1.dao.filtering;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation helps to convert an attribute name
 * to its corresponding database field name.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterName {
  /**
   * @return The name of the database field name
   */
  String value() default "";
}
