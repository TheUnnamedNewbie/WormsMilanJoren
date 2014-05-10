package worms.programs.expressions;


public abstract class SubExpressionDoubleOp extends SubExpressionOp {
	
	public void setHasLegalArguments(){
		if(!left.hasSubExpression()){
			preConValue = false;
		} else {
			if(!right.hasSubExpression()){
				preConValue = false;
			} else {
				if(left.getSubExpressionReturnType() != "DoubleLiteral"){
					preConValue = false;
				} else {
					if(right.getSubExpressionReturnType() != "DoubleLiteral"){
						preConValue = false;
					} else {
						preConValue = true;
					}
				}
			} 
		}
	}
	
	public boolean getHasLegalArguments() {
		return this.preConValue;
	}
	
}
