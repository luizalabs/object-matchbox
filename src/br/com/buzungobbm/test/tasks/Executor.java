package br.com.buzungobbm.test.tasks;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import br.com.buzungobbm.matchbox.comparator.BaseType;
import br.com.buzungobbm.matchbox.comparator.PromotionBenefit;
import br.com.buzungobbm.matchbox.comparator.PromotionRule;
import br.com.buzungobbm.test.beans.Order;
import br.com.buzungobbm.test.beans.Product;

public class Executor {
	
	private static boolean isList (Type type) {
		return type instanceof List || type.equals(List.class);
	}
	
	private static boolean isMap (Type type) {
		return type instanceof Map || type.equals(Map.class);
	}
	
	private static boolean isEnum (Type type) {
		return type instanceof Enum || type.equals(Enum.class);
	}
	
	public void matchRule(Order order, List<PromotionRule> matchTypes) {
	}
	
	public void matchBenefit(Order order, List<PromotionBenefit> matchTypes) {
	}
	
	public void matchObject(Order order, List<BaseType> rules) {
		rules = null;
		Field[] classFields = Order.class.getDeclaredFields();

		for (Field field : classFields) {
			Type type = field.getType();
			if (isList(type)) {
				inspectList(field, Product.class.toString());
			} else if (isMap(type)) {
				inspectMap(field, Product.class.toString());
			} else if (isEnum(type)) {
				inspectEnum(field, Product.class.toString());
			} else {
				inspectPrimitives(field, Product.class.toString());
			}
		}
	}

	private void inspectList(Field field, String className) {
	}

	private void inspectMap(Field field, String className) {
	}

	private void inspectEnum(Field field, String className) {
	}

	private void inspectPrimitives(Field field, String className) {
	}
	
	
}