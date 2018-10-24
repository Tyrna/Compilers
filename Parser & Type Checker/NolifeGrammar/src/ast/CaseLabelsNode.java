package ast;

import visitor.Visitor;

public class CaseLabelsNode extends ASTNode {
	
	public CaseLabelsNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
