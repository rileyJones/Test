package org.nwapw.abacus.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FunctionBuilder {
    public static void main(String[] args) throws IOException {
        Scanner stdin = new Scanner(System.in);
        String name = stdin.next();
        File base;
        if(name.endsWith(".java")){
            base = new File(name);
        }else {
            base = new File(name + ".java");
        }

        FileWriter fileFill = new FileWriter(base);
        fileFill.write("package org.nwapw.abacus.plugin;\n" +
                "\n" +
                "import org.nwapw.abacus.function.Function;\n" +
                "import org.nwapw.abacus.function.Operator;\n" +
                "import org.nwapw.abacus.function.OperatorAssociativity;\n" +
                "import org.nwapw.abacus.number.NaiveNumber;\n" +
                "import org.nwapw.abacus.number.NumberInterface;\n" +
                "\n" +
                "import java.util.function.BiFunction;\n");
        fileFill.write("public class "+name+" extends Plugin{\n");
        fileFill.write("public "+name+"(PluginManager manager) {\n" +
                "super(manager);\n" +
                "}\n" +
                "@Override\n" +
                "public void onEnable(){\n" +
                "");


        ArrayList<String> functions = new ArrayList<>();
        System.out.println("Enter Commands:")
        while(true){
            name=stdin.next();
            if(name.equalsIgnoreCase("exit")) {
                break;
            }
            functions.add(name);
        }
        for(String function:functions){

        }
        fileFill.close();
        base.createNewFile();
    }
}
