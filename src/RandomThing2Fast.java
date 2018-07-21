import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class RandomThing2Fast {
    public static void main(String[] args){
        GridBagConstraints placement = new GridBagConstraints();
        placement.gridx=0;
        placement.gridy=0;
        JPanel window = new JPanel();
        window.setLayout(new GridBagLayout());
        CheckboxGroup sets = new CheckboxGroup();
        Checkbox ordered = new Checkbox("Ordered",sets,true);
        Checkbox weighted = new Checkbox("Weighted",sets,false);
        Checkbox chaotic = new Checkbox("Chaotic",sets,false);
        CheckboxGroup fixedOrder = new CheckboxGroup();
        Checkbox fixed = new Checkbox("Fixed",fixedOrder,false);
        Checkbox unFixed = new Checkbox("Not Fixed",fixedOrder,true);
        window.add(ordered,placement);
        placement.gridy=1;
        window.add(weighted,placement);
        placement.gridy=2;
        window.add(chaotic,placement);
        placement.gridx=1;
        placement.gridy=0;
        window.add(fixed,placement);
        placement.gridy=1;
        window.add(unFixed,placement);
        JFrame output = new JFrame("Bravely Second Four Job Randomizer");
        output.setLayout(new GridBagLayout());
        placement.gridx=0;
        placement.gridy=0;
        output.add(window,placement);
        placement.gridy=1;
        JTextField outText = new JTextField("                                 ");
        outText.setEditable(false);
        outText.setText("                                                                                                                                                     ");
        output.add(outText,placement);
        JButton calculate = new JButton("Calculate");
        JButton stop = new JButton("Stop");
        StopListener stopper = new StopListener();
        stop.addActionListener(stopper);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        placement.gridy=0;
        buttonPanel.add(calculate,placement);
        placement.gridx=1;
        buttonPanel.add(stop,placement);
        SuperListener2Fast betterName = new SuperListener2Fast(stop,stopper,outText,calculate,ordered,weighted,fixed);
        calculate.addActionListener(betterName);
        placement.gridx=0;
        placement.gridy=2;
        output.add(buttonPanel,placement);
        stop.setEnabled(false);
        output.setVisible(true);
    }
}
class SuperListener2Fast implements ActionListener {
    private Checkbox weighted;
    private Checkbox ordered;
    private Checkbox fixed;
    private JTextField outText;
    private JButton calculateButton;
    private boolean fixedVal;
    private StopListener stopper;
    private JButton stop;
    public SuperListener2Fast(JButton stop,StopListener stopper,JTextField outText,JButton calculateButton,Checkbox ordered,Checkbox weighted,Checkbox fixed){
        super();
        this.outText=outText;
        this.stop=stop;
        this.stopper=stopper;
        this.ordered=ordered;
        this.calculateButton=calculateButton;
        this.weighted=weighted;
        this.fixed=fixed;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Runnable calculate= new Runnable(){public void run() {
            calculateButton.setEnabled(false);
            stop.setEnabled(true);
            String output = randomizeController();
            outText.setText(output);
            stop.setEnabled(false);
            calculateButton.setEnabled(true);
        }};
        Thread calcThread = new Thread(calculate);
        stopper.addThread(calcThread);
        calcThread.start();
    }
    private String randomizeController(){
        String output="";
        int[] picked;
        fixedVal =fixed.getState();
        if(ordered.getState()){
            picked=orderedRandomize();
        }else if(weighted.getState()){
            picked=weightedRandomize();
        }else{
            picked=chaoticRandomize();
        }
        boolean debug=false;
        if(!debug){
            if(fixedVal){
                output=fixedInterpreter(picked);
            }else{
                output=unfixedInterpreter(picked);
            }
        }else{
            output=picked[0]+", "+picked[1]+", "+picked[2]+", "+picked[3];
        }
        return output;
    }
    private int[] orderedRandomize(){
        int[] picked = {31,31,31,31};
        //List<String> temp;
        int value=0;
        for(int i=0;i<4;i++){
            //temp=randomer.getRandomIntDecimal(1,5*i+5,1);
            if(Thread.currentThread().isInterrupted()){break;}
            if(fixedVal){
                if(i!=3) {
                    value = (int) (Math.random() * (8 * i + 8)) + 1;
                }else{
                    value = (int) (Math.random() * 29);
                }
            }else {
                if(i!=3) {
                    value = (int) (Math.random() * (7 * i + 8)) + 1;
                }else{
                    value = (int) (Math.random() * 26);
                }
                for (int x = 0; x < i; x++) {
                    if (value >= picked[x]) {
                        value++;
                    }
                }
            }
            picked[i] = value;
            Arrays.sort(picked);

        }
        //temp=randomer.getRandomIntDecimal(1,20,1);
        if(fixedVal){
            for(int i=0;i<4;i++){
                value = (int) (Math.random()*100)+1;
                if (value == 1) {
                    //temp=randomer.getRandomIntDecimal(1,4,1);
                    picked[i] = 30;
                }
            }
        }else {
            value = (int) (Math.random() * 25) + 1;
            if (value == 1) {
                //temp=randomer.getRandomIntDecimal(1,4,1);
                value = (int) (Math.random() * 4) + 1;
                picked[value - 1] = 30;
            }
        }
        return picked;
    }
    private int[] weightedRandomize(){
        int[] picked = {31,31,31,31};
        int max=238004*25;
        //List<String> temp;
        int value;
        for(int i=0;i<4;i++){
            if(Thread.currentThread().isInterrupted()){break;}
            //temp=randomer.getRandomIntDecimal(1,max,1);
            value=(int)(Math.random()*max)+1;
            for(int y=30;y>0;y--){
                boolean pass=false;
                if(!fixedVal) {
                    for (int x = i - 1; x >= 0; x--) {
                        if (y == picked[x]) {
                            pass = true;
                        }
                    }
                }
                if(!pass){
                    if(y==30){
                        value-=238004;
                    }else if(y>24){
                        value-=2048*24;
                    }else if(y>16){
                        value-=4596*24;
                    }else if(y>8){
                        value-=8600*24;
                    }else{
                        value-=17180*24;
                    }


                    if(value<=0){
                        value=y;
                        y=-1;
                    }
                }
            }
            if(!fixedVal) {
                if (value == 30) {
                    max -= 238004;
                } else if (value > 24) {
                    max -= 2048*24;
                } else if (value > 16) {
                    max -= 4596*24;
                } else if (value > 8) {
                    max -= 8600*24;
                } else {
                    max -= 17180*24;
                }
            }
            picked[i]=value;
            Arrays.sort(picked);

        }
        return picked;
    }
    private int[] chaoticRandomize(){
        int[] picked = {31,31,31,31};
        //List<String> temp;
        int value;
        for(int i=0;i<4;i++){
            if(Thread.currentThread().isInterrupted()){break;}
            //temp=randomer.getRandomIntDecimal(1,24-i,1);
            if(fixedVal) {
                value = (int) (Math.random() * (30)) + 1;
            }else {
                value = (int) (Math.random() * (30 - i)) + 1;
                for (int x = 0; x < i; x++) {
                    if (value >= picked[x]) {
                        value++;
                    }
                }
            }
            picked[i]=value;
            Arrays.sort(picked);

        }
        return picked;
    }
    private String unfixedInterpreter(int[] picked){
        String output="";
        for(int i=0;i<4;i++){
            switch(picked[i]){
                case 1:
                    output+="Wizard";
                    break;
                case 2:
                    output+="Charioteer";
                    break;
                case 3:
                    output+="Fencer";
                    break;
                case 4:
                    output+="Bishop";
                    break;
                case 5:
                    output+="Astrologian";
                    break;
                case 6:
                    output+="Catmancer";
                    break;
                case 7:
                    output+="Red Mage";
                    break;
                case 8:
                    output+="Thief";
                    break;
                case 9:
                    output+="Swordmaster";
                    break;
                case 10:
                    output+="Summoner";
                    break;
                case 11:
                    output+="Hawkeye";
                    break;
                case 12:
                    output+="Patissier";
                    break;
                case 13:
                    output+="White Mage";
                    break;
                case 14:
                    output+="Merchant";
                    break;
                case 15:
                    output+="Black Mage";
                    break;
                case 16:
                    output+="Ranger";
                    break;
                case 17:
                    output+="Knight";
                    break;
                case 18:
                    output+="Ninja";
                    break;
                case 19:
                    output+="Exorcist";
                    break;
                case 20:
                    output+="Monk";
                    break;
                case 21:
                    output+="Valkyrie";
                    break;
                case 22:
                    output+="Pirate";
                    break;
                case 23:
                    output+="Performer";
                    break;
                case 24:
                    output+="Time Mage";
                    break;
                case 25:
                    output+="Dark Knight";
                    break;
                case 26:
                    output+="Guardian";
                    break;
                case 27:
                    output+="Kaiser";
                    break;
                case 28:
                    output+="Templar";
                    break;
                case 29:
                    output+="Yokai";
                    break;
                case 30:
                    output+="Freelancer";
                    break;
                default:
                    output+="Bravely";
                    break;
            }
            if(i!=3){
                output+=", ";
            }

        }
        return output;
    }
    private String fixedInterpreter(int[] picked){
        int[] repicked = new int[4];
        int[] info = {5,5,5,5};
        //List<String> temp;
        int value;
        for(int i=0;i<4;i++){
            if(Thread.currentThread().isInterrupted()){break;}
            //temp=randomer.getRandomIntDecimal(1,4-i,1);
            value=(int)(Math.random()*(4-i))+1;
            for(int x=0;x<i;x++){
                if(value>=info[x]){
                    value++;
                }
            }
            info[i]=value;
            repicked[i]=picked[value-1];
            Arrays.sort(info);

        }


        return unfixedInterpreter(repicked);
    }

}
