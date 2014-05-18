package worms.programs.Expressions;

import worms.programs.Expressions.SubExpression.*;
import worms.programs.types.TypeType;

public class Expression {
	public Expression(int line, int column) {
		this.line = line;
		this.column = column;
		this.subExpression = null;
	}
	
	private final int line, column;
	public SubExpression subExpression;
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public SubExpression getSubExpression() {
		return this.subExpression;
	}
	
	
	/**
	 * Creators for subexpressions, trivial but space/time consuming.
	 */
	
	//LITERAL
	public void createSubExpressionDoubleLiteral(Object content) {
		this.subExpression = new LiteralExpression(this, LiteralType.DOUBLE, content);
	}
	
	public void createSubExpressionBooleanLiteral(Object content) {
		this.subExpression = new LiteralExpression(this, LiteralType.BOOLEAN, content);
	}
	
	public void createSubExpressionEntityLiteral(Object content) {
		this.subExpression = new LiteralExpression(this, LiteralType.ENTITY, content);
	}
	
	public void createSubExpressionNullLiteral() {
		this.subExpression = new LiteralExpression(this, LiteralType.NULL, null);
	}
	
	public void createSubExpressionThisLiteral() {
		this.subExpression = new LiteralExpression(this, LiteralType.THIS, null);
	}
	
	//Boolean Operations
	public void createSubExpressionAnd(Expression left, Expression right) {
		this.subExpression = new BoolOpExpression(this, BoolOpType.AND, left, right);
	}
	
	public void createSubExpressionOr(Expression left, Expression right) {
		this.subExpression = new BoolOpExpression(this, BoolOpType.OR, left, right);
	}
	
	public void createSubExpressionNot(Expression content) {
		this.subExpression = new BoolOpExpression(this, BoolOpType.NOT, content, content);
	}
	
