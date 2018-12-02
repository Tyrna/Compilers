package ast;

import visitor.Visitor;

public class SubNode extends BinaryNode {

	public SubNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
