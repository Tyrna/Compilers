package ast;

import visitor.Visitor;

public class LessThanNode extends BinaryNode {
	
	public LessThanNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}