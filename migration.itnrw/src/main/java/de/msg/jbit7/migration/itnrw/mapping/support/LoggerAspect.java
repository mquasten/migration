package de.msg.jbit7.migration.itnrw.mapping.support;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggerAspect {
	
	
	
	@AfterThrowing(value="within(@org.springframework.stereotype.Service *)",throwing="exception" )
	public void logBefore(final JoinPoint jp, final Exception exception) {
		final MethodSignature ms = (org.aspectj.lang.reflect.MethodSignature) jp.getSignature();
		final Class<?>   targetType= ms.getDeclaringType();
		
		final Logger logger = LoggerFactory.getLogger(targetType);
		final String name = ms.getMethod().getName();
		logger.error("Error method: " + name, exception );
	}

}
