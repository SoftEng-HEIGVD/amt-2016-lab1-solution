package ch.heigvd.amt.prl.lab1.rest;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation PATCH for the REST API.
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH {
}
