package ast;

import visitor.Visitor;

public class SymNode extends ASTNode {
	
	public SymNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
