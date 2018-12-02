package visitor;

import ast.*;

public interface Visitor<T> {

	public T visit(SymNode n);
	public T visit(IntNode n);
	public T visit(FloatNode n);
	public T visit(CharNode n);
	public T visit(AddNode n);
	public T visit(SubNode n);
	public T visit(ModNode n);
	public T visit(MulNode n);
	public T visit(LessThanNode n);
	public T visit(LessEqualNode n);
	public T visit(GreaterThanNode n);
	public T visit(GreaterEqualNode n);
	public T visit(NotEqualNode n);
	public T visit(EqualNode n);
	public T visit(AndNode n);
	public T visit(OrNode n);
	public T visit(IdRefNode n);
	public T visit(ArrayRefNode arrayRefNode);
	public T visit(StringNode stringNode);
	public T visit(ParenNode parenNode);
	public T visit(NotNode n);
	public T visit(DeclNode declNode);
	public T visit(ProgramNode programNode);
	public T visit(VarDeclsNode varDeclsNode);
	public T visit(IntTypeNode intTypeNode);
	public T visit(FloatTypeNode floatTypeNode);
	public T visit(CharTypeNode charTypeNode);
	public T visit(AssignNode assignNode);
	public T visit(IfStmtNode ifStmtNode);
	public T visit(WhileNode whileNode);
	public T visit(ProcCallNode procCallNode);
	public T visit(WriteNode writeNode);
	public T visit(ReadNode readNode);
	public T visit(ReturnNode returnNode);
	public T visit(CaseStmtNode caseStmtNode);
	public T visit(CaseListNode caseListNode);
	public T visit(CaseNode caseNode);
	public T visit(CaseLabelsNode caseLabelsNode);
	public T visit(StmtListNode stmtListNode);
	public T visit(ExprListNode exprListNode);
	public T visit(SubProgDeclNode subProgDeclNode);
	public T visit(ProcNode procNode);
	public T visit(FuncNode funcNode);
	public T visit(ParamNode paramNode);
	public T visit(CallNode callNode);
	public T visit(IdDeclNode idDeclNode);
	public T visit(ArrayDeclNode arrayDeclNode);
	public T visit(IdDefNode idDefNode);
	public T visit(ArrayDefNode arrayDefNode);
}
