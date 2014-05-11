package worms.programs.statements;


public class Statement {
	
	private int line, column;
	private SubStatement subStatement;
	
	public Statement(int ln, int clmn){
		this.line = ln;
		this.column = clmn;
		this.subStatement = null;
	}

}
