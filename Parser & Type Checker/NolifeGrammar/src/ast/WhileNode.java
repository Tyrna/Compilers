package ast;

import visitor.Visitor;

public class WhileNode extends BinaryNode {
	
	public WhileNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
