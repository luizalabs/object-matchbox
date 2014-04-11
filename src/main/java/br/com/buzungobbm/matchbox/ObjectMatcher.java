package br.com.buzungobbm.matchbox;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.buzungobbm.matchbox.exception.NoOperatorSetException;

public class ObjectMatcher {
	
	private Object invokeObjectGetter(Method method, Object instance) {
		Object genericObject = new Object();

		try {
			genericObject = method.invoke(instance);
		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		}

		return genericObject;
	}

	private Map<?, ?> invokeMapGetter(Method method, Object instance) {
		Map<?, ?> genericMap = new HashMap<>();

		try {

			genericMap = (HashMap<?, ?>) method.invoke(instance);

		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		}

		return genericMap;
	}

	private List<?> invokeListGetter (Method method, Object instance) {
		List<?> genericList = new ArrayList<Object>();

		try {

			genericList = (List<?>) method.invoke(instance);

		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		}

		return genericList;
	}

	private String invokeGetter (Method method, Object instance) {
		String fieldValue = "";

		try {

			if (method.getReturnType().equals(Byte.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(byte.class))) {
				fieldValue = Byte.toString((Byte)method.invoke(instance));

			} else if (method.getReturnType().equals(Short.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(short.class))) {
				fieldValue = Short.toString((Short)method.invoke(instance));

			} else if (method.getReturnType().equals(Integer.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(int.class))) {
				fieldValue = Integer.toString((Integer)method.invoke(instance));

			} else if (method.getReturnType().equals(Long.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(long.class))) {
				fieldValue = Long.toString((Long)method.invoke(instance));

			} else if (method.getReturnType().equals(Float.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(float.class))) {
				fieldValue = Float.toString((Float)method.invoke(instance));

			} else if (method.getReturnType().equals(Double.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(double.class))) {
				fieldValue = Double.toString((Double)method.invoke(instance));

			} else if (method.getReturnType().equals(Boolean.class) || (method.getReturnType().isPrimitive() && method.getReturnType().equals(boolean.class))) {
				fieldValue = Boolean.toString((Boolean)method.invoke(instance));

			} else {
				fieldValue = (String) method.invoke(instance);
			}

		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		}
		
		return fieldValue;
	}
	
	private static boolean isPrimitive (Type type) {
		return type.equals(Byte.class) || type.equals(byte.class) || 
			   type.equals(Short.class) || type.equals(short.class) || 
			   type.equals(Integer.class) || type.equals(int.class) || 
			   type.equals(Long.class) || type.equals(long.class) || 
			   type.equals(Date.class) ||
			   type.equals(Float.class) || type.equals(float.class) || 
			   type.equals(Double.class) || type.equals(double.class) || 
			   type.equals(Boolean.class) || type.equals(boolean.class) ||
			   type.equals(String.class) || type.equals(char.class);
	}
	
	private static boolean isList (Type type) {
		return type.equals(List.class);
	}
	
	private static boolean isMap (Type type) {
		return type.equals(Map.class);
	}

	public boolean conditionSatisfies(String value, Operator operator, String fieldValue) {
		if (fieldValue.equals("")) {
			return false;
		} else if (operator.equals(Operator.EQUALS_TO)) {
			try {
				return Double.parseDouble(value) == Double.parseDouble(fieldValue);
			} catch (NumberFormatException nfe) {
				return value.toLowerCase().equals(fieldValue.toLowerCase());
			}
		} else if (operator.equals(Operator.GREATER_THAN)) {
			return Double.parseDouble(fieldValue) > Double.parseDouble(value);
		} else if (operator.equals(Operator.GREATER_THAN_OR_EQUALS_TO)) {
			return Double.parseDouble(fieldValue) >= Double.parseDouble(value);
		} else if (operator.equals(Operator.LOWER_THAN)) {
			return Double.parseDouble(fieldValue) < Double.parseDouble(value);
		} else if (operator.equals(Operator.LOWER_THAN_OR_EQUALS_TO)) {
			return Double.parseDouble(fieldValue) <= Double.parseDouble(value);
		} else {
			throw new NoOperatorSetException();
		}
	}

	public List<BaseFilter> matchObject(Object object, List<BaseFilter> filters) throws ClassNotFoundException {
		for (BaseFilter filter : filters) {
			if (filter.getOption() == null) {
				boolean status = conditionSatisfies(filter.getValue(), filter.getOperator(), extractValue(object, filter));
				filter.setApplyable(status);
			} else {
				if (filter.getOption().equals(MatchingOptions.SKIP_AND_RETURN_TRUE)) {
					filter.setApplyable(true);
				} else if (filter.getOption().equals(MatchingOptions.SKIP_AND_RETURN_FALSE)) {
					filter.setApplyable(false);
				} else {
					boolean status = conditionSatisfies(filter.getValue(), filter.getOperator(), extractValue(object, filter));
					filter.setApplyable(status);
				}
			}
		}
		return filters;
	}

