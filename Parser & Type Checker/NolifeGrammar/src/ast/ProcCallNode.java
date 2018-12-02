package ast;

import visitor.Visitor;

public class ProcCallNode extends ASTNode {
	
	public ProcCallNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
