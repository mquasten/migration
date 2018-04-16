package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

public class CatchExceptionRuleListener implements RuleListener {

	private List<Exception> exceptions = new ArrayList<>();
	
	@Override
	public final boolean beforeEvaluate(Rule rule, Facts facts) {
		
		return true;
	}

	@Override
	public final  void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
		
		
	}

	@Override
	public final  void beforeExecute(Rule rule, Facts facts) {
		
		
	}

	@Override
	public final void onSuccess(Rule rule, Facts facts) {
		
		
	}

	@Override
	public final void onFailure(final Rule rule, final Facts facts, final Exception exception) {
		exceptions.add(exception);
	}

	
	public final  boolean hasErrors() {
		return exceptions.size() != 0;
		
	}
	
	public final List<Exception> exceptions() {
		return Collections.unmodifiableList(exceptions);
	}

}
