package visitor;

import java.util.HashMap;
import java.util.Stack;

import ast.*;
import javafx.scene.Node;

public class MemoryMapVisitor implements Visitor {
	
	private final int INTEGER = 0;
	private final int FLOAT = 1;
	private final int CHAR = 2;
	protected Stack<HashMap<String, ASTNode>> symTableStack = new Stack<HashMap<String, ASTNode>>();
	protected HashMap<String, ASTNode> symTable;
	protected HashMap<String, Integer> constTable = new HashMap<String, Integer>();
	private int realType;
	private int offset = 0;
	private int constOffset = 0;
	private int tempNum = 0;
	private boolean params = false;
	

	public MemoryMapVisitor() {
		super();
	}
	
	@Override
	public Object visit(SymNode n) {
		
	return null; }
	
	@Override
	public Object visit(IntNode n) {
		tempNum = Integer.parseInt(n.getLabel());
		n.setType(realType);
		setType(INTEGER);
		return null; 
	}
	
	@Override
	public Object visit(FloatNode n) {
		setType(FLOAT);
		if (!constTable.containsKey(n.getLabel())) {
			n.setOffset(constOffset);
			constTable.put(n.getLabel(), constOffset);
			constOffset -= 4;
		} else {
			n.setOffset(constTable.get(n.getLabel()));
		}
		System.out.printf("Constant declared : %s\n", n.getLabel());
		return null; 
	}
	
	@Override
	public Object visit(CharNode n) {
		tempNum = n.getLabel().charAt(1);
		return null; 
	}
	
	@Override
	public Object visit(AddNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }

	@Override
	public Object visit(SubNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }

	@Override
	public Object visit(ModNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }

	@Override
	public Object visit(MulNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }
	
	@Override
	public Object visit(LessThanNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}

