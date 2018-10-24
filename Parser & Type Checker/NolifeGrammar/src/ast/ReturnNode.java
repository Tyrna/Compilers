package ast;

import visitor.Visitor;

public class ReturnNode extends ASTNode {
	
	public ReturnNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
