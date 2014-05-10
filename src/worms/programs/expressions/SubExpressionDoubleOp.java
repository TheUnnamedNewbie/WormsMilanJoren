package worms.programs.expressions;

public abstract class SubExpressionDoubleOp extends SubExpression {

	protected Expression left, right;
	protected boolean hasLegalArguments;
	
	public void setHasLegalArguments(Expression left, Expression right){
		if(!left.hasSubExpression()){
			hasLegalArguments = false;
		} else {
			if(!right.hasSubExpression()){
				hasLegalArguments = false;
			} else {
				if(left.getSubExpressionType() != "DoubleLiteral")
			}
		}
	}
	
}
