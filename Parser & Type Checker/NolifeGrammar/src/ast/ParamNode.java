package ast;

import visitor.Visitor;

public class ParamNode extends ASTNode {
	
	public ParamNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
