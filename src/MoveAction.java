import javax.swing.*;
import java.awt.event.ActionEvent;
public class MoveAction extends AbstractAction {
    private int x;
    private Platform p;

    public MoveAction(int x, Platform p) {
        this.x = x;
        this.p = p;
    }

    public void actionPerformed(ActionEvent e) {
        p.update(x);
    }
}