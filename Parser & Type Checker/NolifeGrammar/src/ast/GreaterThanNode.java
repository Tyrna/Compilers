package ast;

import visitor.Visitor;

public class GreaterThanNode extends BinaryNode {
	
	public GreaterThanNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}