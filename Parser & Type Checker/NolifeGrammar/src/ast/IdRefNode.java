package ast;

import visitor.Visitor;

public class IdRefNode extends ASTNode {
	
	public IdRefNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
