package ast;

import visitor.Visitor;

public class ExprListNode extends ASTNode {
	
	public ExprListNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
