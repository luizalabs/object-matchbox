package br.com.buzungobbm.matchbox;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.buzungobbm.matchbox.mocks.*;

public class ObjectMatcherTest {
	
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
		TestObject testObject = new TestObject("Skip and return true should force matcher to set applyable=true", "Blue", 299.00, null);
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
		TestObject testObject = new TestObject("Skip and return true should force matcher to set applyable=false", "Blue", 299.00, null);
		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("Custom match returning false", MatchingOptions.SKIP_AND_RETURN_FALSE));
		
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
		TestObject testObject = new TestObject("Primitives comparison within object", "Blue", 299.00, null);
		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("Setting applyable for product with price over 200.00", testObject.getClass(), "price", Operator.GREATER_THAN,"200.00"));
		testFilter.add(this.buildFilter("Setting applyable for blue coloured product", testObject.getClass(), "color", Operator.EQUALS_TO,"blue"));
		
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
		TestObject testObject = new TestObject("Primitives comparison within nested object", "Blue", 299.00, new NestedClass("nestedTest", 200.00, 30));

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
		TestObject testObject = new TestObject("Primitives ", "Blue", 299.00, null);
		ArrayList<String> primitiveStringList = new ArrayList<String>();
		primitiveStringList.add("first_primitive_string");
		primitiveStringList.add("Second Primitive STRING");
		testObject.setFakeList(primitiveStringList);

		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		testFilter.add(this.buildFilter("First primitive string test with list", testObject.getClass(), "fakeList", Operator.EQUALS_TO,"first_primitive_string"));
		testFilter.add(this.buildFilter("Second primitive string testwith list", testObject.getClass(), "fakeList", Operator.EQUALS_TO,"Second Primitive STRING"));
		
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
	public void primitivesListComparisonWithinNestedObject () {
		TestObject testObject = new TestObject("Primitives list comparison within nested object", "Blue", 299.00, new NestedClass("nestedTest", 200.00, 30));

		ArrayList<String> primitiveStringList = new ArrayList<String>();
		primitiveStringList.add("first_primitive_string");
		primitiveStringList.add("Second Primitive STRING");
		testObject.getNestedObject().setFakeList(primitiveStringList);

		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		try {
			testFilter.add(this.buildFilter("First primitive string test with list", "br.com.buzungobbm.matchbox.mocks.NestedClass", "fakeList", Operator.EQUALS_TO,"first_primitive_string"));
			testFilter.add(this.buildFilter("Second primitive string testwith list", "br.com.buzungobbm.matchbox.mocks.NestedClass", "fakeList", Operator.EQUALS_TO,"Second Primitive STRING"));
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
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
	public void complexListComparisonWithinObject () {
		TestObject testObject = new TestObject("Complex list comparison within nested object", "Blue", 299.00, new NestedClass("nestedTest", 200.00, 30));

		ArrayList<NestedClass> primitiveStringList = new ArrayList<NestedClass>();
		primitiveStringList.add(new NestedClass("nestedListObjectTest", 250.00, 30));
		primitiveStringList.add(new NestedClass("anotherNestedListObjectTest", 150.00, 30));
		testObject.setFakeNestedObjectList(primitiveStringList);

		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		try {
			testFilter.add(this.buildFilter("First complex string test with list", "br.com.buzungobbm.matchbox.mocks.NestedClass", "name", Operator.EQUALS_TO,"nestedListObjectTest"));
			testFilter.add(this.buildFilter("First complex double test with list", "br.com.buzungobbm.matchbox.mocks.NestedClass", "price", Operator.LOWER_THAN_OR_EQUALS_TO,"250.00"));
			testFilter.add(this.buildFilter("Second complex string test with list", "br.com.buzungobbm.matchbox.mocks.NestedClass", "name", Operator.EQUALS_TO,"anotherNestedListObjectTest"));
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
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
		assertThat(true, equalTo(result.get(2).isApplyable()));
	}

	@Test
	public void complexListComparisonWithinNestedObject () {
		TestObject testObject = new TestObject("Complex list comparison within nested object", "Blue", 299.00, null);
		
		NestedClass nestedObject = new NestedClass("nestedTest", 200.00, 30);
		ArrayList<NestedClass> primitiveStringList = new ArrayList<NestedClass>();
		primitiveStringList.add(new NestedClass("nestedListObjectTest", 199.99, 10));
		primitiveStringList.add(new NestedClass("anotherNestedListObjectTest", 49.90, 10));
		nestedObject.setFakeNestedObjectList(primitiveStringList);
		
		testObject.setNestedObject(nestedObject);

		ArrayList<BaseFilter> testFilter = new ArrayList<BaseFilter>();
		try {
			testFilter.add(this.buildFilter("First complex test with nested object in a list comparing a string", "br.com.buzungobbm.matchbox.mocks.NestedClass", "name", Operator.EQUALS_TO, "nestedListObjectTest"));
			testFilter.add(this.buildFilter("First complex test with nested object in a list comparing a double", "br.com.buzungobbm.matchbox.mocks.NestedClass", "price", Operator.EQUALS_TO, "199.99"));
			testFilter.add(this.buildFilter("Second complex test with nested object in a list comparing a string", "br.com.buzungobbm.matchbox.mocks.NestedClass", "name", Operator.EQUALS_TO, "anotherNestedListObjectTest"));
			testFilter.add(this.buildFilter("Second complex test with nested object in a list comparing a double", "br.com.buzungobbm.matchbox.mocks.NestedClass", "price", Operator.EQUALS_TO, "49.90"));
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
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
		assertThat(true, equalTo(result.get(2).isApplyable()));
		assertThat(true, equalTo(result.get(3).isApplyable()));
	}

}