package ast;

import visitor.Visitor;

public class OrNode extends BinaryNode {
	
	public OrNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
