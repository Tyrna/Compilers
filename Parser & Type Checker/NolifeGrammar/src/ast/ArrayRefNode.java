package ast;

import visitor.Visitor;

public class ArrayRefNode extends ASTNode {

	public ArrayRefNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
