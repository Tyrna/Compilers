package ast;

import visitor.Visitor;

public class NotNode extends ASTNode {
	
	public NotNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
