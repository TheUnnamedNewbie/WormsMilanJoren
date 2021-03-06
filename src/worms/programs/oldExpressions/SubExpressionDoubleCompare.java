package worms.programs.oldExpressions;
import worms.programs.oldExpressions.Expression;

public abstract class SubExpressionDoubleCompare extends SubExpressionDoubleOp {
	
	public boolean getLeftValue() {
		if(getLeftExpression().getSubExpressionReturnType() != "DoubleLiteral") {
			return false;
		} else {
			return ((Expression.BooleanLiteral) getLeftExpression().getSubExpression()).getValue();
		}
	}
	
	public boolean getRightValue() {
		if(getRightExpression().getSubExpressionReturnType() != "DoubleLiteral") {
			return false;
		} else {
			return ((Expression.BooleanLiteral) getRightExpression().getSubExpression()).getValue();
		}
	}
	
	public String getReturnType() {
		return "BooleanLiteral";
	}
	
}
