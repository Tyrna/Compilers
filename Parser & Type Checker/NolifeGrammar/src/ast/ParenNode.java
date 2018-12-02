package ast;

import visitor.Visitor;

public class ParenNode extends ASTNode {
	
	public ParenNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
