package ast;

public class ASTNodeFactory {
	
	public ASTNode makeASTNode(String nodeType) {
		
		switch(nodeType) {
			case "SymNode":
				return new SymNode();
			case "IntNode":
				return new IntNode();
			case "FloatNode":
				return new FloatNode();
			case "CharNode":
				return new CharNode();
			case "AddNode":
				return new AddNode();
			case "SubNode":
				return new SubNode();
			case "ModNode":
				return new ModNode();
			case "MulNode":
				return new MulNode();
			case "LessThanNode":
				return new LessThanNode();
			case "LessEqualNode":
				return new LessEqualNode();
			case "GreaterThanNode":
				return new GreaterThanNode();
			case "GreaterEqualNode":
				return new GreaterEqualNode();
			case "NotEqualNode":
				return new NotEqualNode();
			case "EqualNode":
				return new EqualNode();
			case "AndNode":
				return new AndNode();
			case "OrNode":
				return new OrNode();
			case "IdDefNode":
				return new IdDefNode();
			case "IdDeclNode":
				return new IdDeclNode();
			case "IdRefNode":
				return new IdRefNode();
			case "ArrayDefNode":
				return new ArrayDefNode();
			case "ArrayDeclNode":
				return new ArrayDeclNode();
			case "ArrayRefNode":
				return new ArrayRefNode();
			case "StringNode":
				return new StringNode();
			case "ParenNode":
				return new ParenNode();
			case "NotNode" :
				return new NotNode();
			case "DeclNode":
				return new DeclNode();
			case "VarDeclsNode":
				return new VarDeclsNode();
			case "IntTypeNode":
				return new IntTypeNode();
			case "FloatTypeNode":
				return new FloatTypeNode();
			case "CharTypeNode":
				return new CharTypeNode();
			case "ProgramNode":
				return new ProgramNode();
			case "AssignNode":
				return new AssignNode();
			case "IfStmtNode":
				return new IfStmtNode();
			case "WhileNode":
				return new WhileNode();
			case "ProcCallNode":
				return new ProcCallNode();
			case "WriteNode":
				return new WriteNode();
			case "ReadNode":
				return new ReadNode();
			case "ReturnNode":
				return new ReturnNode();
			case "CaseStmtNode":
				return new CaseStmtNode();
			case "CaseListNode":
				return new CaseListNode();
			case "CaseNode":
				return new CaseNode();
			case "CaseLabelsNode":
				return new CaseLabelsNode();
			case "StmtListNode":
				return new StmtListNode();
			case "ExprListNode":
				return new ExprListNode();
			case "SubProgDeclNode":
				return new SubProgDeclNode();
			case "ProcNode":
				return new ProcNode();
			case "FuncNode":
				return new FuncNode();
			case "ParamNode":
				return new ParamNode();
			case "CallNode":
				return new CallNode();
			default:
				return null;
				
		}
	}
}
