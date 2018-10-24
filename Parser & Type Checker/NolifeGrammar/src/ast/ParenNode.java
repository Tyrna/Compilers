package ast;

import visitor.Visitor;

public class ParenNode extends ASTNode {
	
	public ParenNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
