package ast;

import visitor.Visitor;

public class CallNode extends ASTNode {
	
	public CallNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
