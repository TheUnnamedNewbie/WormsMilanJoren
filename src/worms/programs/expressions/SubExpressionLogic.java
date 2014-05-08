package worms.programs.expressions;

import worms.programs.expressions.Expression;
import worms.programs.expressions.Expression.BooleanLiteral;

public abstract class SubExpressionLogic extends SubExpression {

	
	protected boolean preConValue = false;
	protected Expression left, right;
	
	public boolean setLegalExpressionsLogic(Expression left, Expression right){
		if(!left.hasSubExpression()){
			preConValue = false;
		} else {
			if(!right.hasSubExpression()){
				preConValue = false;
			} else {
				if(left.getSubExpressionType() != "BooleanLiteral"){
					preConValue = false;
				} else {
					if(right.getSubExpressionType() != "BooleanLiteral"){
						preConValue = false;
					} else {
						preConValue = true;
					}
				}
			}
		}
	}
	
	/**
	 * getter for the value stored by the constructor. This is a value that is stored for total reasons. 
	 * If a LogicAnd is constructed with wrong/illegal initial expressions (no subexpressions or subexpressions are not of the BooleanLiteral type)
	 * this will be false and force all results later on to be false.
	 * @return
	 */
	public boolean getPreConValue() {
		return this.preConValue;
	}
	
	/**
	 * Getter for the left expression in the subExpression
	 * @return
	 */
	public Expression getLeftExpression() {
		return this.left;
	}
	
	/**
	 * Getter for the Value of the left Expression.
	 * @return
	 * 		|
	 */
	public boolean getLeftValue() {
		if(getLeftExpression().getSubExpressionType() != "BooleanLiteral") {
			return false;
		} else {
			return ((BooleanLiteral) getLeftExpression().getSubExpression()).getValue();
		}
	}
	
	/**
	 * getter for the right expression. Identical to that of the left getter.
	 * @return
	 */
	public Expression getRightExpression() {
		return this.right;
	}
	
	/**
	 * getter for the value of the right subexpression. Identical to that of the left one.
	 * @return
	 */
	public boolean getRightValue() {
		if(getRightExpression().getSubExpressionType() != "BooleanLiteral") {
			return false;
		} else {
			return ((BooleanLiteral) getRightExpression().getSubExpression()).getValue();
		}
	}
}
