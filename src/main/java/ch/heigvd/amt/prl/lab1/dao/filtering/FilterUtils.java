package ch.heigvd.amt.prl.lab1.dao.filtering;

import ch.heigvd.amt.prl.lab1.models.IModel;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class to extract the filter names mappings.
 * 
 * A filter name mapping is the correspondence between an attribute name of a model class and its database
 * field name.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class FilterUtils {

  /**
   * Stores the resolved field names to avoid calling Java Reflection each time we need to know the field
   * mappings.
   * 
   * For the internal Map&lt;String, String&gt;, the key is the attribute name of the class, and the value
   * is the database field name.
   */
  private static final Map<Class<? extends IModel>, Map<String, String>> FILTER_NAMES_MAPPINGS = new HashMap<>();

  /**
   * Retrieves the available filters of a class
   * 
   * @param modelClass The class for which we want to retrieve the filter names
   * @return The mapping between attribute of the class and the database field names
   */
  private static Map<String, String> extractFieldNames(Class<? extends IModel> modelClass) {
    // Return empty mapping for null model classes
    if (modelClass == null) {
      return new HashMap<>();
    }
    
    // Check if we had not already resolved the mapping
    if (!FILTER_NAMES_MAPPINGS.containsKey(modelClass)) {
      Map<String, String> fieldNames = new HashMap<>();

      // Iterates over the class fields to get the annotated fields
      for (Field field : modelClass.getDeclaredFields()) {
        
        // Check if our custom annotation is present
        if (field.isAnnotationPresent(FilterName.class)) {

          // Check if the mapping is the same between field name and database name
          FilterName filterName = field.getAnnotation(FilterName.class);
          if (filterName.value().isEmpty()) {
            fieldNames.put(field.getName(), field.getName());
          }
          else {
            fieldNames.put(field.getName(), filterName.value());
          }
        }
      }
      
      // Stores the resolved mapping
      FILTER_NAMES_MAPPINGS.put(modelClass, fieldNames);
    }
    
    // Returns the stored mapping
    return FILTER_NAMES_MAPPINGS.get(modelClass);
  }
  
  /**
   * Check if a filter name is valid
   * 
   * @param filter The filter to retrieve required data
   * @return True if the filter name is valid
   */
  public static boolean isFilterNameValid(Filter filter) {
    return extractFieldNames(filter.getModelClass()).containsKey(filter.getFieldName());
  }
  
  /**
   * @param modelClass The model class to retrieve the list of filter names
   * @return The filter names for the given model class
   */
  public static List<String> getAvailableFilterNames(Class<? extends IModel> modelClass) {
    return extractFieldNames(modelClass)
      .entrySet()
      .stream()
      .map((entry) -> entry.getValue())
      .collect(Collectors.toList());
  }
  
  /**
   * @param filter The filter to retrieve required data
   * @return The DB field name if any, null otherwise
   */
  public static String getCorrespondingDbFieldName(Filter filter) {
    return extractFieldNames(filter.getModelClass()).get(filter.getFieldName());
  }
}
