package br.com.buzungobbm.matchbox;

import java.util.List;
import java.util.concurrent.Callable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ObjectArrays;

import br.com.buzungobbm.matchbox.exception.NoOperatorSetException;

public class FilterAnalyzer implements Callable<BaseFilter> {
	
	private Object sObject;
	private BaseFilter sFilter;
	
	public FilterAnalyzer () {
	}
	
	public FilterAnalyzer (Object object, BaseFilter filter) {
		this.sObject = object;
		this.sFilter = filter;
	}

	@Override
	public BaseFilter call() throws Exception {
		BaseFilter filter = analyzeFilter(sObject, sFilter);
		return filter;
	}

	private BaseFilter analyzeFilter(Object object, BaseFilter filter) throws ClassNotFoundException {
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
		return filter;
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
			if (isPrimitiveOrSubclass(item.getClass())) {
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
			if (isPrimitiveOrSubclass(item.getClass())) {
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
	
	private Field[] mergeFields (Field[] baseClassFields, Field[] superClassFields) {
		return ObjectArrays.concat(baseClassFields, superClassFields, Field.class);
	}
	
	public String extractValue(Object instance, BaseFilter filter) throws ClassNotFoundException {
		String fieldValue = "";

		Class<?> baseClass = Class.forName(instance.getClass().getName());
		Field[] baseClassFields = baseClass.getDeclaredFields();

		Class<?> superClass = baseClass.getSuperclass();
		Field[] superClassFields = superClass.getDeclaredFields();
		
		Field[] classFields = mergeFields(baseClassFields, superClassFields);

		if (instance.getClass().equals(filter.getClassName())) {

			for (Field field : classFields) {

				if (!fieldValue.equals(""))
					break;

				if (field.getName().equals(filter.getAttributeName())) {
					for (Method method : instance.getClass().getMethods()) {
						if (isThisMethodTheFieldGetter(method, field)) {
							if (isPrimitiveOrSubclass(method.getReturnType())) {
								String s = invokeGetter(method, instance);
								if (conditionSatisfies(filter.getValue(), filter.getOperator(), s)) {
									return s;
								} else {
									break;
								}
							} else {
								System.out.println("Unidentified return type!");
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

				} else if (!isPrimitiveOrSubclass(type)) {

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
	
	private static boolean isPrimitiveOrSubclass (Type type) {
		return Number.class.isAssignableFrom((Class<?>) type) ||
			   Date.class.isAssignableFrom((Class<?>) type) ||
			   Boolean.class.isAssignableFrom((Class<?>) type) ||
			   String.class.isAssignableFrom((Class<?>) type) ||
			   boolean.class.isAssignableFrom((Class<?>) type) ||
			   byte.class.isAssignableFrom((Class<?>) type) ||
			   short.class.isAssignableFrom((Class<?>) type) ||
			   int.class.isAssignableFrom((Class<?>) type) ||
			   long.class.isAssignableFrom((Class<?>) type) ||
			   float.class.isAssignableFrom((Class<?>) type) ||
			   double.class.isAssignableFrom((Class<?>) type) ||
			   char.class.isAssignableFrom((Class<?>) type);
	}
	
	private static boolean isList (Type type) {
		return List.class.isAssignableFrom((Class<?>) type);
	}
	
	private static boolean isMap (Type type) {
		return Map.class.isAssignableFrom((Class<?>) type);
	}

	public boolean conditionSatisfies(String value, Operator operator, String fieldValue) {
		if (fieldValue == null) {
			return false;
		} else if (fieldValue.equals("")) {
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

}
