package worms.programs.statements;

public abstract class SubStatement {
	
	protected StatementType type;
	protected Statement master;
	
	public Statement getMaster() {
		return this.master;
	}
	
	public StatementType getType() {
		return this.type;
	}
	
	public boolean isWellFormed() {
		return false;
	}
	
	public boolean containsAction() {
		return true;
	}
	
}
