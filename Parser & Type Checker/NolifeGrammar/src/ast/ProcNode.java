package ast;

import visitor.Visitor;

public class ProcNode extends ASTNode {
	
	public ProcNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
