package ast;

import visitor.Visitor;

public class OrNode extends BinaryNode {
	
	public OrNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
