package worms.programs.expressions;


public class Expression {
	
	/**
	 * Constructor for an Expression. Defaults subExpression to a null object, since it is too complicated
	 * or too much of a hassle to inclue the construction of the subexpression in this constructor
	 * @param targetLine
	 * @param targetColumn
	 */
	public Expression(int targetLine, int targetColumn){
		this.line = targetLine;
		this.column = targetColumn;
		this.subExpression = null;
	}
		
	
	private final int line, column; 
	private SubExpression subExpression;
	
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
	public static boolean isValidExpression(Expression expression) {
		if(expression == null){
			return false;
		}
		else {return true;}
	}
	
	/**
	 * Checks if the Expression has innitialized a SubExpression (ie the subexpression it stores is not a null object)
	 * @return
	 * 		| result == (this.getSubExpression() != null)
	 */
	public boolean hasSubExpression() {
		return (this.getSubExpression() != null);
		
	}
	
	/**
	 * Retruns a string with the name of the stored subExpression's type
	 * @return
	 * 		| if(hasSubExpression()){ result == this.getSubExpression().getType() }
	 * 		| else result == "null"
	 */
	public String getSubExpressionType() {
		if(hasSubExpression()){
			return this.getSubExpression().getType();
		} else {
			return "null";
		}
	}
	
	/**
	 * returns the subexpression stored
	 * @return
	 * 		| result == this.subExpression
	 */
	public SubExpression getSubExpression() {
		return this.subExpression;
	}
	
	/**
	 * 
	 * @param target
	 */
	public void createSubExpressionDoubleLiteral(double target){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleLiteral(target, this);
		}
	}
	
	
	/**
	 * DONE
	 * Class for double literal expressions. 
	 * @author Joren
	 * @invar the value stored is always smaller than Double.MAX_VALUE and greater than Double.MIN_VALUE.
	 * 		| (value >  Double.MIN_VALUE) && (value < Double.MIN_VALUE)
	 *
	 */

	public class DoubleLiteral extends SubExpression{
		
		private double value;
		
		public DoubleLiteral(double target, Expression targetMaster){
			if(isLegalValue(target)){
				this.value = target;
			}
			master = targetMaster;
		}
		
		/**
		 * setter for the value of target
		 * @param target
		 * 		the value to be stored by the litteral.
		 */
		public void setValue(double target) {
			if(isLegalValue(target)){
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
		
		public String getType(){
			return "DoubleLiteral";
		}
	}
	/**
	 * DONE
	 *Class for Boolean literal expressions
	 * @author Joren
	 *
	 */
	public class BooleanLiteral extends SubExpression{
		
		private boolean value;
		
		public BooleanLiteral(boolean bool, Expression targetMaster) {
			this.master = targetMaster;
			this.value = bool;
		}
		
		public void setValue(boolean target) {
			this.value = target;
		}
		
		public boolean getValue() {
			return this.value;
		}
		
		public String getType() {
			return "BooleanLiteral";
		}
	}
	
	/**
	 * DONE
	 * Class for Logical And expressions with two childexpressions. And expressions with more than two childexpressions are built of trees.
	 * @author Joren
	 * 
	 * The value of the And will be false in all cases except where the two expressions return booleans and are true.
	 *
	 * Might be interesting to at some point let the constructor decide what should go left and right for optimal excecution time (checking
	 * will stop if the left argument is false)
	 *
	 */
	public class LogicAnd extends SubExpressionLogic{
				
		/**
		 * Constructor for a Logical and, The value is false in all cases except when two expressions of the same type and value are given.
		 * @param left
		 * 		the left expression of the And
		 * @param right
		 * 		the right expression of the And
		 */
		public LogicAnd(Expression first, Expression second, Expression targetMaster){
			this.master = targetMaster;
			this.left = first;
			this.right = second;
			preConValue = areLegalExpressionsLogic(first, second);
		}
					
		/**
		 * returns the value of the and. Always false, except when the subexpressions in the and are both of the BooleanLiteral type
		 * and both are true.
		 * @return
		 * 		| result == (this.getPreConValue() && (getLeftValue() && getRightValue()))
		 */
		public boolean getValue() {
			return this.getPreConValue() && (getLeftValue() && getRightValue());
		}
		
		/** 
		 * Returns the type of this subexpression, in this case "LogicAnd"
		 * @return
		 * 		| result == "LogicAnd"
		 */
		public String getType(){
			return "LogicAnd";
		}
	}
	
	/**
	 * DONE
	 * Class for the LogicOr. Equal to LogicAnd in almost all aspects except for getValue and getType.
	 * @author Joren
	 *
	 */
	public class LogicOr extends SubExpressionLogic{
		
		/**
		 * Physically identical to the constructor of LogicAnd
		 * @param first
		 * 		the first of the two actual parameters for the logical or
		 * @param second
		 * 		the second of the two actual parameters for the logical or 
		 * @param targetMaster
		 * 		the parent Expression.
		 */
		public LogicOr(Expression first, Expression second, Expression targetMaster) {
			this.master = targetMaster;
			this.left = first;
			this.right = first;
			preConValue = areLegalExpressionsLogic(first, second);
		}
		
		/**
		 * returns the value of the or. 
		 * False if the initialzation was not a legal one (IE both expressions were not for the type "BooleanLiteral"
		 * True if they were both of the type "BooleanLiteral" and at least one of them is true.
		 * @return
		 * 		| result == (getPreConValue() && (getLeftValue() || getRightValue()))
		 */
		public boolean getValue() {
			return (getPreConValue() && (getLeftValue() || getRightValue()));
		}
		
		/** 
		 * Returns the type of this subexpression, in this case "LogicOr"
		 * @return
		 * 		| result == "LogicOr"
		 */
		public String getType(){
			return "LogicOr";
		}
	}
	
		
		
}

