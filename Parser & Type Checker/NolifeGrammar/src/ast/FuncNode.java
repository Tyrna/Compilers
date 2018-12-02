package ast;

import visitor.Visitor;

public class FuncNode extends ASTNode {
	
	protected int variables;
	
	public FuncNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
	
	public void setVars(int i) {
		variables = i;
	}
	
	public int getVars() {
		return variables;
	}

}
