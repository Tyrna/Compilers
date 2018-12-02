package ast;

import visitor.Visitor;

public class IntTypeNode extends ASTNode {

	public IntTypeNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
