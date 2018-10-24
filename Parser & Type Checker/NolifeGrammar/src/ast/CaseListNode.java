package ast;

import visitor.Visitor;


public class CaseListNode extends ASTNode {
	
	public CaseListNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
