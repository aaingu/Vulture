package dev.vulture.uniform.expr.kroppeb.stareval.expression;

import dev.vulture.uniform.expr.kroppeb.stareval.function.FunctionContext;
import dev.vulture.uniform.expr.kroppeb.stareval.function.FunctionReturn;

import java.util.Collection;

public interface Expression {
	void evaluateTo(FunctionContext context, FunctionReturn functionReturn);

	default Expression partialEval(FunctionContext context, FunctionReturn functionReturn) {
		return this;
	}

	void listVariables(Collection<? super VariableExpression> variables);
}
