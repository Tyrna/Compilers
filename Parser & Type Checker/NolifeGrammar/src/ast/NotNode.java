package ast;

import visitor.Visitor;

public class NotNode extends ASTNode {
	
	public NotNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
