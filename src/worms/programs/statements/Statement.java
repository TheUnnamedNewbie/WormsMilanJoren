package worms.programs.statements;

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
	
	public boolean isWellFormed() {
		switch (subStatement.type) {
		case ACTION:
			return true;
		case ASSIGN:
			return true;
		case PRINT:
			return true;
		case IF: case SEQ:
			return false; //TODO ask the contained statement
		case WHILE: case FOR:
			return false; //Ask if it contains actions
		default: return false; //because errors otherwise
		}
	}
	
	public boolean containsAction() {
		switch (subStatement.type) {
		case ACTION:
			return true;
		case ASSIGN:
			return false;
		case PRINT:
			return false;
		case IF: case SEQ: case FOR: case WHILE:
			return true; //ask the contained statement
		default: return true; //Because errors otherwise
		}
	}
	
	//TESTING ON ACTION STATEMENT
	public void createSubStatementAction(String command, Object value) {
		this.subStatement = new ActionStatement(this, command, value);
	}
	
	public class ActionStatement extends SubStatement {
		
		public ActionStatement(Statement targetMaster, String command, Object value) {
			this.type = StatementType.ACTION;
			this.master = targetMaster;
		}
		
	}
}
