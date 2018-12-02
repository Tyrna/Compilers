package ast;

import visitor.Visitor;

public class CaseLabelsNode extends ASTNode {
	
	public CaseLabelsNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
