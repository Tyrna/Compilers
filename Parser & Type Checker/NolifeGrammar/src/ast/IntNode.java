package ast;

import visitor.Visitor;

public class IntNode extends ASTNode {
	
	public IntNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
