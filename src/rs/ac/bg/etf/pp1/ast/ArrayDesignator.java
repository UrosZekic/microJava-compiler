// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class ArrayDesignator extends DesigExprList {

    private DesigExprListLBracket DesigExprListLBracket;
    private Expression Expression;

    public ArrayDesignator (DesigExprListLBracket DesigExprListLBracket, Expression Expression) {
        this.DesigExprListLBracket=DesigExprListLBracket;
        if(DesigExprListLBracket!=null) DesigExprListLBracket.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public DesigExprListLBracket getDesigExprListLBracket() {
        return DesigExprListLBracket;
    }

    public void setDesigExprListLBracket(DesigExprListLBracket DesigExprListLBracket) {
        this.DesigExprListLBracket=DesigExprListLBracket;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesigExprListLBracket!=null) DesigExprListLBracket.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigExprListLBracket!=null) DesigExprListLBracket.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigExprListLBracket!=null) DesigExprListLBracket.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayDesignator(\n");

        if(DesigExprListLBracket!=null)
            buffer.append(DesigExprListLBracket.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayDesignator]");
        return buffer.toString();
    }
}
