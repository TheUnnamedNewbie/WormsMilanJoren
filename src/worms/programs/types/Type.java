package worms.programs.types;

import worms.programs.expressions.Expression;

public class Type {
	public Type(TypeType type) {
		this.type = type;
		this.expression = null;
	}
	
	private TypeType type;
	private Expression expression;
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return this.expression;
	}
	
	public TypeType getType() {
		return this.type;
	}
}
