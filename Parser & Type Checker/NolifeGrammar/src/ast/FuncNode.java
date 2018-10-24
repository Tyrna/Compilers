package ast;

import visitor.Visitor;

public class FuncNode extends ASTNode {
	
	public FuncNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
