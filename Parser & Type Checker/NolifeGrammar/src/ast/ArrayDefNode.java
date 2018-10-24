package ast;

import visitor.Visitor;

public class ArrayDefNode extends ASTNode {
	
	public ArrayDefNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
