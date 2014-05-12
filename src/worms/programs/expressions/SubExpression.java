package worms.programs.expressions;

import worms.entities.Entity;
import worms.programs.expressions.Expression;

public abstract class SubExpression {
	
	protected Expression master;
	
	public Expression getMaster(){
		return master; //THIS MIGHT BETTER BE REPLACED BY A CLONE, REQUIRES A RECURSIVE CLONE TO BE DEFINED WITHIN EXPRESSION (SO WE GET DEEP CLONES)
	}
	
	public String getType(){
		return "SubExpression";
	}
	
	public String getReturnType(){
		return "null";
	}

	public Object getValue(){
		return null;
	}
}
