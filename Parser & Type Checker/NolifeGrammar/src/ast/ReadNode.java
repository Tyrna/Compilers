package ast;

import visitor.Visitor;

public class ReadNode extends ASTNode {
	
	public ReadNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
