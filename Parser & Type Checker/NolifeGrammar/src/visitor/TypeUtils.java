package visitor;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;

import ast.ASTNode;

public final class TypeUtils {
	
	protected static Stack<HashMap<String, Symbol>> symTableStack = new Stack<HashMap<String, Symbol>>();
	private static TypeUtils instance = new TypeUtils();
	
	protected class Symbol {
	
		int type;
		String name;
		boolean usedInScope = false;
		
		public Symbol(int t, String n) {
			type = t;
			name = n;
		}
	}
	
	protected class funcSymbol extends Symbol {
		
		LinkedList<Symbol> parameters = new LinkedList<Symbol>();
		boolean returns = true;
		
		public funcSymbol(int t, String n) {
			super(t, n);
		}
		
		public funcSymbol(int t, String n, boolean r) {
			super(t, n);
			returns = r;
		}
	}
	
	protected class arraySymbol extends Symbol {
		int intFromDim;
		int intToDim;
		char charFromDim;
		char charToDim;
		
		// 0 = int
		// 2 = char
		int typeOfDim;
		
		public arraySymbol(int f, int d, int t, String n, int type){
			super(t, n);
			intFromDim = f;
			intToDim = d;
			typeOfDim = type;
		}
		
		public arraySymbol(char f, char d, int t, String n, int type){
			super(t, n);
			charFromDim = f;
			charToDim = d;
			typeOfDim = type;
		}
	}
	
	protected static String typeCh(int n) {
		
		switch(n) {
			case 0:
				return "INTEGER";
			case 1:
				return "FLOAT";
			case 2:
				return "CHAR";
			case 3:
				return "ANYTYPE";
			case 4:
				return "NOTYPE";
			default:
				return null;
		}
	}
	
	protected static void newFrame() {
		symTableStack.push(new HashMap<String, Symbol>());
	}
	
	protected static void remFrame() {
		symTableStack.pop();
	}
	
	protected static void addSymbol(String symName, int type) {
		symTableStack.peek().put(symName, instance.new Symbol(type, symName));
	}
	
	protected static void addFuncSymbol(String symName, int type, boolean returns) {
		symTableStack.peek().put(symName, instance.new funcSymbol(type, symName, returns));
	}
	
	protected static void addArraySymbol(String symName, int type, String from, String to, int dimType) {	
		if (dimType == 2) 
			symTableStack.peek().put(symName, instance.new arraySymbol(from.charAt(1), to.charAt(1), type, symName, dimType));
		else
			symTableStack.peek().put(symName, instance.new arraySymbol(Integer.parseInt(from), Integer.parseInt(to), type, symName, dimType));
	}
	
	protected static arraySymbol getArraySymbol(String symName) {
		Symbol sym;
		
		if ((sym = symTableStack.peek().get(symName)) == null)	
			if ((sym = symTableStack.get(0).get(symName)) == null)
				return null;
				
		arraySymbol arr  = (arraySymbol) sym;
		return arr;
		
	}
	
	protected static int getArrayType(String symName) {
		Symbol sym = symTableStack.peek().get(symName);
		arraySymbol arr  = (arraySymbol) sym;
		
		return arr.typeOfDim;
		
	}
	
