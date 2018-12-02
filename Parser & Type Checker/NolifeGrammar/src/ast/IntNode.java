package ast;

import visitor.Visitor;

public class IntNode extends ASTNode {
	
	public IntNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
