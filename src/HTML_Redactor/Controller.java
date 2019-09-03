package HTML_Redactor;

import HTML_Redactor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public HTMLDocument getDocument() {
        return document;
    }
    public Controller(View view) {
        this.view = view;
    }
    public void init(){
        createNewDocument();
    }
    public void exit(){ System.exit(0);}
    public void resetDocument() {
        UndoListener listener= view.getUndoListener();
        if(document!=null) {document.removeUndoableEditListener(view.getUndoListener());}

        this.document= (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        this.document.addUndoableEditListener(listener);
        this.view.update();
    }

    public void setPlainText(String text){
        resetDocument();
        StringReader stringReader= new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader,(HTMLDocument) document,0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            ExceptionHandler.log(e);
        }

    }
    public String getPlainText(){

        StringWriter stringWriter= new StringWriter();
        try {
            new HTMLEditorKit().write(stringWriter,document,0,document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }
    public void createNewDocument(){
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile=null;
    }
    public void openDocument(){
        view.selectHtmlTab();
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new HTMLFileFilter());
        if(jfc.showOpenDialog(view)== JFileChooser.APPROVE_OPTION) {
            currentFile = jfc.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try {
                FileReader fr= new FileReader(currentFile);
                new HTMLEditorKit().read(fr,document,0);
                fr.close();
            } catch (FileNotFoundException e) {
                ExceptionHandler.log(e);
            } catch (IOException e) {
                ExceptionHandler.log(e);
            } catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }
            view.resetUndo();

        }
    }
    public void saveDocument(){
        if(currentFile==null) saveDocumentAs();
        else {
            view.selectHtmlTab();
            view.setTitle(currentFile.getName());
            FileWriter fw= null;
            try {
                fw = new FileWriter(currentFile);
                new HTMLEditorKit().write(fw,document,0,document.getLength());
                fw.close();
            } catch (IOException e) {
                ExceptionHandler.log(e);
            } catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }
        }
    }
    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new HTMLFileFilter());
        if(jfc.showSaveDialog(view)== JFileChooser.APPROVE_OPTION) {
            currentFile = jfc.getSelectedFile();
            view.setTitle(currentFile.getName());
            FileWriter fw= null;
            try {
                fw = new FileWriter(currentFile);
                new HTMLEditorKit().write(fw,document,0,document.getLength());
                fw.close();
            } catch (IOException e) {
               ExceptionHandler.log(e);
            } catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }

        }
    }
    public static void main(String[] args) {
        View view= new View();
        Controller controller= new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();

    }
}
