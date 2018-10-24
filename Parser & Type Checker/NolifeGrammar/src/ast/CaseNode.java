package ast;

import visitor.Visitor;

public class CaseNode extends ASTNode {
	
	public CaseNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
