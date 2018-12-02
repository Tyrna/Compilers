package ast;

import visitor.Visitor;


public class CaseListNode extends ASTNode {
	
	public CaseListNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
