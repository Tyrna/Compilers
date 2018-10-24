package ast;

import visitor.Visitor;

public class SubProgDeclNode extends ASTNode {
	
	public SubProgDeclNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
