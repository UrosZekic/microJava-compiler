
package rs.ac.bg.etf.pp1;

import java_cup.runtime.*;
import java.io.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

class Compiler {
	
	public static void tsdump() {
		SymbolTableVisitorPrint ss = new SymbolTableVisitorPrint(1);
																	
		Tab.dump(ss);
	}

	public static void main(String args[]) throws Exception {
		if (args.length<2){
			System.err.println("Moraju se navesti src i dst");
			return;
		}
		Logger log = Logger.getLogger(Compiler.class);
		BasicConfigurator.configure();//mozda ne treba
		FileReader r = null;
		try {
			 r = new FileReader(args[0]);
			
		} catch (Exception e) {
			System.err.println("Greska pri otvaranju ulaznog fajla");
			System.exit(1);
		}
		log.info("Kompajlira se izvorni fajl ");
		Yylex skener = new Yylex(r);
		MJParser p = new MJParser(skener);
		Symbol s = p.parse();
		if (!p.parseError) {
			Program prog = (Program) (s.value);
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");
			System.out.println("==================SEMANTICKA OBRADA====================");
			
			
			SemanticAnalyzer.init();
			SemanticAnalyzer sa = new SemanticAnalyzer();
			prog.traverseBottomUp(sa);
			if (!sa.mainExists){
				System.err.println("Ne postoji main metoda");
			}
			if (!sa.semanticError && sa.mainExists) {
				
				CodeGenerator cg = new CodeGenerator();
				prog.traverseBottomUp(cg);
				tsdump();
				sa.counters();
				File objFile = new File(args[1]);
				if (objFile.exists())
					objFile.delete();
				try {
					if (!sa.semanticError && !Code.greska) {
						System.out.println("USPESNO ZAVRSENO");
						 Code.write(new FileOutputStream(objFile));
					} else {
						System.err.println("Kod nije izgenerisan zbog gresaka u semantickoj analizi ili samom generisanju koda");
					}

				} catch (Exception e) {
					System.err.println("Greska pri kreiranju objektnog fajla");
				}
			} else {
				System.err.println("Greska prilikom semanticke analize");
			}
		} else {
			System.err.println("Greska prilikom parsiranja");
		}
	}
}
