package worms.programs.oldExpressions;

import worms.programs.oldExpressions.Expression;
import worms.programs.oldExpressions.Expression.BooleanLiteral;

public abstract class SubExpressionLogic extends SubExpressionOp {

	public void setLegalExpressionsLogic(){
		if(!left.hasSubExpression()){
			preConValue = false;
		} else {
			if(!right.hasSubExpression()){
				preConValue = false;
			} else {
				if(left.getSubExpressionReturnType() != "BooleanLiteral"){
					preConValue = false;
				} else {
					if(right.getSubExpressionReturnType() != "BooleanLiteral"){
						preConValue = false;
					} else {
						preConValue = true;
					} 
				}
			}
		}
	}
	
	public void setLegalExpressionsLogic(Expression left){
		if(!left.hasSubExpression()){
			preConValue = false;
		} else {
				if(left.getSubExpressionReturnType() != "BooleanLiteral"){
					preConValue = false;
				} else {
						preConValue = true;
				} 
		}
	}
	
	/**
	 * Getter for the Value of the left Expression.
	 * @return
	 * 		|
	 */
	public boolean getLeftValue() {
		if(getLeftExpression().getSubExpressionReturnType() != "BooleanLiteral") {
			return false;
		} else {
			return ((BooleanLiteral) getLeftExpression().getSubExpression()).getValue();
		}
	}
	
	
	/**
	 * getter for the value of the right subexpression. Identical to that of the left one.
	 * @return
	 */
	public boolean getRightValue() {
		if(getRightExpression().getSubExpressionReturnType() != "BooleanLiteral") {
			return false;
		} else {
			return ((BooleanLiteral) getRightExpression().getSubExpression()).getValue();
		}
	}
	
	public String getReturnType() {
		return "BooleanLiteral";
	}
}
