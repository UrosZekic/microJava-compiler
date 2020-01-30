// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(VarPart VarPart);
    public void visit(Factor Factor);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(MulOp MulOp);
    public void visit(VarList VarList);
    public void visit(VarDeclList VarDeclList);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Statement Statement);
    public void visit(AddOp AddOp);
    public void visit(DesigExprList DesigExprList);
    public void visit(ConstList ConstList);
    public void visit(Term Term);
    public void visit(RetTypeIdent RetTypeIdent);
    public void visit(FactValue FactValue);
    public void visit(StatementList StatementList);
    public void visit(Value Value);
    public void visit(Expression Expression);
    public void visit(LocalVarDeclList LocalVarDeclList);
    public void visit(Module Module);
    public void visit(Divide Divide);
    public void visit(Multiply Multiply);
    public void visit(Subtract Subtract);
    public void visit(Add Add);
    public void visit(FBool FBool);
    public void visit(FChar FChar);
    public void visit(FNum FNum);
    public void visit(NewArrayFactor NewArrayFactor);
    public void visit(ExprFactor ExprFactor);
    public void visit(FactorValue FactorValue);
    public void visit(FactorFuncCall FactorFuncCall);
    public void visit(VarFactor VarFactor);
    public void visit(MulOpTerm MulOpTerm);
    public void visit(FactorTerm FactorTerm);
    public void visit(ExpressionOp ExpressionOp);
    public void visit(PosExpression PosExpression);
    public void visit(NegExpression NegExpression);
    public void visit(DesigExprListLBracket DesigExprListLBracket);
    public void visit(SimpleDesignator SimpleDesignator);
    public void visit(ArrayDesignator ArrayDesignator);
    public void visit(Designator Designator);
    public void visit(DesignatorFuncCall DesignatorFuncCall);
    public void visit(Decrement Decrement);
    public void visit(Increment Increment);
    public void visit(BeforeEqual BeforeEqual);
    public void visit(AfterEqual AfterEqual);
    public void visit(Assignment Assignment);
    public void visit(PrintWithNumStatement PrintWithNumStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnEmptyStatement ReturnEmptyStatement);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(BaseStatement BaseStatement);
    public void visit(NoStatement NoStatement);
    public void visit(Statements Statements);
    public void visit(MethodDeclStart MethodDeclStart);
    public void visit(MethodDecl MethodDecl);
    public void visit(RetType RetType);
    public void visit(RetVoid RetVoid);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(NoLocalVars NoLocalVars);
    public void visit(LocalDeclarations LocalDeclarations);
    public void visit(VarError VarError);
    public void visit(VarArrayId VarArrayId);
    public void visit(VarId VarId);
    public void visit(VarOrArrayId VarOrArrayId);
    public void visit(VarIdList VarIdList);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(Type Type);
    public void visit(BoolValue BoolValue);
    public void visit(CharValue CharValue);
    public void visit(NumValue NumValue);
    public void visit(ConstPart ConstPart);
    public void visit(ConstId ConstId);
    public void visit(ConstIdList ConstIdList);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoConstDecl NoConstDecl);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
