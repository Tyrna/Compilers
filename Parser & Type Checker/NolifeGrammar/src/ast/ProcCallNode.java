package ast;

import visitor.Visitor;

public class ProcCallNode extends ASTNode {
	
	public ProcCallNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
