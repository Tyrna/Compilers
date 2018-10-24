package ast;

import visitor.Visitor;

public class ParamNode extends ASTNode {
	
	public ParamNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
