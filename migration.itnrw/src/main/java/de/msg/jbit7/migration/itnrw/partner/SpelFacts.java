package de.msg.jbit7.migration.itnrw.partner;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SpelFacts extends Facts {
	
	private final ExpressionParser expressionParser = new SpelExpressionParser();
	
	private final  Map<String, Converter<?, ?>> converters = new HashMap<>();  ; 
	
	public SpelFacts(Collection<Converter<?, ?>> converters) {
		converters.forEach(converter -> this.converters.put(converter.toString(), converter));
	}
	
	public SpelFacts() {
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		
		Assert.hasText(name , "Name is required");
		
		
		final String[] arguments= name(name);
		if( arguments.length==2) {
			if( converters.containsKey(arguments[0]) ) {
				final Converter<Object, Object> converter = (Converter<Object, Object>) converters.get(arguments[0]);
				Object value = StringUtils.hasText(arguments[1]) ? asMap().get(arguments[1]): asMap();
				return  (T) converter.convert(value);
			}
			
		final Object fact = asMap().get(arguments[0]);
			return (T) expressionParser.parseExpression(arguments[1]).getValue(fact);
			
		}
		return super.get(name);
	  }

	private String[] name(final String name) {
		if( name.endsWith("$")) {
			return (name+" ").split("[$]");
		}
		
		return name.split("[$]");
	}

}