	private boolean isThisMethodTheFieldGetter (Method method, Field field) {
		return (method.getName().startsWith("get")) &&
			   (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) &&
			   (method.getName().length() == (field.getName().length() + 3));
	}

	private String extractValueFromMap (Method method, Object instance, BaseFilter filter) throws ClassNotFoundException {

		Map<?, ?> genericMap = invokeMapGetter(method, instance);

		if (genericMap == null) 
			return null;

		Set<?> keys = genericMap.keySet();

		for (Object key : keys) {
			Object item = genericMap.get(key);
			if (isPrimitive(item.getClass())) {
				if (conditionSatisfies(filter.getValue(), filter.getOperator(), String.valueOf(item))) {
					return String.valueOf(item);
				}
			} else {
				String s = extractValue(item, filter);
				if (conditionSatisfies(filter.getValue(), filter.getOperator(), s))
					return s;
			}
		}

		return "";

	}

	private String extractValueFromList (Method method, Object instance, BaseFilter filter) throws ClassNotFoundException {
		List<?> genericList = invokeListGetter(method, instance);

		if (genericList == null) 
			return null;

		for (Object item : genericList) {
			if (isPrimitive(item.getClass())) {
				if (conditionSatisfies(filter.getValue(), filter.getOperator(), String.valueOf(item))) {
					return String.valueOf(item);
				}
			} else {
				String s = extractValue(item, filter);
				if (conditionSatisfies(filter.getValue(), filter.getOperator(), s))
					return s;
			}
		}
		
		return "";
	}
	
	public String extractValue(Object instance, BaseFilter filter) throws ClassNotFoundException {
		String fieldValue = "";

		Class<?> baseClass = Class.forName(instance.getClass().getName());
		Field[] classFields = baseClass.getDeclaredFields();

		if (instance.getClass().equals(filter.getClassName())) {

			for (Field field : classFields) {

				if (!fieldValue.equals(""))
					break;

				if (field.getName().equals(filter.getAttributeName())) {
					for (Method method : instance.getClass().getMethods()) {
						if (isThisMethodTheFieldGetter(method, field)) {
							if (isPrimitive(method.getReturnType())) {
								String s = invokeGetter(method, instance);
								if (conditionSatisfies(filter.getValue(), filter.getOperator(), s)) {
									return s;
								}
							} else {
								System.out.println("WTF?!");
							}
						}
					}
				} else {
					for (Method method : instance.getClass().getMethods()) {
						if (isList(method.getReturnType())) {

							String valueExtractedFromList = extractValueFromList(method, instance, filter);
							if (valueExtractedFromList == null)
								continue;
							else if (!valueExtractedFromList.equals(""))
								return valueExtractedFromList;

						} else if (isMap(method.getReturnType())) {

							String valueExtractedFromMap = extractValueFromMap(method, instance, filter);
							if (valueExtractedFromMap == null)
								continue;
							else if (!valueExtractedFromMap.equals(""))
								return valueExtractedFromMap;

						}
					}
				}
			}

		} else {

			for (Field field : classFields) {

				Type type = field.getType();

				if (isList(type)) {

					for (Method method : instance.getClass().getMethods()) {
						if (isThisMethodTheFieldGetter(method, field)) {
							String valueExtractedFromList = extractValueFromList(method, instance, filter);
							if (valueExtractedFromList == null)
								continue;
							else if (!valueExtractedFromList.equals(""))
								return valueExtractedFromList;
							else
								break;
						}
					}

				} else if (isMap(type)) {

					for (Method method : instance.getClass().getMethods()) {

						if (isThisMethodTheFieldGetter(method, field)) {

							String valueExtractedFromMap = extractValueFromMap(method, instance, filter);
							if (valueExtractedFromMap == null)
								continue;
							else if (!valueExtractedFromMap.equals(""))
								return valueExtractedFromMap;

						}
					}

				} else if (!isPrimitive(type)) {

					for (Method method : instance.getClass().getMethods()) {
						if (isThisMethodTheFieldGetter(method, field)) {
							Object genericObject = invokeObjectGetter(method, instance);
							if (genericObject != null) {
								String s = extractValue(genericObject, filter);
								if (conditionSatisfies(filter.getValue(), filter.getOperator(), s)) {
									return s;
								}
							}
						}
					}

				}
			}
		}

		return fieldValue;
	}

	public boolean allFiltersAreApplyable(ArrayList<BaseFilter> filters) {

		for (BaseFilter filter : filters) {
			if (!filter.isApplyable())
				return false;
		}
		
		return true;
	}
	
}