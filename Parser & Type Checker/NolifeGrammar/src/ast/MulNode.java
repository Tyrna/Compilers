package ast;

import visitor.Visitor;

public class MulNode extends BinaryNode {

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
