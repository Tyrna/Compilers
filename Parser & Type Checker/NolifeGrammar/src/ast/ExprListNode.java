package ast;

import visitor.Visitor;

public class ExprListNode extends ASTNode {
	
	public ExprListNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
