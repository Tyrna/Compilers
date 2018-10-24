package ast;

import visitor.Visitor;

public class CaseStmtNode extends ASTNode {
	
	public CaseStmtNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
