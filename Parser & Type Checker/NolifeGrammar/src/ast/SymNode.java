package ast;

import visitor.Visitor;

public class SymNode extends ASTNode {
	
	public SymNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
