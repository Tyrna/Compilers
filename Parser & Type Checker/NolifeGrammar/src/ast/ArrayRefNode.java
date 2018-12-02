package ast;

import visitor.Visitor;

public class ArrayRefNode extends ASTNode {
	
	protected int endOffset;
	protected int start;
	protected int end;

	public ArrayRefNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
	
	public void setEndOffset(int i) {
		endOffset = i;
	}
	
	public int getEndOffset() {
		return endOffset;
	}
	
	public void setDims(int s, int e) {
		start = s;
		end = e;
	}
	
	public int getMaxDim() {
		return end;
	}
	
	public int getStartDim() {
		return start;
	}
}
