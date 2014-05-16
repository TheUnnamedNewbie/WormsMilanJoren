package worms.programs.Expressions;

/**
 * SubExpression contains stuff for identifying the kind of expression and the main expression to which it links
 * 
 * The nested enumerations are for identifying the operation ProgrammedWorm must make
 * @author Milan
 *
 */
public abstract class SubExpression {
	
	protected Expression master;
	protected ExpressionKind kind;
	
	public Expression getMaster() {
		return this.master;
	}
	
	public ExpressionKind getKind() {
		return this.kind;
	}
	
	
	/* The different types which a subexpression can be, categorized by kind */
	
	public enum LiteralType {
		BOOLEAN, DOUBLE, ENTITY, THIS, NULL;
	}
	
	public enum BoolOpType {
		AND, OR, NOT;
	}
	
	public enum DoubleOpType {
		GREATER, GREATEREQUAL, LESSER, LESSEREQUAL, ADD, SUB, MUL, DIV, SQRT, SIN, COS;
	}
	
	public enum EntityOpType {
		GETX, GETY, GETRAD, GETDIR, GETAP, GETMAXAP, GETHP, GETMAXHP, SAMETEAM, SEARCHOBJ, ISWORM, ISFOOD;
	}
	
	public enum EqualityType {
		BOOLEAN, DOUBLE, ENTITY, UNCHECKED;
	}
}
