package worms.programs;

import worms.model.programs.ProgramFactory;



public class Factory implements ProgramFactory<E, S, T> {
	
	public E createnDoubleLiteral(int line, int column, double d) {
		return null;
	}
	
	public E createBooleanLiteral(int line, int column, boolean b) {
		return null;
	}
	
	public E createAnd(int line, int column, E e1, E e2) {
		return null;
	}
	
	public E createOr(int line, int column, E e1, E e2) {
		return null;
	}
}
