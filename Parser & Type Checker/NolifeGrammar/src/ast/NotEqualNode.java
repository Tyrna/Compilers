package ast;

import visitor.Visitor;

public class NotEqualNode extends BinaryNode {
	
	public NotEqualNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}