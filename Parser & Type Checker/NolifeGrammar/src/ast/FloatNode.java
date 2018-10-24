package ast;

import visitor.Visitor;

public class FloatNode extends ASTNode {
	
	public FloatNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
