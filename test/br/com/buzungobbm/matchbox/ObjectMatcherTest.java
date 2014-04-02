package br.com.buzungobbm.matchbox;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.buzungobbm.matchbox.mocks.*;

public class ObjectMatcherTest {

	private Object getTestObjectInstance (String name, String color, Double price) {
		return getTestObjectInstance (name, color, price, null);
	}
	
	private Object getTestObjectInstance (String name, String color, Double price, NestedClass nestedObject) {
		return new TestObject(name, color, price, nestedObject);
	}
	
	private BaseFilter getBaseFilterInstance() {
		return new BaseFilter() {
			String value;
			MatchingOptions options;
			Operator operator;
			String description;
			Class<?> className;
			String attributeName;
			boolean applyable;
			
			@Override
			public void setValue(String value) { this.value = value; }
			
			@Override
			public void setOption(MatchingOptions options) { this.options = options; }
			
			@Override
			public void setOperator(Operator operator) { this.operator = operator; }
			
			@Override
			public void setDescription(String description) { this.description = description; }
			
			@Override
			public void setClassName(Class<?> className) { this.className = className; }
			
			@Override
			public void setAttributeName(String attributeName) { this.attributeName = attributeName; }
			
			@Override
			public void setApplyable(boolean applyable) { this.applyable = applyable; }
			
			@Override
			public boolean isApplyable() { return this.applyable; }
			
			@Override
			public String getValue() { return this.value; }
			
			@Override
			public MatchingOptions getOption() { return this.options; }
			
			@Override
			public Operator getOperator() { return this.operator; }
			
			@Override
			public String getDescription() { return this.description; }
			
			@Override
			public Class<?> getClassName() { return this.className; }
			
			@Override
			public String getAttributeName() { return this.attributeName; }
		};
		
	}
	
	private BaseFilter buildFilter (String description, MatchingOptions option) {

		BaseFilter filter = this.getBaseFilterInstance();
		
		filter.setDescription(description);
		filter.setOption(option);
		
		return filter;

	}

	private BaseFilter buildFilter (String description, String className, String attributeName, Operator operator, String value) throws ClassNotFoundException {
		return buildFilter(description, Class.forName(className), attributeName, operator, value);
	}
	
	private BaseFilter buildFilter (String description, Class<?> className, String attributeName, Operator operator, String value) {

		BaseFilter filter = this.getBaseFilterInstance();

		filter.setDescription(description);
		filter.setClassName(className);
		filter.setAttributeName(attributeName);
		filter.setOperator(operator);
		filter.setValue(value);
		
		return filter;
		
	}
	
	@Test
	public void skipAndReturnTrueShouldForceMatcherToSetApplyableEqualsTrue () {
		Object testObject = this.getTestObjectInstance("SimpleTest", "Blue", 299.00);
		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("Custom match returning true", MatchingOptions.SKIP_AND_RETURN_TRUE));
		
		ObjectMatcher matcher = new ObjectMatcher();
		List<BaseFilter> result = new ArrayList<BaseFilter>();
		try {
			result = matcher.matchObject(testObject, testFilter);
		} catch (ClassNotFoundException e) {
		}
		
		assertThat(true, equalTo(result.get(0).isApplyable()));
	}

	@Test
	public void skipAndReturnFalseShouldForceMatcherToSetApplyableEqualsFalse () {
		Object testObject = this.getTestObjectInstance("SimpleTest", "Blue", 299.00);
		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("Custom match returning true", MatchingOptions.SKIP_AND_RETURN_FALSE));
		
		ObjectMatcher matcher = new ObjectMatcher();
		List<BaseFilter> result = new ArrayList<BaseFilter>();
		try {
			result = matcher.matchObject(testObject, testFilter);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		assertThat(true, not(equalTo(result.get(0).isApplyable())));
	}

	@Test
	public void primitivesComparisonWithinObject () {
		Object testObject = this.getTestObjectInstance("SimpleTest", "Blue", 299.00);
		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("Setting applyable for product with price over 200.00", testObject.getClass(), "price", Operator.GREATER_THAN,"200.00"));
		testFilter.add(this.buildFilter("Setting applyable for product with color blue", testObject.getClass(), "color", Operator.EQUALS_TO,"blue"));
		
		ObjectMatcher matcher = new ObjectMatcher();
		List<BaseFilter> result = new ArrayList<BaseFilter>();
		try {
			result = matcher.matchObject(testObject, testFilter);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		assertThat(true, equalTo(result.get(0).isApplyable()));
		assertThat(true, equalTo(result.get(1).isApplyable()));
	}

	@Test
	public void primitivesComparisonWithinNestedObject () {
		Object testObject = this.getTestObjectInstance("SimpleTest", "Blue", 299.00, new NestedClass("nestedTest", 200.00, 30));

		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		try {
			testFilter.add(this.buildFilter("Setting applyable for product with price over 200.00", "br.com.buzungobbm.matchbox.mocks.NestedClass", "price", Operator.GREATER_THAN,"199.90"));
			testFilter.add(this.buildFilter("Setting applyable for product named nestedTest", "br.com.buzungobbm.matchbox.mocks.NestedClass", "name", Operator.EQUALS_TO,"nestedTest"));
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		ObjectMatcher matcher = new ObjectMatcher();
		List<BaseFilter> result = new ArrayList<BaseFilter>();
		try {
			result = matcher.matchObject(testObject, testFilter);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		assertThat(true, equalTo(result.get(0).isApplyable()));
		assertThat(true, equalTo(result.get(1).isApplyable()));
	}

	@Test
	public void primitivesListComparisonWithinObject () {
		assertThat(true, equalTo(false));
	}

	@Test
	public void primitivesListComparisonWithinNestedObject () {
		assertThat(true, equalTo(false));
	}

	@Test
	public void complexListComparisonWithinObject () {
		assertThat(true, equalTo(false));
	}

	@Test
	public void complexListComparisonWithinNestedObject () {
		assertThat(true, equalTo(false));
	}

}