package ast;

import visitor.Visitor;

public class AndNode extends BinaryNode {
	
	public AndNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
