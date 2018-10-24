package ast;

import visitor.Visitor;

public class LessEqualNode extends BinaryNode {
	
	public LessEqualNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}