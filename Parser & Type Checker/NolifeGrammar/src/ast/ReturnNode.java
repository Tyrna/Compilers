package ast;

import visitor.Visitor;

public class ReturnNode extends ASTNode {
	
	public ReturnNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
