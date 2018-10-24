package ast;

import visitor.Visitor;

public class IntTypeNode extends ASTNode {

	public IntTypeNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