	@Override
	public Object visit(LessEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(GreaterThanNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(GreaterEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(NotEqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(EqualNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		n.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(AndNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }
	
	@Override
	public Object visit(OrNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }
	
	@Override
	public Object visit(IdRefNode n) {
		setScope(n);
		ASTNode idNode = getNode(n.getLabel());
		//IdDeclNode x;
		//if (node.getClass().getSimpleName().compareTo("IdDeclNode") == 0)
			//IdDeclNode idNode = (IdDeclNode)node;
		//else
			//ArrayDeclNode idNode = (ArrayDeclNode)node;
		n.setOffset(idNode.getOffset());
		n.setType(idNode.getType());
		setType(n.getType());
		System.out.printf("Reference at %s scope: %s %s, is on offset %d\n",
				n.getScope() == true ? "Local" : "Global",
				idNode.getParam() ? "Parameter" : "Variable",
				n.getLabel(), n.getOffset());
		return null; 
	}
	
	@Override
	public Object visit(ArrayRefNode arrayRefNode) {
		arrayRefNode.getChild(0).accept(this);
		
		setScope(arrayRefNode);
		ArrayDeclNode declNode = (ArrayDeclNode)getNode(arrayRefNode.getLabel());
		arrayRefNode.setOffset(declNode.getEndOffset() + ((tempNum - declNode.getStartDim()) * 4));
		arrayRefNode.setType(declNode.getType());
		arrayRefNode.setEndOffset(declNode.getEndOffset());
		arrayRefNode.setDims(declNode.getStartDim(), declNode.getMaxDim());
		System.out.printf("Array Reference at %s scope: %s %s, is on offset %d\n",
				arrayRefNode.getScope() == true ? "Local" : "Global",
				declNode.getParam() ? "Parameter" : "Variable",
				arrayRefNode.getLabel(), arrayRefNode.getOffset());
	return null; }
	
	@Override
	public Object visit(StringNode stringNode) {
		String sliced = stringNode.getLabel().substring(1, stringNode.getLabel().length()-1);
		int length = sliced.length() + 1;
		sliced = "\"" + sliced + "\""; 
		
		stringNode.setOffset(constOffset);
		constOffset -= length;
		constTable.put(sliced, constOffset);
		System.out.printf("Constant declared : %s\n", sliced);
	return null; }
	
	@Override
	public Object visit(ParenNode parenNode) {
		Integer i = (Integer) parenNode.getChild(0).accept(this);
		return i; 
	}
	
	@Override
	public Object visit(NotNode notNode) {
		notNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(DeclNode declNode) {
		declNode.getChild(0).accept(this);
		int i;
		for (i = 1; i < declNode.getChildren().size(); i++) 
			declNode.getChild(i).accept(this);
		return i; 
	}

	@Override
	public Object visit(ProgramNode programNode) {
		programNode.getLabel();
		//Initialize global scope
		symTable = new HashMap<String, ASTNode>();
		symTableStack.push(symTable);
		for (ASTNode node : programNode.getChildren())
			node.accept(this);
		
		//Testing the constTable
		//for (String key : constTable.keySet())
		//	System.out.printf("Key: %s, Value: %d\n", key, constTable.get(key));
		
		programNode.setTable(constTable);
	return null; }

	@Override
	public Object visit(VarDeclsNode varDeclsNode) {
		for (ASTNode node : varDeclsNode.getChildren())
			node.accept(this);
	return null; }

	@Override
	public Object visit(IntTypeNode intTypeNode) {
		realType = INTEGER;
		intTypeNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(FloatTypeNode floatTypeNode) {
		realType = FLOAT;
		floatTypeNode.getChild(0).accept(this);
	return null; }
	
	@Override
	public Object visit(CharTypeNode charTypeNode) {
		realType = CHAR;
		charTypeNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(AssignNode assignNode) {
		assignNode.getChild(0).accept(this);
		assignNode.getChild(1).accept(this);
		
		assignNode.setType(realType);
		realType = 0;
		return null; 
	}
	
	@Override
	public Object visit(IfStmtNode ifStmtNode) {
		ifStmtNode.getChild(0).accept(this); 
		ifStmtNode.getChild(1).accept(this); 
		if (ifStmtNode.getChild(2) != null)
			ifStmtNode.getChild(2).accept(this);
	return null; }
	
	@Override
	public Object visit(WhileNode whileNode) {
		whileNode.getChild(0).accept(this);
		whileNode.getChild(1).accept(this);
	return null; }
	
	@Override
	public Object visit(ProcCallNode procCallNode) {
		procCallNode.getLabel();
		if (procCallNode.getChild(0) != null) 
			procCallNode.getChild(0).accept(this);
	return null; }
	
	@Override
	public Object visit(WriteNode writeNode) {
		writeNode.getChild(0).accept(this);
		return null; 
	}
	
	@Override
	public Object visit(ReadNode readNode) {
		readNode.getChild(0).accept(this);
		readNode.setType(readNode.getChild(0).getType());
		return null; 
	}
	
	@Override
	public Object visit(ReturnNode returnNode) {
		returnNode.getChild(0).accept(this);
	return null; }
	
	@Override
	public Object visit(CaseStmtNode caseStmtNode) {
		caseStmtNode.getChild(0).accept(this);
		if (caseStmtNode.getChild(1) != null) 
			caseStmtNode.getChild(1).accept(this);
	return null; }
	
	@Override
	public Object visit(CaseListNode caseListNode) {
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
		return null; 
	}
	
	@Override
	public Object visit(CaseNode caseNode) {
		caseNode.getChild(0).accept(this);
		caseNode.getChild(1).accept(this);
		return null; 
	}
	
	@Override
	public Object visit(CaseLabelsNode caseLabelsNode) {
		caseLabelsNode.getChild(0).accept(this);
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++)
			caseLabelsNode.getChild(i).accept(this);
	return null; }

	@Override
	public Object visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		for (ASTNode childNode : stmtListNode.getChildren()) 
			childNode.accept(this);
	return null; }
	
	@Override
	public Object visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) 
			exprListNode.getChild(i).accept(this);
	return null; }
	
	@Override
	public Object visit(SubProgDeclNode subProgDeclNode) {
		for (ASTNode childNode : subProgDeclNode.getChildren())
			childNode.accept(this);
	return null; }
	
	@Override
	public Object visit(ProcNode procNode) {
		//New scope
		symTable = new HashMap<String, ASTNode>();
		symTableStack.push(symTable);
		offset = 0;
		
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
		
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		procNode.getChild(2).accept(this);
		symTableStack.pop();
		symTable = symTableStack.peek();
		return null; 
	}

	@Override
	public Object visit(FuncNode funcNode) {
		//New scope
		symTable = new HashMap<String, ASTNode>();
		symTableStack.push(symTable);
		offset = 0;
		
		if (funcNode.getChild(0) != null) 
			funcNode.getChild(0).accept(this);
		
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);
	
		funcNode.getChild(2).accept(this);
		symTableStack.pop();
		symTable = symTableStack.peek();
		return null;
	}
	
	@Override
	public Object visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		params = true;
		paramNode.getChild(0).accept(this);
		for (int i = 1; i < paramNode.getChildren().size(); i++)
			paramNode.getChild(i).accept(this);
		offset = 0;
		params = false;
	return null; }
	
	@Override
	public Object visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		callNode.getLabel();
		if (callNode.getChild(0) != null) 
			callNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(IdDeclNode idDeclNode) {
		symTable.put(idDeclNode.getLabel(), idDeclNode);
		idDeclNode.setOffset(offsetCalc(4));
		idDeclNode.setParam(params);
		idDeclNode.setType(realType);
		setScope(idDeclNode);
		System.out.printf("%s Declaration: %s, is on offset %d\n",
				idDeclNode.getParam() ? "Parameter" : "Variable",
				idDeclNode.getLabel(), idDeclNode.getOffset());
	return null; }
	
	@Override
	public Object visit(ArrayDeclNode arrayDeclNode) {
		arrayDeclNode.getLabel();
		arrayDeclNode.getChild(0).accept(this);
		int dim1 = tempNum;
		arrayDeclNode.getChild(1).accept(this);
		int dim2 = tempNum;
		
		arrayDeclNode.setDims(dim1, dim2);
		arrayDeclNode.setOffset(offsetCalc(0));
		arrayDeclNode.setParam(params);
		arrayDeclNode.setType(realType);
		arrayDeclNode.setEndOffset(offsetCalc((dim2-dim1) * 4) - 4);
		
		symTable.put(arrayDeclNode.getLabel(), arrayDeclNode);
		arrayDeclNode.setOffset(arrayDeclNode.getOffset());
		setScope(arrayDeclNode);
		System.out.printf("%s Array Declaration: %s, is until offset %d\n\t\t\tFrom offset %d\n",
				arrayDeclNode.getParam() ? "Parameter" : "Variable",
				arrayDeclNode.getLabel(), arrayDeclNode.getOffset(), arrayDeclNode.getEndOffset());
	return null; }

	@Override
	public Object visit(IdDefNode idDefNode) {
		setScope(idDefNode);
		IdDeclNode idNode = (IdDeclNode)getNode(idDefNode.getLabel());
		idDefNode.setOffset(idNode.getOffset());
		idDefNode.setType(idNode.getType());
		setType(idDefNode.getType());
		System.out.printf("Definition at %s scope: %s %s, is on offset %d\n",
				idDefNode.getScope() == true ? "Local" : "Global",
				idNode.getParam() ? "Parameter" : "Variable",
				idDefNode.getLabel(), idDefNode.getOffset());
		return null; 
	}
	
	@Override
	public Object visit(ArrayDefNode arrayDefNode) {
		arrayDefNode.getChild(0).accept(this);
		
		setScope(arrayDefNode);
		ArrayDeclNode declNode = (ArrayDeclNode)getNode(arrayDefNode.getLabel());
		arrayDefNode.setOffset(declNode.getEndOffset() + ((tempNum - declNode.getStartDim()) * 4));
		arrayDefNode.setType(declNode.getType());
		arrayDefNode.setEndOffset(declNode.getEndOffset());
		arrayDefNode.setDims(declNode.getStartDim(), declNode.getMaxDim());
		System.out.printf("Array Definition at %s scope: %s %s, is on offset %d\n",
				arrayDefNode.getScope() == true ? "Local" : "Global",
				declNode.getParam() ? "Parameter" : "Variable",
				arrayDefNode.getLabel(), arrayDefNode.getOffset());
		return null; 
	}
	
	private int offsetCalc(int i) {
		offset -= i;
		return offset;
	}
	
	private ASTNode getNode(String label) {
		ASTNode ret;
		if ((ret = symTable.get(label)) != null)
			return ret;
		else if ((ret = symTableStack.get(0).get(label)) != null)
			return ret;
			
		return null;
	}
	
	private Object setScope(ASTNode node) {
		if (symTable.get(node.getLabel()) != null)
			node.setScope(true);
		else
			node.setScope(false);
		return null; 
	}
	
	private void setType(int t) {
		if (realType == FLOAT)
			return;
		else
			realType = t;
	}

}
