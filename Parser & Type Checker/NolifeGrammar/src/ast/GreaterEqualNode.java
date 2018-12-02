package ast;

import visitor.Visitor;

public class GreaterEqualNode extends BinaryNode {
	
	public GreaterEqualNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}