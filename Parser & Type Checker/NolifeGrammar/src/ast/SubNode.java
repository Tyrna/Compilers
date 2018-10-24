package ast;

import visitor.Visitor;

public class SubNode extends BinaryNode {

	public SubNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
