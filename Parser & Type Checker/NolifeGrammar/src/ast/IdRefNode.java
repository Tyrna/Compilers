package ast;

import visitor.Visitor;

public class IdRefNode extends ASTNode {
	
	public IdRefNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
