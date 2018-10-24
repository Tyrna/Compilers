package ast;

import visitor.Visitor;

public class CallNode extends ASTNode {
	
	public CallNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
