package ast;

import java.util.ArrayList;
import java.util.List;

import visitor.Visitor;

public abstract class ASTNode {
	protected List<ASTNode> children = new ArrayList<ASTNode>();
	protected String label;
	protected int expectedType;
	protec
	
	public abstract void accept(Visitor v);
	
	public ASTNode addChild(ASTNode c) {
		children.add(c);
		return this;
	}
	
	public ASTNode addLabel(String label) {
		this.label = label;
		return this;
	}
	
	public List<ASTNode> getChildren() {
		return children;
	}
	
	public ASTNode getChild(int index) {
		return children.get(index);
	}
	
	public String getLabel() {
		return label;
	}
}
