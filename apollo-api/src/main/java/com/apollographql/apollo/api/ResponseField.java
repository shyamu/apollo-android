package com.apollographql.apollo.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.apollographql.apollo.api.internal.Utils.checkNotNull;
import static java.util.Collections.unmodifiableList;

/**
 * Field is an abstraction for a field in a graphQL operation. Field can refer to: <b>GraphQL Scalar Types, Objects or
 * List</b>. For a complete list of types that a Field object can refer to see {@link ResponseField.Type} class.
 */
public class ResponseField {
  private final Type type;
  private final String responseName;
  private final String fieldName;
  private final Map<String, Object> arguments;
  private final boolean optional;
  private final List<Condition> conditions;

  public static final String VARIABLE_IDENTIFIER_KEY = "kind";
  public static final String VARIABLE_IDENTIFIER_VALUE = "Variable";
  public static final String VARIABLE_NAME_KEY = "variableName";

  /**
   * Factory method for creating a Field instance representing {@link Type#STRING}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#STRING}
   */
  public static ResponseField forString(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.STRING, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#INT}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#INT}
   */
  public static ResponseField forInt(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.INT, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#LONG}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#LONG}
   */
  public static ResponseField forLong(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.LONG, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#DOUBLE}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#DOUBLE}
   */
  public static ResponseField forDouble(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.DOUBLE, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#BOOLEAN}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#BOOLEAN}
   */
  public static ResponseField forBoolean(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.BOOLEAN, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#ENUM}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#ENUM}
   */
  public static ResponseField forEnum(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.ENUM, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing a custom {@link Type#OBJECT}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing custom {@link Type#OBJECT}
   */
  public static ResponseField forObject(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.OBJECT, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#LIST}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#LIST}
   */
  public static ResponseField forList(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    return new ResponseField(Type.LIST, responseName, fieldName, arguments, optional, conditions);
  }

  /**
   * Factory method for creating a Field instance representing a custom GraphQL Scalar type, {@link Type#CUSTOM}
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param arguments arguments to be passed along with the field
   * @param optional whether the arguments passed along are optional or required
   * @param scalarType the custom scalar type of the field
   * @param conditions list of conditions for this field
   * @return Field instance representing {@link Type#CUSTOM}
   */
  public static CustomTypeField forCustomType(String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, ScalarType scalarType, List<Condition> conditions) {
    return new CustomTypeField(responseName, fieldName, arguments, optional, scalarType, conditions);
  }

  /**
   * Factory method for creating a Field instance representing {@link Type#FRAGMENT}.
   *
   * @param responseName alias for the result of a field
   * @param fieldName name of the field in the GraphQL operation
   * @param typeConditions conditional GraphQL types
   * @return Field instance representing {@link Type#FRAGMENT}
   */
  public static ResponseField forFragment(String responseName, String fieldName, List<Condition> conditions) {
    return new ResponseField(Type.FRAGMENT, responseName, fieldName, Collections.<String, Object>emptyMap(), false, conditions);
  }

  ResponseField(Type type, String responseName, String fieldName, Map<String, Object> arguments,
      boolean optional, List<Condition> conditions) {
    this.type = type;
    this.responseName = responseName;
    this.fieldName = fieldName;
    this.arguments = arguments == null ? Collections.<String, Object>emptyMap() : Collections.unmodifiableMap(arguments);
    this.optional = optional;
    this.conditions = conditions == null ? Collections.<Condition>emptyList() : unmodifiableList(conditions);
  }

  public Type type() {
    return type;
  }

  public String responseName() {
    return responseName;
  }

  public String fieldName() {
    return fieldName;
  }

  public Map<String, Object> arguments() {
    return arguments;
  }

  public boolean optional() {
    return optional;
  }

  public List<Condition> conditions() {
    return conditions;
  }

  /**
   * Resolve field argument value by name. If argument represents a references to the variable, it will be resolved from
   * provided operation variables values.
   *
   * @param name argument name
   * @param variables values of operation variables
   * @return resolved argument value
   */
  @SuppressWarnings("unchecked") @Nullable public Object resolveArgument(@NotNull String name,
      @NotNull Operation.Variables variables) {
    checkNotNull(name, "name == null");
    checkNotNull(variables, "variables == null");
    Map<String, Object> variableValues = variables.valueMap();
    Object argumentValue = arguments.get(name);
    if (argumentValue instanceof Map) {
      Map<String, Object> argumentValueMap = (Map<String, Object>) argumentValue;
      if (isArgumentValueVariableType(argumentValueMap)) {
        String variableName = argumentValueMap.get(VARIABLE_NAME_KEY).toString();
        return variableValues.get(variableName);
      } else {
        return null;
      }
    }
    return argumentValue;
  }

  public static boolean isArgumentValueVariableType(Map<String, Object> objectMap) {
    return objectMap.containsKey(VARIABLE_IDENTIFIER_KEY)
        && objectMap.get(VARIABLE_IDENTIFIER_KEY).equals(VARIABLE_IDENTIFIER_VALUE)
        && objectMap.containsKey(VARIABLE_NAME_KEY);
  }

  /**
   * An abstraction for the field types
   */
  public enum Type {
    STRING,
    INT,
    LONG,
    DOUBLE,
    BOOLEAN,
    ENUM,
    OBJECT,
    LIST,
    CUSTOM,
    FRAGMENT,
    FRAGMENTS
  }

  /**
   * Abstraction for a Field representing a custom GraphQL scalar type.
   */
  public static final class CustomTypeField extends ResponseField {
    private final ScalarType scalarType;

    CustomTypeField(String responseName, String fieldName, Map<String, Object> arguments, boolean optional,
        ScalarType scalarType, List<Condition> conditions) {
      super(Type.CUSTOM, responseName, fieldName, arguments, optional, conditions);
      this.scalarType = scalarType;
    }

    public ScalarType scalarType() {
      return scalarType;
    }
  }

  /**
   * Abstraction for condition to be associated with field
   */
  public static class Condition {

    Condition() {
    }

    /**
     * Creates new {@link TypeNameCondition}
     */
    @NotNull public static TypeNameCondition typeCondition(@NotNull String[] types) {
      return new TypeNameCondition(Arrays.asList(types));
    }

    /**
     * Creates new {@link BooleanCondition}
     */
    @NotNull public static BooleanCondition booleanCondition(@NotNull String variableName, boolean inverted) {
      return new BooleanCondition(variableName, inverted);
    }
  }

  /**
   * Abstraction for type name condition
   */
  public static final class TypeNameCondition extends Condition {
    private final List<String> typeNames;

    TypeNameCondition(List<String> typeNames) {
      this.typeNames = checkNotNull(typeNames, "typeNames == null");
    }

    public List<String> typeNames() {
      return typeNames;
    }
  }

  /**
   * Abstraction for boolean condition
   */
  public static final class BooleanCondition extends Condition {
    private final String variableName;
    private final boolean inverted;

    BooleanCondition(@NotNull String variableName, boolean inverted) {
      this.variableName = checkNotNull(variableName, "variableName == null");
      this.inverted = inverted;
    }

    @NotNull public String variableName() {
      return variableName;
    }

    public boolean inverted() {
      return inverted;
    }
  }
}
