package worms.programs.expressions;

public abstract class SubExpressionDoubleMathematical extends
		SubExpressionDoubleOp {
	
	public double getLeftValue() {
		if(getLeftExpression().getSubExpressionReturnType() != "DoubleLiteral") {
			return Double.NaN;
		} else {
			return ((DoubleLiteral) getLeftExpression().getSubExpression()).getValue();
		}
	}
	
	public double getRightValue() {
		if(getRightExpression().getSubExpressionReturnType() != "DoubleLiteral") {
			return Double.NaN;
		} else {
			return ((DoubleLiteral) getRightExpression().getSubExpression()).getValue();
		}
	}
	
	public String getReturnType() {
		return "DoubleLiteral"
	}

}
