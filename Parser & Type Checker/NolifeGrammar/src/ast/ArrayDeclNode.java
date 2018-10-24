package ast;

import visitor.Visitor;

public class ArrayDeclNode extends ASTNode {
	
	public ArrayDeclNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
