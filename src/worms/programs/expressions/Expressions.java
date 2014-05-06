package worms.programs.expressions;

import java.beans.Expression;

public class Expressions {
	
	private static int line, column; 
	
	/**
	 * Getter for the line variable
	 * @return line
	 * 		the variable for line.
	 */
	@Immutable
	public int getLine() {
		return this.line;		
	}
	
	/**
	 * Getter for the column variable
	 * @return column
	 * 		the value for column
	 */
	@Immutable
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Checks the validity of an expression - most rudementary form - will most likely become useless at some point
	 * @param exp
	 * 		Expression to be checked
	 * @return
	 * 		if the expression is a null object
	 * 		| return (exp == null)
	 */
	public static boolean isValidExpression(Expression exp) {
		if(exp == null){
			return false;
		}
		else {return true;}
	}
	
	//TODO IMPLEMENT
	public boolean hasSubExpression() {
		return false;
		
	}
	
	//TODO IMPLEMENT
	public String getSubExpressionType() {
		return " ";
	}
	
	/**
	 * Class for double literal expressions. 
	 * @author Joren
	 * @invar the value stored is always smaller than Double.MAX_VALUE and greater than Double.MIN_VALUE.
	 * 		| (value >  Double.MIN_VALUE) && (value < Double.MIN_VALUE)
	 *
	 */

	public class DoubleLiteral {
		
		private double value;
		
		public DoubleLiteral(double target){
			if(isLegalValue(target)){
				this.value = target;
			}
			else {throw new IlligalArgumentException<E>};
		}
		
		/**
		 * setter for the value of target
		 * @param target
		 * 		the value to be stored by the litteral.
		 */
		public void setValue(double target) {
			if(isLegalValue(target){
				this.value = target;
			}
			else {return;}
		}
		
		/**
		 * Getter for the value
		 * @return
		 * 		the value
		 * 		| result == this.value
		 */
		public double getValue() {
			return this.value;
		}
		
		public boolean isLegalValue(double target) {
			if(target != target){
				return false;
			}
			if(target == Double.MAX_VALUE){
				return false;
			}
			if(target == Double.MIN_VALUE){
				return false;
			}
			else {
				return true;
			}
		}
	}
	/**
	 *Class for Boolean literal expressions
	 * @author Joren
	 *
	 */
	public class BooleanLiteral {
		
		private boolean value;
		
		public BooleanLiteral(boolean bool) {
			this.value = bool;
		}
		
		public void setValue(boolean target) {
			this.value = target;
		}
		
		public boolean getValue() {
			return this.value;
		}
	}
	
	/**
	 * Class for Logical And expressions with two childexpressions. And expressions with more than two childexpressions are built of trees.
	 * @author Joren
	 *
	 */
	public class LogicAnd {
			
		private boolean value;
		private Expression left, right;
		
		/**
		 * Constructor for a Logical and, The value is false in all cases except when two expressions of the same type and value are given.
		 * @param left
		 * 		the left expression of the And
		 * @param right
		 * 		the right expression of the And
		 */
		public LogicAnd(Expressions first, Expressions second){
			this.left = first;
			this.right = second;
			if(!left.hasSubExpression()){
				this.value = false;
			}
			if(!right.hasSubExpression()){
				this.value = false;
			}
			if(left.getClass() != right.getClass()){
				this.value = false;
			}
			if(left == right) {
				this.value = true;
			}	else {
				this.value = false;
			}
			
			public boolean getValue() {
				return this.value;
			}
			
			public Expression getLeft() {
				return this.left;
			}
			
			public Expression getRight() {
				return this.right;
			}
			
			
			
	}
}
