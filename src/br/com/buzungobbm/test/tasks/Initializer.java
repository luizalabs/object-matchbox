package br.com.buzungobbm.test.tasks;

import java.util.ArrayList;
import java.util.List;

import br.com.buzungobbm.matchbox.comparator.PromotionBenefit;
import br.com.buzungobbm.matchbox.comparator.PromotionRule;
import br.com.buzungobbm.test.beans.Category;
import br.com.buzungobbm.test.beans.Order;
import br.com.buzungobbm.test.beans.Product;


public class Initializer {

	public static void main(String[] args) {

		Product p1 = new Product(1, "tv", "tv de led", "sony", 699.90);
		p1.setCategory(new Category(1000, "televisoes", "televisores"));

		Product p2 = new Product(2, "som", "aparelho de som sony", "Sony", 699.90);
		p1.setCategory(new Category(2000, "audio", "aparelhos de audio"));

		Product p3 = new Product(3, "ventilador", "tv de led", "brastemp", 699.90);
		p1.setCategory(new Category(3000, "eletroeletronicos", "ventiladores"));

		Product p4 = new Product(4, "notebook", "notebook sony vaio", "SONY", 699.90);
		p1.setCategory(new Category(4000, "computadores", "computadores portateis"));
		
		Order order = new Order();

		order.addProduct(p1);
		order.addProduct(p2);
		order.addProduct(p3);
		order.addProduct(p4);
		
		Executor matcher = new Executor();

		List<PromotionRule> ruleList = new ArrayList<PromotionRule>();
		PromotionRule rule1 = new PromotionRule();
		PromotionRule rule2 = new PromotionRule();
		PromotionRule rule3 = new PromotionRule();
		ruleList.add(rule1);
		ruleList.add(rule2);
		ruleList.add(rule3);
		matcher.matchRule(order, ruleList);
		
		List<PromotionBenefit> benefitList = new ArrayList<PromotionBenefit>();
		PromotionBenefit benefit1 = new PromotionBenefit();
		PromotionBenefit benefit2 = new PromotionBenefit();
		PromotionBenefit benefit3 = new PromotionBenefit();
		benefitList.add(benefit1);
		benefitList.add(benefit2);
		benefitList.add(benefit3);
		matcher.matchBenefit(order, benefitList);
	}
}