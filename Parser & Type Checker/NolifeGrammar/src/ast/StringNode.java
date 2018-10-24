package ast;

import visitor.Visitor;

public class StringNode extends ASTNode {

	public StringNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
