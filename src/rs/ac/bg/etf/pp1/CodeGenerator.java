package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	private Stack<Integer> addsub;
	private Stack<Integer> muldivmod;
	CodeGenerator(){
		super();
		addsub = new Stack<>();
		muldivmod = new Stack<>();
	}
/*****************************************************************
*  Vraca broj elemenata u listi Obj cvorova
*****************************************************************/ 	
	public int nVars(Obj o) {
		int n = 0;
		Collection<Obj> temp = o.getLocalSymbols();
		n = temp.size();
		return n;
	}
	public int getMainPc(){
		return mainPc;
	}

	public void visit(Program program) {
		Code.dataSize=nVars(program.getProgName().obj);
	}
	public void visit(ProgName progrName){
		
		
		Tab.chrObj.setAdr(Code.pc);
		Tab.ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n + 0);
		Code.put(Code.exit);
		Code.put(Code.return_);
		Tab.lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n+0);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

    public void visit(MethodDeclStart methodDeclStart) {
		if (methodDeclStart.getRetTypeIdent().obj.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}
		Obj o = methodDeclStart.getRetTypeIdent().obj; // Obj cvor zapamcen tokom sint. analize
        o.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(o.getLevel());
        Code.put(nVars(o)); // velicina locals liste metoda
	}


	public void visit(MethodDecl methodDecl) {
		Obj o = methodDecl.getMethodDeclStart().getRetTypeIdent().obj;
		Struct methodType = o.getType();
		if (methodType==Tab.noType) {
			Code.put(Code.exit); 
			Code.put(Code.return_);
		} else { 
			Code.put(Code.trap); 
			Code.put(1);
		}
	}
	/*****************************************************************
	*  Aritmeticke operacije i dodela
	*****************************************************************/
	public void visit(Assignment assignment) {
		Code.store(assignment.getDesignator().obj);
	}
	public void visit(MulOpTerm mulOpTerm){
		Code.put(muldivmod.pop());
	}
	public void visit(ExpressionOp expressionOp){
		Code.put(addsub.pop());
	}
	public void visit(Multiply multiply){
		muldivmod.push(Code.mul);
	}
	public void visit(Divide divide){
		muldivmod.push(Code.div);
	}
	public void visit(Module module){
		muldivmod.push(Code.rem);
	}
	public void visit(Add add){
		addsub.push(Code.add);
	}
	public void visit(Subtract subtract){
		addsub.push(Code.sub);
	}
	public void visit(NegExpression negExpression){
		Code.put(Code.neg);
	}
	/*****************************************************************
	*  Return iskazi
	*****************************************************************/
	public void visit(ReturnEmptyStatement returnEmptyStatement) { 
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnStatement returnStatement) { 
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	/*****************************************************************
	*  Print i Read
	*****************************************************************/
	public void visit(PrintStatement printStatement) { 
		Struct temp = printStatement.getExpression().struct;
		if (temp == Tab.intType || temp == SemanticAnalyzer.boolType) {
			Code.loadConst(5); // sirina ispisa na e-stek, expr je vec na e-steku
			Code.put(Code.print);
		} else {
			Code.loadConst(1); // sirina ispisa na e-stek, expr je vec na e-steku
			Code.put(Code.bprint);
		}		
	}
	public void visit(PrintWithNumStatement printWithNumStatement){
		int num = printWithNumStatement.getN2();
		Struct temp =printWithNumStatement.getExpression().struct;
		if(temp.equals(Tab.charType)){
			// sta sa bool?
			Code.loadConst(num);
			Code.put(Code.bprint);
		}else {
			Code.loadConst(num);
			Code.put(Code.print);
		}
	}
	public void visit(ReadStatement readStatement){
		Obj temp = readStatement.getDesignator().obj;
		if(temp.getType().equals(Tab.charType)){
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
			// sta za bool
		}
		Code.store(temp);
	}
	/*****************************************************************
	*  Pozivi metoda
	*****************************************************************/
	public void visit(DesigExprListLBracket desigExprListLBracket) {
		Obj o = desigExprListLBracket.getDesigExprList().obj;
		Code.load(o);
	}
	
    public void visit(FactorFuncCall factorFuncCall) {
		
		Obj o = factorFuncCall.getDesignator().obj;
        int dest_adr=o.getAdr()-Code.pc; // racunanje relativne adrese 
        Code.put(Code.call); 
        Code.put2(dest_adr);
	}
    public void visit(DesignatorFuncCall designatorFuncCall){
    	//ako metoda vraca rezultat,treba ga skinuti sa steka
    	Struct temp = designatorFuncCall.getDesignator().obj.getType();
    	Obj o = designatorFuncCall.getDesignator().obj;
    	int dest_adr=o.getAdr()-Code.pc;
    	Code.put(Code.call); 
        Code.put2(dest_adr);
		if (!temp.equals(Tab.noType))
			Code.put(Code.pop);
    }
    /*****************************************************************
  	*  Ucitavanje vrednosti
  	*****************************************************************/
	public void visit(VarFactor varFactor) {
		Obj o = varFactor.getDesignator().obj;
        Code.load(o);
	}
	public void visit(FNum fNum) {
		Obj o = new Obj(Obj.Con, "", Tab.intType);
        o.setAdr(fNum.getN1()); 
        o.setLevel(0);
		Code.load(o); 
	}
	public void visit(FChar fChar) {
		Obj o = new Obj(Obj.Con, "", Tab.charType);
        o.setAdr(fChar.getC1()); 
        o.setLevel(0);
		Code.load(o); 
		
	}
	public void visit(FBool fBool){
		Obj o = new Obj(Obj.Con,"",SemanticAnalyzer.boolType);
		int adr =Boolean.valueOf(fBool.getB1())?1:0;
		o.setAdr(adr);
		o.setLevel(0);
		Code.load(o);
	}
	/*public void visit(FactorValue factorValue){
		Code.load(factorValue.getValue().obj);
	}*/
	
    public void visit(NewArrayFactor newArrayFactor) {
        Code.put(Code.newarray);
        if ( newArrayFactor.getType().struct == Tab.charType ) 
			Code.put(0); 
        else 
			Code.put(1);
	}
    /*****************************************************************
	*  Increment i decrement
	*****************************************************************/
    public void visit(Increment increment){
		Obj temp = increment.getDesignator().obj;
		//ako je clan niza, treba sacuvati vrednosti na steku
		if (temp.getKind() == Obj.Elem)
            Code.put(Code.dup2);
		Code.load(temp);
		Code.loadConst(1);
		Code.put(Code.add);// Code.put(Code.inc)?
		Code.store(temp);
	}
    public void visit(Decrement decrement){
		Obj temp = decrement.getDesignator().obj;
		//ako je clan niza, treba sacuvati vrednosti na steku
		if (temp.getKind() == Obj.Elem)
            Code.put(Code.dup2);
		Code.load(temp);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(temp);
	}
}