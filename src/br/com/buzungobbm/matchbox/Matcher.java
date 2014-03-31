package br.com.buzungobbm.matchbox;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.buzungobbm.test.beans.Order;
import br.com.buzungobbm.test.beans.Product;
import br.com.magazineluiza.service.dao.database.dan.BaseType;
import br.com.magazineluiza.service.dao.pandora.PromotionBenefit;
import br.com.magazineluiza.service.dao.pandora.PromotionRule;

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

	public void matchObject(Object order, List<BaseType> filters) {
		try {
			Class<?> c = Class.forName(order.getClass().getName());
			Field[] classFields = c.getDeclaredFields();

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
		} catch (ClassNotFoundException e) {
			//TODO: log this
			e.printStackTrace();
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