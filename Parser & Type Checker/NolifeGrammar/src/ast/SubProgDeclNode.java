package ast;

import visitor.Visitor;

public class SubProgDeclNode extends ASTNode {
	
	public SubProgDeclNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
