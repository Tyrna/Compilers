package ast;

import visitor.Visitor;

public class LessEqualNode extends BinaryNode {
	
	public LessEqualNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}