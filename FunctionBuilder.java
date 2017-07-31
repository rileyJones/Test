import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FunctionBuilder {
    public static void main(String[] args) throws IOException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("File name: ");
        String input = stdin.next();
        File base;
        if (input.endsWith(".java")) {
            base = new File(input);
        } else {
            base = new File(input + ".java");
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
        fileFill.write("public class " + input + " extends Plugin{\n");
        fileFill.write("public " + input + "(PluginManager manager) {\n" +
                "super(manager);\n" +
                "}\n" +
                "@Override\n" +
                "public void onEnable(){\n" +
                "registerNumber(\"naive\", NaiveNumber.class);\n" +
                "registerNumber(\"precise\", PreciseNumber.class);\n");
        System.out.print("Add a function y/n: ");
        ArrayList<FunctionData> functions = new ArrayList<>();
        while(stdin.next().equalsIgnoreCase("y")){
            functions.add(new FunctionData());
            System.out.print("Function name: ");
            functions.get(functions.size()-1).setName(stdin.next());
            System.out.print("Function or Operator: ");
            functions.get(functions.size()-1).setIsOperator(stdin.next());
            if(functions.get(functions.size()-1).getIsOperator()){
                System.out.print("Number of operators: ");
                functions.get(functions.size()-1).setOperatorCount(stdin.nextInt());
                System.out.print("Direction of first input: ");
                functions.get(functions.size()-1).setDirection(stdin.next());
            }
            stdin.nextLine();
            while(!input.equals("end")){
                System.out.println("Next Command (help)");
                input = stdin.nextLine().toLowerCase();
                if(input.equals("help")){
                    System.out.println("Commands:\n" +
                            "init x: Creates an internal variable with the name x and value of 0\n" +
                            "loop x: Repeats the operation x times or until x is false\n" +
                            "if x: Does the operation if x is true\n" +
                            "end: Ends the function creation\n" +
                            "Operations:\n" +
                            "a is first input\n" +
                            "b is the second input\n" +
                            "c is the returned result\n" +
                            "ex: c=a+(b-a)");
                }else if(!input.equals("end")){
                    functions.get(functions.size()-1).addData(input);
                }
            }
            System.out.print("Add another function y/n: ");
        }
        for(FunctionData function:functions){
            if(function.getIsOperator()){
                fileFill.write("registerOperator(\""+ function.getName() +"\", new Operator(OperatorAssociativity."+function.getDirection()+", OperatorType.");
                if(function.getOperatorCount()==1){
                    fileFill.write("UNARY_POSTFIX");
                }else if(function.getOperatorCount()==2){
                    fileFill.write("BINARY_INFIX");
                }
                fileFill.write(0+", new Function(){\n" +
                        "@Override\n" +
                        "protected boolean matchesParams(NumberInterface[] params) {\n" +
                        "return params.length == "+1+";\n" +
                        "}\n");

            }
        }
        fileFill.close();
        base.createNewFile();
    }
}



class FunctionData{
    private String name;
    private ArrayList<String> data;
    private boolean isOperator;
    private int operatorCount;
    private String direction;
    public FunctionData(){
        data = new ArrayList<>();
    }
    public void setName(String name){
        this.name = name;
    }
    public void setIsOperator(String type) {
        if(type.equalsIgnoreCase("function")){
            isOperator = false;
        }else if(type.equalsIgnoreCase("operator")){
            isOperator = true;
        }
    }

    public boolean getIsOperator() {
        return isOperator;
    }
    public void setOperatorCount(int operatorCount) {
        this.operatorCount = operatorCount;
    }

    public void setDirection(String direction) {
        this.direction=direction.toUpperCase();
    }
    public void addData(String line){
        data.add(line);
    }

    public String getName() {
        return name;
    }
    public String getDirection(){
        return direction;
    }

    public int getOperatorCount() {
        return operatorCount;
    }
}