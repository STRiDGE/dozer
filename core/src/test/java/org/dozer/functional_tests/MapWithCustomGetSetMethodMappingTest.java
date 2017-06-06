package org.dozer.functional_tests;

import java.util.HashMap;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class MapWithCustomGetSetMethodMappingTest extends AbstractFunctionalTest {

  private static final String NULL_VALUE = "nullValue";
  private static final String EMPTY_VALUE = "emptyValue";
  private static final String SPACE_VALUE = "spaceValue";

  @Test
  public void testNoMapSetMethodsDefined() {
    DozerBeanMapper mapper = new DozerBeanMapper();
    mapper.addMapping(new BeanMappingBuilder() {
      
      @Override
      protected void configure() {
        //mapping(HashMap<String, String>, HashMap<String, String>);
      }
    });
    
    Map<String, String> input = new HashMap<>();
    input.put(NULL_VALUE, null);
    input.put(EMPTY_VALUE, "");
    input.put(SPACE_VALUE, " ");
    
    MapWithCustomGetAndPut output = mapper.map(input, MapWithCustomGetAndPut.class);
    
    assertThat(output.get(NULL_VALUE), is(nullValue()));
    assertThat(output.get(EMPTY_VALUE), is(""));
    assertThat(output.get(SPACE_VALUE), is(" "));
  }
  
  protected static class MapWithCustomGetAndPut extends HashMap<String, String> {
    
    public String customPut(String key, String value) {
      if (value == null) {
        super.put(key, "");
      }
      
      return super.put(key, value);
    }
    
    public Object customGet(Object key) {
      Object value = super.get(key);
      
      if (value == null) {
        return "NULL";
      } else {
        return value;
      }
    }
    
  }
}
