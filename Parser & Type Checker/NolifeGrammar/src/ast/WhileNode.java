package ast;

import visitor.Visitor;

public class WhileNode extends BinaryNode {
	
	public WhileNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
