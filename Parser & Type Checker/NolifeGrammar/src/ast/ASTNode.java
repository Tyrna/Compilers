package ast;

import java.util.ArrayList;
import java.util.List;

import visitor.Visitor;

public abstract class ASTNode {
	protected List<ASTNode> children = new ArrayList<ASTNode>();
	protected String label;
	protected int expectedType;
	protected int realType;
	protected int offset;
	protected boolean scope;
	protected boolean param;
	private int paramOffset;
	
	public abstract Object accept(Visitor v);
	
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
	
	public void setOffset(int o) {
		offset = o;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setType(int t) {
		expectedType = t;
	}
	
	public int getType() {
		return expectedType;
	}
	
	public void setRealType(int t) {
		realType = t;
	}
	
	public int getRealType() {
		return realType;
	}
	
	public void setScope(boolean s) {
		scope = s;
	}
	
	public boolean getScope() {
		return scope;
	}
	
	public void setParam(boolean p) {
		param = p;
	}
	
	public boolean getParam() {
		return param;
	}
	
	public void setParamOffset(int x) {
		paramOffset = x;
	}
	
	public int getParamOffset() {
		return paramOffset;
	}
}
