package ast;

import visitor.Visitor;

public class ProcNode extends ASTNode {
	
	public ProcNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
