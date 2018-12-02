package ast;

import visitor.Visitor;

public class CaseNode extends ASTNode {
	
	public CaseNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
