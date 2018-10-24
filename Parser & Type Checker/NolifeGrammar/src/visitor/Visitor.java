package visitor;

import ast.*;

public interface Visitor {

	public void visit(SymNode n);
	public void visit(IntNode n);
	public void visit(FloatNode n);
	public void visit(CharNode n);
	public void visit(AddNode n);
	public void visit(SubNode n);
	public void visit(ModNode n);
	public void visit(MulNode n);
	public void visit(LessThanNode n);
	public void visit(LessEqualNode n);
	public void visit(GreaterThanNode n);
	public void visit(GreaterEqualNode n);
	public void visit(NotEqualNode n);
	public void visit(EqualNode n);
	public void visit(AndNode n);
	public void visit(OrNode n);
	public void visit(IdRefNode n);
	public void visit(ArrayRefNode arrayRefNode);
	public void visit(StringNode stringNode);
	public void visit(ParenNode parenNode);
	public void visit(DeclNode declNode);
	public void visit(ProgramNode programNode);
	public void visit(NotNode notNode);
	public void visit(VarDeclsNode varDeclsNode);
	public void visit(IntTypeNode intTypeNode);
	public void visit(FloatTypeNode floatTypeNode);
	public void visit(CharTypeNode charTypeNode);
	public void visit(AssignNode assignNode);
	public void visit(IfStmtNode ifStmtNode);
	public void visit(WhileNode whileNode);
	public void visit(ProcCallNode procCallNode);
	public void visit(WriteNode writeNode);
	public void visit(ReadNode readNode);
	public void visit(ReturnNode returnNode);
	public void visit(CaseStmtNode caseStmtNode);
	public void visit(CaseListNode caseListNode);
	public void visit(CaseNode caseNode);
	public void visit(CaseLabelsNode caseLabelsNode);
	public void visit(StmtListNode stmtListNode);
	public void visit(ExprListNode exprListNode);
	public void visit(SubProgDeclNode subProgDeclNode);
	public void visit(ProcNode procNode);
	public void visit(FuncNode funcNode);
	public void visit(ParamNode paramNode);
	public void visit(CallNode callNode);
	public void visit(IdDeclNode idDeclNode);
	public void visit(ArrayDeclNode arrayDeclNode);
	public void visit(IdDefNode idDefNode);
	public void visit(ArrayDefNode arrayDefNode);
}
