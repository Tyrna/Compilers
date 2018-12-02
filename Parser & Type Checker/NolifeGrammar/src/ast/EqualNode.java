package ast;

import visitor.Visitor;

public class EqualNode extends BinaryNode {
	
	public EqualNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}