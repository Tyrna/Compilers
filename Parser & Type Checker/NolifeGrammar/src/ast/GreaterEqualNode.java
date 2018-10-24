package ast;

import visitor.Visitor;

public class GreaterEqualNode extends BinaryNode {
	
	public GreaterEqualNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}