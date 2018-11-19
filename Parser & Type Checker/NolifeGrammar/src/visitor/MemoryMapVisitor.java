package visitor;

import java.util.HashMap;

import ast.*;


public class MemoryMapVisitor implements Visitor {
	
	private final int INTEGER = 0;
	private final int FLOAT = 1;
	private final int CHAR = 2;
	protected static HashMap<String, Integer> symTable = new HashMap<String, Integer>();
	private int realType;
	private int expectedType;
	private int offset = 0;
	

	public MemoryMapVisitor() {
		super();
	}
	
	@Override
	public void visit(SymNode n) {
		
	}
	
	@Override
	public void visit(IntNode n) {

	}
	
	@Override
	public void visit(FloatNode n) {

	}
	
	@Override
	public void visit(CharNode n) {

	}
	
	@Override
	public void visit(AddNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(SubNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(ModNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(MulNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(LessThanNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(LessEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(GreaterThanNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(GreaterEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(NotEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(EqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(AndNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(OrNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(IdRefNode n) {
		n.getLabel();
	}
	
	@Override
	public void visit(ArrayRefNode arrayRefNode) {
		arrayRefNode.getLabel();
		arrayRefNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(StringNode stringNode) {
		stringNode.getLabel();
	}
	
	@Override
	public void visit(ParenNode parenNode) {
		parenNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(NotNode notNode) {
		notNode.getChild(0).accept(this);
	}

	@Override
	public void visit(DeclNode declNode) {
		declNode.getChild(0).accept(this);
		for (int i = 1; i < declNode.getChildren().size(); i++) 
			declNode.getChild(i).accept(this);
	}

	@Override
	public void visit(ProgramNode programNode) {
		programNode.getLabel();
		for (ASTNode node : programNode.getChildren())
			node.accept(this);
		
		//Print memmap
		for (String var : symTable.keySet())
			System.out.printf("Variable: %s, Offset: %d\n", var, symTable.get(var));
	}

	@Override
	public void visit(VarDeclsNode varDeclsNode) {
		for (ASTNode node : varDeclsNode.getChildren())
			node.accept(this);
	}

	@Override
	public void visit(IntTypeNode intTypeNode) {
		realType = INTEGER;
		intTypeNode.getChild(0).accept(this);
	}

	@Override
	public void visit(FloatTypeNode floatTypeNode) {
		realType = FLOAT;
		floatTypeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(CharTypeNode charTypeNode) {
		realType = CHAR;
		charTypeNode.getChild(0).accept(this);
	}

	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getChild(0).accept(this);
		assignNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(IfStmtNode ifStmtNode) {
		ifStmtNode.getChild(0).accept(this); 
		ifStmtNode.getChild(1).accept(this); 
		if (ifStmtNode.getChild(2) != null)
			ifStmtNode.getChild(2).accept(this);
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		whileNode.getChild(0).accept(this);
		whileNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		procCallNode.getLabel();
		if (procCallNode.getChild(0) != null) 
			procCallNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(WriteNode writeNode) {
		writeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(ReadNode readNode) {
		readNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(ReturnNode returnNode) {
		returnNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(CaseStmtNode caseStmtNode) {
		caseStmtNode.getChild(0).accept(this);
		if (caseStmtNode.getChild(1) != null) 
			caseStmtNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(CaseListNode caseListNode) {
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
	}
	
	@Override
	public void visit(CaseNode caseNode) {
		caseNode.getChild(0).accept(this);
		caseNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(CaseLabelsNode caseLabelsNode) {
		caseLabelsNode.getChild(0).accept(this);
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++)
			caseLabelsNode.getChild(i).accept(this);
	}

	@Override
	public void visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		for (ASTNode childNode : stmtListNode.getChildren()) 
			childNode.accept(this);
	}
	
	@Override
	public void visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) 
			exprListNode.getChild(i).accept(this);
	}
	
	@Override
	public void visit(SubProgDeclNode subProgDeclNode) {
		for (ASTNode childNode : subProgDeclNode.getChildren())
			childNode.accept(this);
	}
	
	@Override
	public void visit(ProcNode procNode) {
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
		
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		procNode.getChild(2).accept(this);
	}

	@Override
	public void visit(FuncNode funcNode) {
		if (funcNode.getChild(0) != null)
			funcNode.getChild(0).accept(this);
		
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);

		funcNode.getChild(2).accept(this);
	}
	
	@Override
	public void visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		paramNode.getChild(0).accept(this);
		for (int i = 1; i < paramNode.getChildren().size(); i++)
			paramNode.getChild(i).accept(this);
	}
	
	@Override
	public void visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		callNode.getLabel();
		if (callNode.getChild(0) != null) 
			callNode.getChild(0).accept(this);
	}

	@Override
	public void visit(IdDeclNode idDeclNode) {
		symTable.put(idDeclNode.getLabel(), offsetCalc());
		idDeclNode.getLabel();
	}
	
	@Override
	public void visit(ArrayDeclNode arrayDeclNode) {
		arrayDeclNode.getLabel();
		arrayDeclNode.getChild(0).accept(this);
		arrayDeclNode.getChild(1).accept(this);
	}

	@Override
	public void visit(IdDefNode idDefNode) {
		idDefNode.setOffset(symTable.get(idDefNode.getLabel()));
		System.out.printf("Variable %s, is on offset %d\n", idDefNode.getLabel(), idDefNode.getOffset());
	}
	
	@Override
	public void visit(ArrayDefNode arrayDefNode) {
		arrayDefNode.getLabel();
		arrayDefNode.getChild(0).accept(this);
	}
	
	private int offsetCalc() {
		offset -= 4;
		return offset;
	}

}
