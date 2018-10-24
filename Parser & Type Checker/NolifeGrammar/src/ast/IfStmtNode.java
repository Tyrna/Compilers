package ast;

import visitor.Visitor;

public class IfStmtNode extends BinaryNode {
	
	public IfStmtNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
