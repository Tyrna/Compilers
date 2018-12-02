package ast;

import visitor.Visitor;

public class CaseStmtNode extends ASTNode {
	
	public CaseStmtNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
