package ast;

import visitor.Visitor;

public class IntTypeNode extends ASTNode {

	public IntTypeNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
