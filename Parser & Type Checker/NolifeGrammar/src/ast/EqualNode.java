package ast;

import visitor.Visitor;

public class EqualNode extends BinaryNode {
	
	public EqualNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}