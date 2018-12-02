package ast;

import visitor.Visitor;

public class IfStmtNode extends BinaryNode {
	
	public IfStmtNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
