package ast;

import visitor.Visitor;

public class FloatNode extends ASTNode {
	
	public FloatNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
