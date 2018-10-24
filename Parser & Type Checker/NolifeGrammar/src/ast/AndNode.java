package ast;

import visitor.Visitor;

public class AndNode extends BinaryNode {
	
	public AndNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
