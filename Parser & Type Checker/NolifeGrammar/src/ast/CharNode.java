package ast;

import visitor.Visitor;

public class CharNode extends ASTNode {
	
	public CharNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}