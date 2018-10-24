package ast;

import visitor.Visitor;

public class ModNode extends BinaryNode {

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