	protected static void checkMyArray(ASTNode arrayNode, int exprType) {
		
		TypeUtils.arraySymbol arr = TypeUtils.getArraySymbol(arrayNode.getLabel());	
		
		//Must be a char or an int
		if (arr.typeOfDim != exprType) {
			if (arr.typeOfDim != 3)
				if (exprType != 3) 
					System.err.printf("Array Index Type mismatch\n\t Array \"%s\" cannot index by %s, as it expects %s\n",
							arrayNode.getLabel(), TypeUtils.typeCh(exprType), TypeUtils.typeCh(arr.typeOfDim));
		}

		//If a char literal...
		else if (arrayNode.getChild(0).getClass().getSimpleName().equals("CharNode")) {
			char[] range = {arr.charFromDim, arr.charToDim};
			char index = arrayNode.getChild(0).getLabel().charAt(1);
			if (range[0] > index || index > range[1]) {
				System.err.printf("Array out of bounds\n\t Cannot access index '%c' on array \"%s\" where bounds are ['%c' ... '%c']\n",
						index, arrayNode.getLabel(), range[0], range[1]);
			}
		}
		//If an int literal...
		else if (arrayNode.getChild(0).getClass().getSimpleName().equals("IntNode")) {
			int[] range = {arr.intFromDim, arr.intToDim};
			int index = Integer.parseInt(arrayNode.getChild(0).getLabel());
			if (range[0] > index || index > range[1]) {
				System.err.printf("Array out of bounds\n\t Cannot access index %d on array \"%s\" where bounds are [%d ... %d]\n",
						index, arrayNode.getLabel(), range[0], range[1]);
			}
		}
	}
	
	protected static Symbol findSymbolType(String sym) {
		if (symTableStack.peek().containsKey(sym)) 
			return symTableStack.peek().get(sym);
		else if (symTableStack.get(0).containsKey(sym))
			return symTableStack.get(0).get(sym);
		else 
			return null;
	}
	
	protected static int checkTypeOfSymbol(Symbol sym, String varType) {
		
		switch(varType) {
			case "Symbol" : {
				if (!(sym.getClass().getSimpleName()).equals(varType)) 
					if(!(sym.getClass().getSimpleName()).equals("funcSymbol"))
						return -1;
				
				break;
			}
			
			case "arraySymbol" : {
				if (!(sym.getClass().getSimpleName()).equals(varType)) 
					return -1;
				
				break;
			}
		}
		
		return sym.type;
	}
	
	protected static boolean findInScope(String sym) {
		if (symTableStack.peek().containsKey(sym))
			return true;
		return false;
	}
	
	protected static void referencedSym(String sym) {
		if (symTableStack.peek().containsKey(sym))
			symTableStack.peek().get(sym).usedInScope = true;
		else if (symTableStack.get(0).containsKey(sym))
			symTableStack.get(0).get(sym).usedInScope = true;
	}
	
	protected static void checkRefSym() {
		for (Symbol n : symTableStack.peek().values()) {
			if (n.usedInScope == false) {
				switch(n.getClass().getSimpleName()) {
					case "Symbol":
						System.err.printf("Variable declared but never referenced: %s\n", n.name);
						break;
					case "arraySymbol":
						System.err.printf("Array declared but never referenced: %s\n", n.name);
						break;
					case "funcSymbol": {
						funcSymbol func = (funcSymbol) n;
						if (func.returns)
							System.err.printf("Warning: Function declared but never called: %s\n", n.name);
						else
							System.err.printf("Warning: Procedure declared but never called: %s\n", n.name);
						break;
					}
					default:
						System.err.printf("UH OH WHAT IS THIS SYMBOL? CONTACT BAD PROGRAMMER\n");
				}
			}
		}
	}
	
	protected static void addParamSymbolsToFunc(ASTNode n) {
		
		funcSymbol fSym = (funcSymbol) symTableStack.get(0).get(n.getLabel());
		Symbol paramSym;
		int symType = -1;
		for (ASTNode paramNode : n.getChild(0).getChildren()) {
			switch(paramNode.getClass().getSimpleName()) {
				case "IntTypeNode" :
					symType = 0;
					break;
				case "FloatTypeNode" :
					symType = 1;
					break;
				case "CharTypeNode" :
					symType = 2;
					break;
			}
			paramSym = instance.new Symbol(symType, paramNode.getChild(0).getLabel());
			//Last element added to the hashmap on current scope...
			//Symbol lastOne = symTableStack.peek().get(paramNode.getChild(0).getLabel());
			//Add to parameters in procedure node, a.k.a last symbol added to the global scope
			fSym.parameters.add(paramSym);
		}
	}
}
