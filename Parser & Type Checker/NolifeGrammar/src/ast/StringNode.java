package ast;

import visitor.Visitor;

public class StringNode extends ASTNode {

	public StringNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}
