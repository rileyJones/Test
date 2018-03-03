import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopListener implements ActionListener{
    private Thread stopping;
    public StopListener(){
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopping.interrupt();
    }
    public void addThread(Thread stopping){
        this.stopping=stopping;
    }
}
