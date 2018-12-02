package visitor;

import ast.*;

public class ASTBuildVisitor implements Visitor {

	private String src;
	private String prefix;

	public ASTBuildVisitor() {
		super();
		src = "";
		prefix = "";
	}
	
	@Override
	public Object visit(SymNode n) {
		src += "\n" + prefix + "SYM " + n.getLabel();
		return null; }
	
	@Override
	public Object visit(IntNode n) {
		src += "\n" + prefix + "CONST INT " + n.getLabel();
	return null; }
	
	@Override
	public Object visit(FloatNode n) {
		src += "\n" + prefix + "CONST FLOAT " + n.getLabel();
	return null; }
	
	@Override
	public Object visit(CharNode n) {
		src += "\n" + prefix + "CONST CHAR " + n.getLabel();
	return null; }
	
	@Override
	public Object visit(AddNode n) {
		src += "\n" + prefix + "ADD";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(SubNode n) {
		src += "\n" + prefix + "SUB";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(ModNode n) {
		src += "\n" + prefix + "MOD";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(MulNode n) {
		src += "\n" + prefix + "MUL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(LessThanNode n) {
		src += "\n" + prefix + "LESSTHAN";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(LessEqualNode n) {
		src += "\n" + prefix + "LESSEQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(GreaterThanNode n) {
		src += "\n" + prefix + "GREATERTHAN";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(GreaterEqualNode n) {
		src += "\n" + prefix + "GREATEREQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(NotEqualNode n) {
		src += "\n" + prefix + "NOTEQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(EqualNode n) {
		src += "\n" + prefix + "EQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(AndNode n) {
		src += "\n" + prefix + "AND";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(OrNode n) {
		src += "\n" + prefix + "OR";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(IdRefNode n) {
		src += "\n" + prefix + "IDREF " + n.getLabel();
	return null; }
	
	@Override
	public Object visit(ArrayRefNode arrayRefNode) {
		src += "\n" + prefix + "ARRAYREF " + arrayRefNode.getLabel();
		addPrefix();
		arrayRefNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(StringNode stringNode) {
		src += "\n" + prefix + "STRING " + stringNode.getLabel();
	return null; }
	
	@Override
	public Object visit(ParenNode parenNode) {
		parenNode.getChild(0).accept(this);
	return null; }
	
	@Override
	public Object visit(NotNode notNode) {
		src += "\n" + prefix + "NOT";
		addPrefix();
		notNode.getChild(0).accept(this);
		delPrefix();
	return null; }

	public String getSrc() {
		return src;
	}

	@Override
	public Object visit(DeclNode declNode) {
		src += "\n" + prefix + "DECLARATION";
		addPrefix();
		declNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < declNode.getChildren().size(); i++) {
			addPrefix();
			declNode.getChild(i).accept(this);
			delPrefix();
		}
		//declNode.getChild(declNode.getChildren().size()-1).accept(this);
	return null; }

	@Override
	public Object visit(ProgramNode programNode) {
		src += "PROG ";
		src +=  programNode.getLabel(); src +="\n";
		for (ASTNode node : programNode.getChildren()) {
			addPrefix();
			node.accept(this);
			delPrefix();
		}
	return null; }

	@Override
	public Object visit(VarDeclsNode varDeclsNode) {
		src += "\n" + prefix + "VarDecl\n";
		for (ASTNode node : varDeclsNode.getChildren()) {
			addPrefix();
			node.accept(this);
			delPrefix();
		}
	return null; }

	@Override
	public Object visit(IntTypeNode intTypeNode) {
		src += "\n" + prefix + "INT ";
		addPrefix();
		intTypeNode.getChild(0).accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(FloatTypeNode floatTypeNode) {
		src += "\n" + prefix + "FLOAT ";
		addPrefix();
		floatTypeNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(CharTypeNode charTypeNode) {
		src += "\n" + prefix + "CHAR ";
		addPrefix();
		charTypeNode.getChild(0).accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(AssignNode assignNode) {
		src += "\n" + prefix + "ASSIGN";
		addPrefix();
		assignNode.getChild(0).accept(this);
		assignNode.getChild(1).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(IfStmtNode ifStmtNode) {
		src += "\n" + prefix + "IFTHENELSE"; 
		addPrefix();
		ifStmtNode.getChild(0).accept(this); 
		delPrefix();
		//src += "\n" + prefix + "THEN"; 
		addPrefix();
		ifStmtNode.getChild(1).accept(this); 
		delPrefix();
		if (ifStmtNode.getChild(2) != null) {
			//src += "\n" + prefix + "ELSE";
			addPrefix();
			ifStmtNode.getChild(2).accept(this);
			delPrefix();
		}
	return null; }
	
	@Override
	public Object visit(WhileNode whileNode) {
		src += "\n" + prefix + "WHILEDO";
		addPrefix();
		whileNode.getChild(0).accept(this);
		whileNode.getChild(1).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(ProcCallNode procCallNode) {
		src += "\n" + prefix + "PROCCALL " + procCallNode.getLabel();
		if (procCallNode.getChild(0) != null) {
			addPrefix();
			procCallNode.getChild(0).accept(this);
			delPrefix();
		}
	return null; }
	
	@Override
	public Object visit(WriteNode writeNode) {
		src += "\n" + prefix + "WRITE ";
		addPrefix();
		writeNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(ReadNode readNode) {
		src += "\n" + prefix + "READ ";
		addPrefix();
		readNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(ReturnNode returnNode) {
		src += "\n" + prefix + "RETURN ";
		addPrefix();
		returnNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(CaseStmtNode caseStmtNode) {
		src += "\n" + prefix + "CASETMT";
		addPrefix();
		caseStmtNode.getChild(0).accept(this);
		if (caseStmtNode.getChild(1) != null) 
			caseStmtNode.getChild(1).accept(this);

		delPrefix();
	return null; }
	
	@Override
	public Object visit(CaseListNode caseListNode) {
		src += "\n" + prefix + "CASELIST";
		addPrefix();
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
		
		delPrefix();
	return null; }
	
	@Override
	public Object visit(CaseNode caseNode) {
		src += "\n" + prefix + "CASE";
		addPrefix();
		caseNode.getChild(0).accept(this);
		caseNode.getChild(1).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(CaseLabelsNode caseLabelsNode) {
		src += "\n" + prefix + "CASELABELS";
		addPrefix();
		caseLabelsNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++) {
			addPrefix();
			caseLabelsNode.getChild(i).accept(this);
			delPrefix();	
		}
	return null; }

	@Override
	public Object visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "COMPSTMT";
		for (ASTNode childNode : stmtListNode.getChildren()) {
			addPrefix();
			childNode.accept(this);
			delPrefix();
		}
	return null; }
	
	@Override
	public Object visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "EXPRLIST";
		addPrefix();
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) 
			exprListNode.getChild(i).accept(this);
		
		delPrefix();
	return null; }
	
	@Override
	public Object visit(SubProgDeclNode subProgDeclNode) {
		src += "\n" + prefix + "SUBPROGDECL";
		addPrefix();
		for (ASTNode childNode : subProgDeclNode.getChildren())
			childNode.accept(this);
			
		delPrefix();
	return null; }
	
	@Override
	public Object visit(ProcNode procNode) {
		src += "\n" + prefix + "PROCEDURE " + procNode.getLabel();
		addPrefix();
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
		
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		procNode.getChild(2).accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(FuncNode funcNode) {
		src += "\n" + prefix + "FUNCTION " + funcNode.getLabel();
		addPrefix();
		if (funcNode.getChild(0) != null)
			funcNode.getChild(0).accept(this);
		
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);

		funcNode.getChild(2).accept(this);
		delPrefix();
	return null; }
	
	@Override
	public Object visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "PARAMETERS";
		addPrefix();
		paramNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < paramNode.getChildren().size(); i++) {
			addPrefix();
			paramNode.getChild(i).accept(this);
			delPrefix();
		}
	return null; }
	
	@Override
	public Object visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "CALL " + callNode.getLabel();
		if (callNode.getChild(0) != null) {
			addPrefix();
			callNode.getChild(0).accept(this);
			delPrefix();
		}
	return null; }

	@Override
	public Object visit(IdDeclNode idDeclNode) {
		
		src += "\n" + prefix + "IDDECL " + idDeclNode.getLabel();
	return null; }
	
	@Override
	public Object visit(ArrayDeclNode arrayDeclNode) {
		src += "\n" + prefix + "ARRAY " + arrayDeclNode.getLabel();
		addPrefix();
		arrayDeclNode.getChild(0).accept(this);
		arrayDeclNode.getChild(1).accept(this);
		delPrefix();
	return null; }

	@Override
	public Object visit(IdDefNode idDefNode) {
		src += "\n" + prefix + "IDDEF " + idDefNode.getLabel();
	return null; }
	
	@Override
	public Object visit(ArrayDefNode arrayDefNode) {
		src += "\n" + prefix + "ARRAYDEF " + arrayDefNode.getLabel();
		addPrefix();
		arrayDefNode.getChild(0).accept(this);
		delPrefix();
	return null; }
	
	private Object addPrefix() {
		prefix = prefix + "\t";
	return null; }
	
	private Object delPrefix() {
		prefix = prefix.substring(0, prefix.length()-1);
	return null; }
	
}
