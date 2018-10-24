package ast;

import visitor.Visitor;

public class LessThanNode extends BinaryNode {
	
	public LessThanNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}