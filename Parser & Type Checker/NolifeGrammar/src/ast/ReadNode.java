package ast;

import visitor.Visitor;

public class ReadNode extends ASTNode {
	
	public ReadNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
