package ast;

import visitor.Visitor;

public class CharNode extends ASTNode {
	
	public CharNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}