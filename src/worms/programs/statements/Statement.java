package worms.programs.statements;

import java.util.List;

import worms.model.programs.ProgramFactory;
import worms.programs.Expressions.Expression;
import worms.programs.types.TypeType;

public class Statement {
	
	public Statement(int line, int column) {
		this.line = line;
		this.column = column;
		this.subStatement = null;
	}
	
	private final int line, column;
	private SubStatement subStatement;
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public SubStatement getSubStatement() {
		return this.subStatement;
	}
	
	public boolean isWellFormed() {
		return subStatement.isWellFormed();
	}
	
	public boolean containsAction() {
		return subStatement.containsAction();
	}
	
	//ACTION
	public void createSubStatementAction(String commandName, Object value) {
		this.subStatement = new ActionStatement(this, commandName, value);
	}
	
	public class ActionStatement extends SubStatement {
		public ActionStatement(Statement targetMaster, String commandName, Object value) {
			this.type = StatementType.ACTION;
			this.master = targetMaster;
			this.commandName = commandName;
			this.value = value;
		}
		
		public final String commandName;
		public final Object value;
		
		public boolean isWellFormed() {
			return true;
		}
		
		public boolean containsAction() {
			return true;
		}
	}
	
	//SEQUENCE
	public void createSubStatementSequence(List<Statement> sequence) {
		this.subStatement = new SequenceStatement(this, sequence);
	}
	
	public class SequenceStatement extends SubStatement {
		public SequenceStatement(Statement targetMaster, List<Statement> sequence) {
			this.type = StatementType.SEQ;
			this.master = targetMaster;
			this.sequence = sequence;
		}
		
		private List<Statement> sequence;
		
		public Statement getFirstStatement() {
			return sequence.get(0);
		}
		
		public Statement getNextStatement(Statement previous) {
			int previousIndex = sequence.indexOf(previous);
			if (previousIndex == sequence.size()-1)
				return null; //This was the last element
			else if (previousIndex == -1)
				throw new IllegalArgumentException("Statement not in sequence"); //This shouldn't happen because internal method
			return sequence.get(previousIndex+1);
		}
		
		public boolean isWellFormed() {
			for (Statement statement: sequence)
				if (! statement.isWellFormed())
					return false;
			return true;
		}
		
		public boolean containsAction() {
			for (Statement statement: sequence)
				if (statement.containsAction())
					return true;
			return false;
		}
		
		public List<Statement> getSequence() {
			return this.sequence;
		}
	}
	
	//ASSIGN
	public void createSubStatementAssign(String varName, Expression expr) {
		this.subStatement = new AssignStatement(this, varName, expr);
	}
	
	public class AssignStatement extends SubStatement {
		public AssignStatement(Statement targetMaster, String varName, Expression expr) {
			this.master = targetMaster;
			this.type = StatementType.ASSIGN;
			this.varName = varName;
			this.expr = expr;
		}
		
		public String varName;
		public Expression expr;
		
		public boolean isWellFormed() {
			return true;
		}
		
		public boolean containsAction() {
			return false;
		}
	}
	
	//PRINT
	public void createSubStatementPrint(Expression output) {
		this.subStatement = new PrintStatement(this, output);
	}
	
	public class PrintStatement extends SubStatement {
		public PrintStatement(Statement targetMaster, Expression output) {
			this.master = targetMaster;
			this.type = StatementType.PRINT;
			this.output = output;
		}
		
		public Expression output;
		
		public boolean isWellFormed() {
			return true;
		}
		
		public boolean containsAction() {
			return false;
		}
	}
	
	//IF
	public void createSubStatementIf(Expression condition, Statement ifthen, Statement ifelse) {
		//COMPLAINT//assert condition.getReturnType() == TypeType.BOOLEAN;
		this.subStatement = new IfStatement(this, condition, ifthen, ifelse);
	}
	
	public class IfStatement extends SubStatement {
		public IfStatement(Statement targetMaster, Expression condition, Statement ifthen, Statement ifelse) {
			this.master = targetMaster;
			this.type = StatementType.IF;
			this.condition = condition;
			this.ifthen = ifthen;
			this.ifelse = ifelse;
		}
		
		public Expression condition;
		public Statement ifthen, ifelse;
		
		public boolean isWellFormed() {
			return (ifthen.getSubStatement().isWellFormed() && ifelse.getSubStatement().isWellFormed());
		}
		
		public boolean containsAction() {
			return (ifthen.getSubStatement().containsAction() || ifelse.getSubStatement().containsAction());
		}
	}
	
	//WHILE
	public void createSubStatementWhile(Expression condition, Statement body) {
		//COMPLAINT//assert condition.getReturnType() == TypeType.BOOLEAN;
		this.subStatement = new WhileStatement(this, condition, body);
	}
	
	public class WhileStatement extends SubStatement {
		public WhileStatement(Statement targetMaster, Expression condition, Statement body) {
			this.master = targetMaster;
			this.type = StatementType.WHILE;
			this.condition = condition;
			this.body = body;
		}
		
		public Expression condition;
		public Statement body;
		
		public boolean isWellFormed() {
			return !containsAction();
		}
		
		public boolean containsAction() {
			return body.containsAction();
		}
	}
	
	//FOR
	public void createSubStatementFor(ProgramFactory.ForeachType fortype, String variableName, Statement body) {
		this.subStatement = new ForStatement(this, fortype, variableName, body);
	}
	
	public class ForStatement extends SubStatement {
		public ForStatement(Statement targetMaster, ProgramFactory.ForeachType fortype, String variableName, Statement body) {
			this.master = targetMaster;
			this.type = StatementType.FOR;
			this.fortype = fortype;
			this.variableName = variableName;
			this.body = body;
		}
		
		public ProgramFactory.ForeachType fortype;
		public String variableName;
		public Statement body;
		
		public boolean isWellFormed() {
			return !containsAction();
		}
		
		public boolean containsAction() {
			return body.containsAction();
		}
	}
}
