package ast;

import visitor.Visitor;

public class GreaterThanNode extends BinaryNode {
	
	public GreaterThanNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}