	//Double Operations
	public void createSubExpressionGreaterThan(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.GREATER, left, right);
	}
	
	public void createSubExpressionGreaterThanOrEqual(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.GREATEREQUAL, left, right);
	}
	
	public void createSubExpressionLessThan(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.LESSER, left, right);
	}
	
	public void createSubExpressionLessThanOrEqual(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.LESSEREQUAL, left, right);
	}
	
	public void createSubExpressionAddition(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.ADD, left, right);
	}
	
	public void createSubExpressionSubtraction(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.SUB, left, right);
	}
	
	public void createSubExpressionMultiplication(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.MUL, left, right);
	}
	
	public void createSubExpressionDivision(Expression left, Expression right) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.DIV, left, right);
	}
	
	public void createSubExpressionSquareRoot(Expression content) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.SQRT, content, content);
	}
	
	public void createSubExpressionSine(Expression content) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.SIN, content, content);
	}
	
	public void createSubExpressionCosine(Expression content) {
		this.subExpression = new DoubleOpExpression(this, DoubleOpType.COS, content, content);
	}
	
	//Entity Operations
	public void createSubExpressionGetX(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETX, content);
	}
	
	public void createSubExpressionGetY(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETY, content);
	}
	
	public void createSubExpressionGetRadius(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETRAD, content);
	}
	
	public void createSubExpressionGetOrientation(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETDIR, content);
	}
	
	public void createSubExpressionGetAP(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETAP, content);
	}
	
	public void createSubExpressionGetHP(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETHP, content);
	}
	
	public void createSubExpressionGetMaxAP(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETMAXAP, content);
	}
	
	public void createSubExpressionGetMaxHP(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.GETMAXHP, content);
	}
	
	public void createSubExpressionIsWorm(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.ISWORM, content);
	}
	
	public void createSubExpressionIsFood(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.ISFOOD, content);
	}
	
	public void createSubExpressionSearchObject(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.SEARCHOBJ, content);
	}
	
	public void createSubExpressionSameTeam(Expression content) {
		this.subExpression = new EntityOpExpression(this, EntityOpType.SAMETEAM, content);
	}
	
	//Equality Operator
	public void createSubExpressionDoubleEquality(Expression left, Expression right) {
		this.subExpression = new EqualityExpression(this, EqualityType.DOUBLE, left, right);
	}
	
	public void createSubExpressionBooleanEquality(Expression left, Expression right) {
		this.subExpression = new EqualityExpression(this, EqualityType.BOOLEAN, left, right);
	}
	
	public void createSubExpressionEntityEquality(Expression left, Expression right) {
		this.subExpression = new EqualityExpression(this, EqualityType.ENTITY, left, right);
	}
	
	public void createSubExpressionUncheckedEquality(Expression left, Expression right) {
		this.subExpression = new EqualityExpression(this, EqualityType.UNCHECKED, left, right);
	}
	
	//Variable Access
	public void createSubExpressionVaribleAccess(String variableName) {
		this.subExpression = new VariableAccessExpression(this, variableName);
	}
	
	
	//General constructor for Literal
	public void createSubExpressionXLiteral(LiteralType type, Object content) {
		this.subExpression = new LiteralExpression(this, type, content);
	}
	
	
	public TypeType getReturnType() {
		switch (getSubExpression().kind) {
		case LITERAL:
			switch (((LiteralExpression)getSubExpression()).type) {
			case ENTITY: case THIS:
				return TypeType.ENTITY;
			case BOOLEAN:
				return TypeType.BOOLEAN;
			case DOUBLE:
				return TypeType.DOUBLE;
			case NULL:
				return null;
			}
		case DOUBLEOP:
			switch (((DoubleOpExpression)getSubExpression()).type) {
			case SUB: case SIN: case ADD: case SQRT: case MUL: case DIV: case COS:
				return TypeType.DOUBLE;
			case GREATER: case GREATEREQUAL: case LESSER: case LESSEREQUAL:
			}
		case EQUALITY: case BOOLOP: case ENTITYOP:
			switch (((EntityOpExpression)getSubExpression()).type) {
			case SAMETEAM: case ISFOOD: case ISWORM:
				return TypeType.BOOLEAN;
			case SEARCHOBJ: return TypeType.ENTITY;
			case GETAP: case GETDIR: case GETHP: case GETMAXAP: case GETMAXHP: case GETRAD: case GETX: case GETY:
				return TypeType.DOUBLE;
			}
		case VARACCESS:
			return null; //This is difficult... I have absolutely no idea on how to get to this...
		default: return null; //because errors otherwise
		}
	}


	/**
	 * The classes which represent the kinds. Meant for containing information, not for calculating
	 * 
	 * NOTE: there are no getters for left/right.
	 * This is as to limit coding. It could be implemented, but I don't feel like it...
	 * 
	 * @author Milan
	 *
	 */
	
	public class LiteralExpression extends SubExpression {
		public LiteralExpression(Expression targetMaster, LiteralType type, Object content) {
			//complain here: type must match content.getClass()
			this.master = targetMaster;
			this.kind = ExpressionKind.LITERAL;
			this.type = type;
			this.content = content;
		}
		
		protected LiteralType type;
		protected final Object content;
		
		public LiteralType getType() {
			return this.type;
		}
		
		public Object getValue() {
			return this.content;
		}
		
	}
	
	public class BoolOpExpression extends SubExpression {
		public BoolOpExpression(Expression targetMaster, BoolOpType type, Expression left, Expression right) {
			//complain here! type must match return types of left/right (Implies more code to be written, not necessary yet)
			this.master = targetMaster;
			this.kind = ExpressionKind.BOOLOP;
			this.type = type;
			this.left = left;
			this.right = right;
		}
		
		private final BoolOpType type;
		public Expression left, right;
		
		public BoolOpType getType() {
			return this.type;
		}
	}
	
	public class DoubleOpExpression extends SubExpression {
		public DoubleOpExpression(Expression targetMaster, DoubleOpType type, Expression left, Expression right) {
			//complain here!
			this.master = targetMaster;
			this.kind = ExpressionKind.DOUBLEOP;
			this.type = type;
			this.left = left;
			this.right = right;
		}
		
		protected DoubleOpType type;
		public Expression left, right;
		
		public DoubleOpType getType() {
			return this.type;
		}
	}
	
	public class EntityOpExpression extends SubExpression {
		public EntityOpExpression(Expression targetMaster, EntityOpType type, Expression content) {
			//complain here
			this.master = targetMaster;
			this.kind = ExpressionKind.ENTITYOP;
			this.type = type;
			this.content = content;
		}
		
		protected EntityOpType type;
		public Expression content;
		
		public EntityOpType getType() {
			return this.type;
		}
	}
	
	public class EqualityExpression extends SubExpression {
		public EqualityExpression(Expression targetMaster, EqualityType type, Expression left, Expression right) {
			//complain here
			this.master = targetMaster;
			this.kind = ExpressionKind.EQUALITY;
			this.type = type;
			this.left = left;
			this.right = right;
		}
		
		protected EqualityType type;
		public Expression left, right;
		
		public EqualityType getType() {
			return this.type;
		}
	}
	
	public class VariableAccessExpression extends SubExpression {
		public VariableAccessExpression(Expression targetMaster, String variableName) {
			this.master = targetMaster;
			this.kind = ExpressionKind.VARACCESS;
			this.varname = variableName;
		}
		
		public String varname;
	}
}
