import javax.swing.*;
import java.awt.*;

public class GridDrawPanel extends Panel {
    boolean[][] grid;
    int width;
    int height;
    Graphics2D graphics;
    public GridDrawPanel(int width, int height){
        super();
        this.width = width;
        this.height = height;
        grid = new boolean[width][height];
        //graphics = (Graphics2D) this.getGraphics();
        //graphics.draw(new Rectangle(20,20,10,10));
        //this.setSize(200,200);
        //graphics.setClip(0,0,100,100);
        //System.out.println(graphics.getClipBounds().getWidth());
        //System.out.println(graphics.getClipBounds().getHeight());
        //this.setVisible(true);
    }
    public void paint(Graphics g){
        graphics = (Graphics2D) g;
        for(int a=0;a<width;a++){
            for(int b=0;b<height;b++){
                if(grid[a][b]){
                    Rectangle shape = new Rectangle(a*super.getWidth()/width,b*super.getHeight()/height,super.getWidth()/width,super.getHeight()/height);
                    graphics.setColor(Color.BLACK);
                    graphics.fill(shape);
                    graphics.setColor(Color.DARK_GRAY);
                    graphics.draw(shape);

                }
            }
        }
    }
    public void flipSquare(int x, int y){
        grid[x][y]=!grid[x][y];
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }




}
class test{
    public static void main(String[] args){
        GridDrawPanel grid =new GridDrawPanel(12,22);
        OverJFrame frame =new OverJFrame(grid);
        frame.add(grid);
        frame.setSize(200,200);
        frame.setVisible(true);
        Runnable test = new Runnable(){
            public void run(){
                for(int c=0;c<grid.getWidth();c++){
                    for(int d=0;d<grid.getHeight();d++){
                        if(c==0||d==0||c==grid.getWidth()-1||d==grid.getHeight()-1){
                            grid.flipSquare(c,d);
                        }
                    }
                }
                while(true){
                    grid.flipSquare((int)(Math.random()*(grid.getWidth()-2))+1,(int)(Math.random()*(grid.getHeight()-2))+1);
                    try {
                        Thread.sleep(375);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread check = new Thread(test);
        check.start();
        while(true) {
            grid.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
class OverJFrame extends JFrame{
    private GridDrawPanel grid;

    public OverJFrame(GridDrawPanel grid){
        super();
        this.grid = grid;
        grid.setBackground(Color.WHITE);
        //grid.setSize(320,480);
        //grid.setSize(grid.getWidth()*50,grid.getHeight()*50);
    }
    /*
    @Override
    public void repaint(){
        grid.paint(grid.getGraphics());
        super.repaint();
    }
    //*/

}