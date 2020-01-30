package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	
	private Struct curr_type = Tab.noType;
	private Struct method_type = Tab.noType;
	private boolean returnExists = false;
	private int methodCounter = 0;// uradjen
	private int globalVarCounter = 0; // uradjen
	private int globalConstCounter = 0;// uradjen
	private int globalArraysCounter = 0; // uradjen
	private int mainVarsCounter = 0; // uradjen
	private int mainStatementsCounter = 0; // uradjen
	private int mainFuncCallCounter = 0; // uradjen
	private String currMethod = "";
	public boolean semanticError = false;
	public boolean mainExists = false;
	static Struct boolType = new Struct(Struct.Bool);
	public static void init(){
		Tab.init();
		Tab.insert(Obj.Type, "bool", boolType);
	}
	private void report_error(String message) {
		semanticError = true;
		System.err.println(message);
		System.err.flush();
	}

	public void counters() {
		System.out.println("==================SINTAKSNA ANALIZA======================");
		System.out.println(methodCounter + "\t methods in the program");
		System.out.println(globalVarCounter + "\t global variables");
		System.out.println(globalConstCounter + "\t global constants");
		System.out.println(globalArraysCounter + "\t global arrays");
		System.out.println(mainVarsCounter + "\t local variables in main");
		System.out.println(mainStatementsCounter + "\t statements in main");
		System.out.println(mainFuncCallCounter + "\t function calls in main");
	}

	
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();

	}

	
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	
	public void visit(Type type) {
		Obj obj = Tab.find(type.getId());
		if (obj.getKind() == Obj.Type)
			curr_type = type.struct = obj.getType();
		else {
			report_error("Greska u liniji " + type.getLine() + " (" + type.getId() + ") ovo  nije ugradjeni tip");
			curr_type = type.struct = Tab.noType;
		}
	}


	public void visit(VarId varId) {
		if (Tab.currentScope.findSymbol(varId.getId()) == null) {
			Obj obj = Tab.insert(Obj.Var, varId.getId(), curr_type);
			if (obj.getLevel() == 0)
				globalVarCounter++;
			else if (currMethod.equalsIgnoreCase("main"))
				mainVarsCounter++;
		} else
			report_error("Greska na liniji " + varId.getLine() + "jer " + varId.getId() + "  je vec deklarisano");

	}

	public void visit(VarArrayId varArrayId) {
		if (Tab.currentScope.findSymbol(varArrayId.getId()) == null) {
			Obj obj = Tab.insert(Obj.Var, varArrayId.getId(), new Struct(Struct.Array, curr_type));
			if (obj.getLevel() == 0)
				globalArraysCounter++;
			else if (currMethod.equalsIgnoreCase("main"))
				mainVarsCounter++;
		} else
			report_error(
					"Greska na liniji " + varArrayId.getLine() + "jer " + varArrayId.getId() + "  je vec deklarisano");
	}

	public void visit(RetType returnType) {
		method_type = returnType.getType().struct;
		returnType.obj = Tab.insert(Obj.Meth, returnType.getId(), method_type);
		currMethod = returnType.getId();
		Tab.openScope();
	}

	public void visit(RetVoid voidRet) {
		method_type = Tab.noType;
		voidRet.obj = Tab.insert(Obj.Meth, voidRet.getId(), method_type);
		currMethod = voidRet.getId();
		Tab.openScope();
	}

	public void visit(MethodDeclStart methodDeclStart) {
		if (methodDeclStart.getRetTypeIdent().obj.getName().equals("main")) {
			mainExists = true;
			if (method_type != Tab.noType)
				report_error("Greska u liniji " + methodDeclStart.getLine() + ": metoda main mora biti void");
		}
	}

	
	public void visit(MethodDecl methodDecl) {
		methodCounter++;
		if (method_type != Tab.noType && !returnExists) {
			report_error("Greska u liniji " + methodDecl.getLine()
					+ ": Metoda mora imati return iskaz jer je definisan sa povratnom vrednoscu");
		}
		returnExists = false; // inic. vrednost za sledeci metod
		Obj o = methodDecl.getMethodDeclStart().getRetTypeIdent().obj;
		o.setLevel(0);
		Tab.chainLocalSymbols(o);
		Tab.closeScope();
		currMethod = "";
	}

	public void visit(Assignment assignment) {
		Struct t = assignment.getDesignator().obj.getType();
		Obj o = assignment.getDesignator().obj;
		if (o.getKind()!=Obj.Elem && o.getKind()!=Obj.Var){
			report_error("Greska u liniji " + assignment.getLine() + ": dozvoljene su samo promenljive i elementi niza");
		}
		if (!t.assignableTo(o.getType()))
			report_error("Greska u liniji " + assignment.getLine() + ": tipovi nisu isti, dodela nemoguca");
	}

	

	public void visit(ReturnEmptyStatement returnEmptyStatement) {
		returnExists = true;
		if (method_type != Tab.noType) {
			report_error("Greska u liniji " + returnEmptyStatement.getLine()
					+ ": Metoda mora imati return iskaz jer je definisan sa povratnom vrednoscu");
		}
		if (currMethod.equals(""))
			report_error("Greska u liniji " + returnEmptyStatement.getLine()
					+ ": return je van metode, to nije dozvoljeno");
	}

	public void visit(ReturnStatement returnStatement) {
		returnExists = true;
		if (method_type == Tab.noType) {
			report_error("Greska u liniji " + returnStatement.getLine()
					+ ": metoda ne sme imati return sa izrazom jer je deklarisan bez povratne vrednosti");
		}
		Struct t = returnStatement.getExpression().struct;
		if (!t.assignableTo(method_type))
			report_error("Greska u liniji " + returnStatement.getLine()
					+ ": tip izraza se ne poklapa sa deklaracijom metode");
		if (currMethod.equals(""))
			report_error("Greska u liniji " + returnStatement.getLine()
					+ ": return je van metode");
	}

	public void visit(PrintStatement printStatement) {
		if (currMethod.equalsIgnoreCase("main"))
			mainFuncCallCounter++;
		Struct temp = printStatement.getExpression().struct;
		if (temp != Tab.intType && temp != Tab.charType && temp != boolType)
			report_error("Greska u liniji " + printStatement.getLine() + ": tip izraza mora biti int, char ili bool");

	}

	public void visit(PrintWithNumStatement printWithNumStatement) {
		if (currMethod.equalsIgnoreCase("main"))
			mainFuncCallCounter++;
		Struct temp = printWithNumStatement.getExpression().struct;
		if (temp != Tab.intType && temp != Tab.charType && temp != boolType)
			report_error(
					"Greska u liniji " + printWithNumStatement.getLine() + ": tip izraza mora biti int, char ili bool");
	}

	public void visit(ReadStatement readStatement) {
		if (currMethod.equalsIgnoreCase("main"))
			mainFuncCallCounter++;
		Obj t = readStatement.getDesignator().obj;
		if (t.getType() != Tab.intType && t.getType() != Tab.charType && t.getType() != boolType)
			report_error("Greska u liniji " + readStatement.getLine() + ": tip izraza mora biti int, char ili bool");
		if (t.getKind() != Obj.Var && t.getKind() != Obj.Elem)
			report_error("Greska u liniji " + readStatement.getLine() + ": Sme se ucitati samo promenljiva ili element niza");
	}

	public void visit(Designator designator) {
		designator.obj = designator.getDesigExprList().obj;
	}


	public void visit(ArrayDesignator arrayDesignator) {
		Obj id = arrayDesignator.getDesigExprListLBracket().obj;
		if (id.getType().getKind() != Struct.Array) {
			report_error("Greska u liniji " + arrayDesignator.getLine() + ": ovo nije niz");
			arrayDesignator.obj = id;
		} else {
			// dalje prenosimo element niza
			arrayDesignator.obj = new Obj(Obj.Elem, "", id.getType().getElemType());
		}
	}

	public void visit(SimpleDesignator simpleDesignator) {
		Obj ob = Tab.find(simpleDesignator.getId()); 
		if (ob != Tab.noObj) {
			SymbolTableVisitorPrint ss = new SymbolTableVisitorPrint(0); 
			ss.visitVar(ob);
			System.out.println("Pretraga " + simpleDesignator.getLine() + " (" + simpleDesignator.getId()
					+ "), nadjeno " + ss.getOutput());
		}
		else {
			report_error("Greska na liniji "+simpleDesignator.getLine()+" "+simpleDesignator.getId()+" nije deklarisano, nema ga u tabeli simbola");
		}
		simpleDesignator.obj = ob;
	}

	public void visit(DesigExprListLBracket desigExprListLbracket) {
		// desigExprListLbracket.obj = desigExprListLbracket.ge().obj;
		desigExprListLbracket.obj = desigExprListLbracket.getDesigExprList().obj;
	}

	public void visit(ExpressionOp expressionOp) {
		Struct first = expressionOp.getExpression().struct;
		Struct second = expressionOp.getTerm().struct;
		if (first.equals(second) && first == Tab.intType) {
			expressionOp.struct = first;
		} else {
			report_error(
					"Greska na liniji " + expressionOp.getLine() + " : tipovi su razliciti i nekompatibilni");
			expressionOp.struct = Tab.noType;
		}
	}

	public void visit(NegExpression negExpression) {
		Struct first = negExpression.getTerm().struct;
		if (first == Tab.intType) {
			negExpression.struct = first;
		} else {
			report_error("Greska na liniji " + negExpression.getLine() + " : tip mora biti int.");
			negExpression.struct = Tab.noType;
		}
	}

	public void visit(MulOpTerm mulOpTerm) {
		Struct first = mulOpTerm.getTerm().struct;
		Struct second = mulOpTerm.getFactor().struct;
		if (!first.equals(Tab.intType) || !second.equals(Tab.intType)) {
			report_error("Greska na " + mulOpTerm.getLine() + ": clanovi izraza nisu tipa int");
		}
		mulOpTerm.struct = mulOpTerm.getTerm().struct;
	}

	public void visit(Increment increment) {
		Obj temp = increment.getDesignator().obj;
		if (!temp.getType().equals(Tab.intType)) {
			report_error("Greska na " + increment.getLine() + " promenljiva " + temp.getName() + " nije int");
		} else if (temp.getKind() != Obj.Var && temp.getKind() != Obj.Elem) {
			report_error("Greska na " + increment.getLine() + " " + temp.getName() + "  nije promenljiva");
		}
	}

	public void visit(Decrement decrement) {
		Obj temp = decrement.getDesignator().obj;
		if (!temp.getType().equals(Tab.intType)) {
			report_error("Greska na " + decrement.getLine() + " promenljiva " + temp.getName() + " nije int");
		} else if (temp.getKind() != Obj.Var && temp.getKind() != Obj.Elem) {
			report_error("Greska na " + decrement.getLine() + " " + temp.getName() + "  nije promenljiva");
		}
	}

	public void visit(PosExpression posExpression) {
		posExpression.struct = posExpression.getTerm().struct;
	}

	public void visit(FactorTerm factorTerm) {
		factorTerm.struct = factorTerm.getFactor().struct;
	}

	public void visit(FactorValue factorValue) {
		factorValue.struct = factorValue.getFactValue().struct;

	}
	public void visit(FNum fNum){
		fNum.struct = Tab.intType;
	}
	public void visit(FChar fChar){
		fChar.struct = Tab.charType;
	}
	public void visit(FBool fBool){
		fBool.struct = boolType;
	}
	/*public void visit(FactorFuncCall factorFunctionCall) {
		factorFunctionCall.struct = factorFunctionCall.getFunctionCall().struct;
	}*/

	public void visit(FactorFuncCall factorFuncCall) {
		if (currMethod.equalsIgnoreCase("main"))
			mainFuncCallCounter++;
		Obj o = factorFuncCall.getDesignator().obj;
		if (o.getKind() != Obj.Meth)
			report_error("Greska u liniji " + factorFuncCall.getLine() + ": ovo nije metoda");
		factorFuncCall.struct = o.getType();
	}
	public void visit(DesignatorFuncCall designatorFuncCall){
		if (currMethod.equalsIgnoreCase("main"))
			mainFuncCallCounter++;
		Obj o =designatorFuncCall.getDesignator().obj;
		if (o.getKind() != Obj.Meth)
			report_error("Greska u liniji " + designatorFuncCall.getLine() + ": ovo nije metoda");
	}
	public void visit(VarFactor varFactor) {
		Obj o = varFactor.getDesignator().obj;
		if (o.getKind() != Obj.Var && o.getKind() != Obj.Elem && o.getKind() != Obj.Con)
			report_error("Greska u liniji " + varFactor.getLine() + ": ovo nije promenljiva ili element niza");
		varFactor.struct = o.getType();
	}

	public void visit(NumValue numVal) {
		numVal.obj = new Obj(Obj.Con, "", Tab.intType);
		numVal.obj.setAdr(numVal.getN1());
	}

	public void visit(CharValue charVal) {
		charVal.obj = new Obj(Obj.Con, "", Tab.charType);
		charVal.obj.setAdr(charVal.getC1());
	}

	public void visit(BoolValue boolVal) {
		boolVal.obj = new Obj(Obj.Con,"",boolType);
		int adr = Boolean.valueOf(boolVal.getB1()) ? 1 : 0;
		boolVal.obj.setAdr(adr);
	}

	public void visit(NewArrayFactor newArrayFactor) {
		if (newArrayFactor.getExpression().struct != Tab.intType)
			{report_error("Greska u liniji " + newArrayFactor.getLine() + ": velicina niza mora biti tipa int");
		newArrayFactor.struct = Tab.noType;
		return;}
		newArrayFactor.struct = new Struct(Struct.Array, newArrayFactor.getType().struct);
	}

	public void visit(ConstDecl constDecl) {
		curr_type = Tab.noType;
	}

	public void visit(ConstPart constPart) {
		Obj temp = constPart.getValue().obj;

		if (temp.getType().equals(curr_type)) {// provera kompatibilnosti i vise
												// defki
			if (Tab.currentScope.findSymbol(constPart.getI1()) == null) {
				Obj temp2 = Tab.insert(temp.getKind(), constPart.getI1(), temp.getType());
				temp2.setAdr(temp.getAdr());
				globalConstCounter++;
			} else {
				report_error(
						"Greska na liniji " + constPart.getLine() + ": " + constPart.getI1() + "  je vec deklarisano");
			}

		} else
			report_error("Greska na liniji " + constPart.getLine() + ": tipovi podataka se ne poklapaju");
	}

	public void visit(Statements statements) {
		if (currMethod.equalsIgnoreCase("main"))
			mainStatementsCounter++;
	}

	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpression().struct;// mozda?
	}

}
