package ast;

import visitor.Visitor;

public class ModNode extends BinaryNode {

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
