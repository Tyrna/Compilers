package ast;

import visitor.Visitor;

public class MulNode extends BinaryNode {

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
