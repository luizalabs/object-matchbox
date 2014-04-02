package br.com.buzungobbm.matchbox;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Matcher {
	
	private static boolean isList (Type type) {
		return type instanceof List || type.equals(List.class);
	}
	
	private static boolean isMap (Type type) {
		return type instanceof Map || type.equals(Map.class);
	}
	
	private static boolean isEnum (Type type) {
		return type instanceof Enum || type.equals(Enum.class);
	}

	public List<BaseFilter> matchObject(Object object, List<BaseFilter> filters) throws ClassNotFoundException {
		for (BaseFilter filter : filters) {
			if (filter.getOption().equals(MatchingOptions.SKIP_AND_RETURN_TRUE)) {
				filter.setApplyable(true);
			} else if (filter.getOption().equals(MatchingOptions.SKIP_AND_RETURN_FALSE)) {
				filter.setApplyable(false);
			} else {
				String fieldValue = extractValue(object, filter);
				filter.setApplyable(conditionSatisfies(filter.getValue(), filter.getOperator(), fieldValue));
			}
		}
		return filters;
	}
	
	private boolean conditionSatisfies(String value, Operator operator, String fieldValue) {
		if (operator.equals(Operator.EQUALS_TO)) {
			try {
				return Double.parseDouble(value) == Double.parseDouble(fieldValue);
			} catch (NumberFormatException nfe) {
				return value.equals(fieldValue);
			}
		} else if (operator.equals(Operator.GREATER_THAN)) {
			return Double.parseDouble(value) > Double.parseDouble(fieldValue);
		} else if (operator.equals(Operator.GREATER_THAN_OR_EQUALS_TO)) {
			return Double.parseDouble(value) >= Double.parseDouble(fieldValue);
		} else if (operator.equals(Operator.LOWER_THAN)) {
			return Double.parseDouble(value) < Double.parseDouble(fieldValue);
		} else if (operator.equals(Operator.LOWER_THAN_OR_EQUALS_TO)) {
			return Double.parseDouble(value) <= Double.parseDouble(fieldValue);
		}
		//TODO: return no_operation_set exception or something like 
		return false;
	}
	
	private String extractValue(Object object, BaseFilter filter) throws ClassNotFoundException {
		String fieldValue = "";

		Class<?> c = Class.forName(object.getClass().getName());
		Field[] classFields = c.getDeclaredFields();

		for (Field field : classFields) {
			Type type = field.getType();
			if (isList(type)) {
				inspectList(field, List.class.toString());
			} else if (isMap(type)) {
				inspectMap(field, Map.class.toString());
			} else if (isEnum(type)) {
				inspectEnum(field, Enum.class.toString());
			} else {
				fieldValue = inspectPrimitives(field, String.class.toString());
			}
		}
		return "";
	}

	private void inspectList(Field field, String className) {
	}

	private void inspectMap(Field field, String className) {
	}

	private void inspectEnum(Field field, String className) {
	}

	private String inspectPrimitives(Field field, String className) {
		return className;
	}
	
}