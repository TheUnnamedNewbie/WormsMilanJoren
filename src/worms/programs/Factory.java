package worms.programs;

import worms.model.programs.ProgramFactory;



public class Factory implements ProgramFactory<E, S, T> {
	
	//DONE
	public E createnDoubleLiteral(int line, int column, double d) {
		return null;
	}
	
	//DONE
	public E createBooleanLiteral(int line, int column, boolean b) {
		return null;
	}
	
	//DONE
	public E createAnd(int line, int column, E e1, E e2) {
		return null;
	}
	
	//DONE
	public E createOr(int line, int column, E e1, E e2) {
		return null;
	}
}
