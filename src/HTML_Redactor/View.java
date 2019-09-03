package HTML_Redactor;

import HTML_Redactor.listeners.FrameListener;
import HTML_Redactor.listeners.TabbedPaneChangeListener;
import HTML_Redactor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane(); //panel with 2 bookmarks
    private JTextPane htmlTextPane = new JTextPane(); //visual html redactor
    private JEditorPane plainTextPane = new JEditorPane(); // html text radector
    //---
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener= new UndoListener(undoManager);

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public View() throws HeadlessException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }
    public void exit(){controller.exit();}
    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }
    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }
    public void initMenuBar() {
        JMenuBar jMenuBar= new JMenuBar();
        MenuHelper.initFileMenu(this,jMenuBar);
        MenuHelper.initEditMenu(this,jMenuBar);
        MenuHelper.initStyleMenu(this,jMenuBar);
        MenuHelper.initAlignMenu(this,jMenuBar);
        MenuHelper.initColorMenu(this,jMenuBar);
        MenuHelper.initFontMenu(this,jMenuBar);
        MenuHelper.initHelpMenu(this,jMenuBar);
        getContentPane().add(jMenuBar,BorderLayout.NORTH);
    }
    public void initEditor() {
        htmlTextPane.setContentType("text/html");
        JScrollPane jScrollhtmlPane= new JScrollPane(htmlTextPane);
        tabbedPane.addTab("HTML",jScrollhtmlPane);
        JScrollPane jScrolltextPane= new JScrollPane(plainTextPane);
        tabbedPane.addTab("Текст",jScrolltextPane);

        tabbedPane.setPreferredSize(new Dimension(200, 200));
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        getContentPane().add(tabbedPane,BorderLayout.CENTER);

    }

    public void selectedTabChanged(){
        int num = tabbedPane.getSelectedIndex();
        if(num==0) controller.setPlainText(plainTextPane.getText());
        else if(num==1) plainTextPane.setText(controller.getPlainText());
        resetUndo();
    }
    public boolean canUndo(){
        return undoManager.canUndo();
    }
    public boolean canRedo(){
        return undoManager.canRedo();
    }
    public void undo(){
        try {
            undoManager.undo();
        } catch (Exception e){ExceptionHandler.log(e);}
    }
    public void redo() {
        try{
        undoManager.redo();
        } catch (Exception e){ExceptionHandler.log(e);}
    }
    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected(){
        int num = tabbedPane.getSelectedIndex();
        if(num== 0) return true;
        else return false;
    }
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        this.resetUndo();
    }
    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }
    public void showAbout(){
        JOptionPane.showMessageDialog(this,"Version 1.0", "About", JOptionPane.INFORMATION_MESSAGE);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Новый")) controller.createNewDocument();
        if(command.equals("Открыть")) controller.openDocument();
        if(command.equals("Сохранить")) controller.saveDocument();
        if(command.equals("Сохранить как...")) controller.saveDocumentAs();
        if(command.equals("Выход")) controller.exit();
        if(command.equals("О программе")) showAbout();


    }
}
