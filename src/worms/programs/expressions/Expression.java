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
	 * Gives the returntype for the subExpression (so we can "stack" expressions into a tree)
	 * @return If the Expression has a subtype, it will pass the request on to the subtype. 
	 *			Else, it will return the string containing "null".
	 *		| if(hasSubExpression()) 
	 *		|	result == this.getSubExpression().getReturnType()
	 *		| else result == "null"
	 */
	public String getSubExpressionReturnType() {
		if(hasSubExpression()){
			return this.getSubExpression().getReturnType();
		} else {
			return "null";
		}
	}
	
	/**
	 * returns the subexpression stored by the Expressionobject the method is called upon.
	 * @return
	 * 		| result == this.subExpression
	 */
	public SubExpression getSubExpression() {
		return this.subExpression;
	}
	
	/**
	 * Method to create a subexpressio of the type DoubleLiteral, if the expression it is called upon does not yet have a subexpression.
	 * Else it will ignore the request
	 * @param target
	 * 		the target value for the DoubleLiteral.
	 * @post if the Expression did not yet have a subexpression at the time of the methodcall, it will now have a DoubleLitarl as subexpression
	 * 		of which the value is equal to target.
	 * 		| if(old.hasSubExpression()) {
	 * 		| 	new.getSubExpression == old.getSubExpression
	 * 		| else {
	 * 		|	( new.getSubExpressionType() == "DoubleLiteral"	) 
	 */
	public void createSubExpressionDoubleLiteral(double target){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleLiteral(target, this);
		}
	}
	
	/**
	 * Method for creating a BooleanLiteral subexpression, with the value of target, if the expression the method is called upon does 
	 * not yet have a subexpression. Otherwise the request is simply ignored.
	 * @param target
	 * 		the boolean value to be stored by the BooleanLiteral.
	 * @post if the expression did not yet have a subexression, it will now store a BooleanLiteral with value target.
	 * 		Else nothing has changed
	 * 		| if(hasSubExpression()){
	 * 		|	new.getSubExpression() == old.getSubExpression() }
	 * 		| else {
	 * 		|	this.getSubExpressionType() == "BooleanLIteral" &&
	 * 		| 	this.getSubExpresison().getValue() == target }
	 */
	public void createSubExpressionBooleanLiteral(boolean target) {
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new BooleanLiteral(target, this);
		}
	}
	
	/**
	 * Method for creating a subExpression of the type LogicAnd, if the Expression it is called upon did not yet have a subexpression.
	 * If it did, nothing is done.
	 * @param first 
	 * 			the first expression to be part of the LogicalAnd. Can be of any type.
	 * @param second
	 * 			the second expression to be part of the LogicalAnd. Can be of any type.
	 * @post
	 * 		if the expression did not yet have a subexpression, it will now store a LogicAnd with parameters first and second.
	 * 		The result stored by this expression will be false in all cases except when both parameters given to have a returnType of BooleanLiteral, 
	 * 		and both of these BooleanLiterals are true.
	 * 		| if(old.hasSubExpression()){
	 * 		| 	new.getSubExpression() == old.getSubExpression()}
	 * 		| else {
	 * 		| 	new.getSubExpressionType() == "LogicAnd"
	 */
	public void createSubExpressionLogicAnd(Expression first, Expression second) {
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new LogicAnd(first, second, this);
		}
	}
	
	/**
	 * Method for creating a subExpression of the type LogicOrd, if the Expression it is called upon did not yet have a subexpression.
	 * If it did, nothing is done.
	 * @param first 
	 * 			the first expression to be part of the LogicalOr. Can be of any type.
	 * @param second
	 * 			the second expression to be part of the LogicalOr. Can be of any type.
	 * @post
	 * 		if the expression did not yet have a subexpression, it will now store a LogicOr with parameters first and second.
	 * 		The result stored by this expression will be false in all cases except when both parameters given to have a returnType of BooleanLiteral, 
	 * 		and one of these Booleans results is true
	 * 		| if(old.hasSubExpression()){
	 * 		| 	new.getSubExpression() == old.getSubExpression()}
	 * 		| else {
	 * 		| 	new.getSubExpressionType() == "LogicOr"
	 */
	public void createSubExpressionLogicOr(Expression first, Expression second) {
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new LogicOr(first, second, this);
		}
	}
	
	/**
	 * Creates a new SubExpression of the type LogicNot with parameter first, if the Expression it is called upon does not yet have a SubExpresison.
	 * If it does, It ignores the request.
	 * @param first
	 * 		The Parameter to be stored and used by the expression.
	 * @post If the Expression had a SubExpression already, nothing changed.
	 * 		Else, the Expression now stores a LogicNot with parameter first.
	 * 		| if(old.hasSubExpression()){
	 * 		|		new.getSubExpresion() == old.getSubExpresion()
	 *		| else {
	 *		|		new.getSubExpression().getType() == "LogicNot"
	 * 
	 */
	public void createSubExpressionLogicNot(Expression first) {
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new LogicNot(first, this);
		}
	}
	
	/**
	 * 
	 * @param first
	 * @param second
	 */
	public void createSubExpressionDoubleLessThan(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleLessThan(first, second);
		}
	}
	
	public void createSubExpressionDoubleGreaterThan(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleGreaterThan(first, second);
		}
	}
	
	public void createSubExpressionDoubleLessThanOrEqualTo(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleLessThanOrEqualTo(first, second);
		}
	}
	
	public void createSubExpressionDoubleGreaterThanOrEqualTo(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleGreaterThanOrEqualTo(first, second);
		}
	}
	
	public void createSubExpressionDoubleEquality(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleEquality(first, second);
		}
	}
	
	public void createSubExpressionDoubleInequality(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleInequality(first, second);
		}
	}
	
	public void createSubExpressionDoubleAddition(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleAddition(first, second);
		}
	}
	
	public void createSubExpressionDoubleSubtraction(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleSubtraction(first, second);
		}
	}
	
	public void createSubExpressionDoubleMultiplication(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleMultiplication(first, second);
		}
	}
	
	public void createSubExpressionDoubleDivision(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleDivision(first, second);
		}
	}
	
	public void createSubExpressionDoubleSquareRoot(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleSquareRoot(first, second);
		}
	}
	
	public void createSubExpressionDoubleSine(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleSine(first, second);
		}
	}
	
	public void createSubExpressionDoubleCosine(Expression first, Expression second){
		if(hasSubExpression()){
			return;
		} else {
			subExpression = new DoubleCosine(first, second);
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
		
		public String getReturnType(){
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
		
		public String getReturnType() {
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
			setLegalExpressionsLogic();
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
		
		public String getReturnType() {
			return "BooleanLiteral";
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
			setLegalExpressionsLogic();
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
		
		public String getReturnType(){
			return "BooleanLiteral";
		}
	}
	
	
	/**
	 * DONE
	 * Class for the logic Not. Still very similar to the LogicAnd and LogicOr classes. Main difference is that it has only one argument.
	 * @author Joren
	 *
	 */
	public class LogicNot extends SubExpressionLogic {
		
		/**
		 * Constructor for the LogicNot Expression. 
		 * @param parameter
		 * 		the actual logical value to be worked with.
		 * @param targetMaster
		 * 		the master of the class.
		 */
		public LogicNot(Expression parameter, Expression targetMaster) {
			this.master = targetMaster;
			this.left = parameter;
			this.right = parameter; //don't really know what to do with this... 
			setLegalExpressionsLogic(parameter);
		}
		
		/**
		 * getter for the value of the expression. True always unless the parameter is of a BooleanLiteral type and true.
		 * @return
		 * 		| result == !getLeftValue()
		 */
		public boolean getValue() { //HOW DO WE HANDLE PRECON?
			return !(getLeftValue());
		}
		
		/** 
		 * getter for the type. Returns "LogicNot".
		 */
		public String getType(){
			return "LogicNot";
		}
		
		public String getReturnType(){
			return "BooleanLiteral";
		}
	
	}
	
	public class DoubleLessThan extends SubExpressionDoubleCompare {
		
		public DoubleLessThan(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() < (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleLessThan";
		}
	
	}
	
	public class DoubleGreaterThan extends SubExpressionDoubleCompare {
		
		public DoubleGreaterThan(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() > (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleGreaterThan";
		}
	}
		
	public class DoubleLessThanOrEqualTo extends SubExpressionDoubleCompare {
		
		public DoubleLessThanOrEqualTo(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() <= (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleLessThanOrEqualTo";
		}
	}
	
	public class DoubleGreaterThanOrEqualTo extends SubExpressionDoubleCompare {
		
		public DoubleGreaterThanOrEqualTo(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() >= (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleGreaterThanOrEqualTo";
		}
	}
	
	public class DoubleEquality extends SubExpressionDoubleCompare {
		
		public DoubleEquality(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() == (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleEquality";
		}
	}

	public class DoubleInequality extends SubExpressionDoubleCompare {
	
		public DoubleInequality(Expression first, Expression second){
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public boolean getValue() {
			if(!getPreConValue()){
				return false;
			} else {
				return ((double)left.getSubExpression().getValue() != (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleInequality";
		}
	}
	
	public class DoubleAddition extends SubExpressionDoubleMathematical {
		
		public DoubleAddition(Expression first, Expression second) {
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return ((double)left.getSubExpression().getValue() + (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleAddition";
		}
	}
	
	public class DoubleSubtraction extends SubExpressionDoubleMathematical {
		
		public DoubleSubtraction(Expression first, Expression second) {
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return ((double)left.getSubExpression().getValue() - (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleSubtraction";
		}
	}

	public class DoubleMultiplication extends SubExpressionDoubleMathematical {
		
		public DoubleMultiplication(Expression first, Expression second) {
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return ((double)left.getSubExpression().getValue() * (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleMultiplication";
		}
	}
	
	public class DoubleDivision extends SubExpressionDoubleMathematical {
		
		public DoubleDivision(Expression first, Expression second) {
			this.left = first;
			this.right = second;
			setHasLegalArguments();
		}
		
		@Override
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return ((double)left.getSubExpression().getValue() / (double)right.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleDivision";
		}
	}
	
	public class DoubleSquareRoot extends SubExpressionDoubleMathematical {
		
		public DoubleSquareRoot(Expression first){
			this.left = first;
			this.right = first;
			setHasLegalArguments();
		}
		
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return Math.sqrt((double)left.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleSquareRoot";
		}
	}
	
	public class DoubleSine extends SubExpressionDoubleMathematical {
		
		public DoubleSine(Expression first){
			this.left = first;
			this.right = first;
			setHasLegalArguments();
		}
		
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return Math.sin((double)left.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleSine";
		}
	}
	
	public class DoubleCosine extends SubExpressionDoubleMathematical {
		
		public DoubleCosine(Expression first){
			this.left = first;
			this.right = first;
			setHasLegalArguments();
		}
		
		public double getValue() {
			if(!getPreConValue()){
				return Double.NaN;
			} else {
				return Math.cos((double)left.getSubExpression().getValue());
			}
		}
		
		public String getType() {
			return "DoubleCosine";
		}
	}
	
	
}

