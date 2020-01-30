// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private RetTypeIdent RetTypeIdent;
    private LocalVarDeclList LocalVarDeclList;

    public MethodDeclStart (RetTypeIdent RetTypeIdent, LocalVarDeclList LocalVarDeclList) {
        this.RetTypeIdent=RetTypeIdent;
        if(RetTypeIdent!=null) RetTypeIdent.setParent(this);
        this.LocalVarDeclList=LocalVarDeclList;
        if(LocalVarDeclList!=null) LocalVarDeclList.setParent(this);
    }

    public RetTypeIdent getRetTypeIdent() {
        return RetTypeIdent;
    }

    public void setRetTypeIdent(RetTypeIdent RetTypeIdent) {
        this.RetTypeIdent=RetTypeIdent;
    }

    public LocalVarDeclList getLocalVarDeclList() {
        return LocalVarDeclList;
    }

    public void setLocalVarDeclList(LocalVarDeclList LocalVarDeclList) {
        this.LocalVarDeclList=LocalVarDeclList;
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
        if(RetTypeIdent!=null) RetTypeIdent.accept(visitor);
        if(LocalVarDeclList!=null) LocalVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RetTypeIdent!=null) RetTypeIdent.traverseTopDown(visitor);
        if(LocalVarDeclList!=null) LocalVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RetTypeIdent!=null) RetTypeIdent.traverseBottomUp(visitor);
        if(LocalVarDeclList!=null) LocalVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclStart(\n");

        if(RetTypeIdent!=null)
            buffer.append(RetTypeIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarDeclList!=null)
            buffer.append(LocalVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclStart]");
        return buffer.toString();
    }
}
