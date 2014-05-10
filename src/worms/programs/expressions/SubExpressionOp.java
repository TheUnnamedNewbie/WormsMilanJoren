package worms.programs.expressions;

import worms.programs.expressions.SubExpression;

public abstract class SubExpressionOp extends SubExpression {

	protected boolean preConValue = false;
	protected Expression left, right;
	
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
	 * getter for the right expression. Identical to that of the left getter.
	 * @return
	 */
	public Expression getRightExpression() {
		return this.right;
	}
	
}


