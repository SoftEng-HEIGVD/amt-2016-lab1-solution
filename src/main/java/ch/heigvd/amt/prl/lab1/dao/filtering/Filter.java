package ch.heigvd.amt.prl.lab1.dao.filtering;

import ch.heigvd.amt.prl.lab1.models.IModel;

/**
 * This class represent a single and simple query filter parameter
 * which consist of a field name and a value.
 * 
 * This filter mechanism is only valid for Sting attributes and works
 * with a contains like comparison.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class Filter {
  private final Class<? extends IModel> modelClass;
  private final String fieldName;
  private final String value;

  /**
   * Constructor
   * 
   * @param modelClass The model class for which the filer is applied
   * @param fieldName The field name to apply the filter
   * @param value The value of the filter
   */
  public Filter(Class<? extends IModel> modelClass, String fieldName, String value) {
    this.modelClass = modelClass;
    this.fieldName = fieldName;
    this.value = value;
  }

  public Class<? extends IModel> getModelClass() {
    return modelClass;
  }
  
  public String getFieldName() {
    return fieldName;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return
      this.getClass().getSimpleName() + "{" +
      "modelClass=" + modelClass.getSimpleName() + ", " +
      "fieldName=" + fieldName + ", " +
      "fieldValue=" + value +
      "}";
  }
}
