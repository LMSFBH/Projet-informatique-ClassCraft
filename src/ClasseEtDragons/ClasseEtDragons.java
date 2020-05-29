

package ClasseEtDragons;


public class ClasseEtDragons{

    public static void main(String[] args){
        MainFrame frame;
        if((args.length == 0) || (args[0] == null))
            frame = new MainFrame(null);
        else
            frame = new MainFrame(args[0]);
        
        frame.setDefaultCloseOperation(MainFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
}