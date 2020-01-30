// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class DesigExprListLBracket implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private DesigExprList DesigExprList;

    public DesigExprListLBracket (DesigExprList DesigExprList) {
        this.DesigExprList=DesigExprList;
        if(DesigExprList!=null) DesigExprList.setParent(this);
    }

    public DesigExprList getDesigExprList() {
        return DesigExprList;
    }

    public void setDesigExprList(DesigExprList DesigExprList) {
        this.DesigExprList=DesigExprList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesigExprList!=null) DesigExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigExprList!=null) DesigExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigExprList!=null) DesigExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigExprListLBracket(\n");

        if(DesigExprList!=null)
            buffer.append(DesigExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigExprListLBracket]");
        return buffer.toString();
    }
}
