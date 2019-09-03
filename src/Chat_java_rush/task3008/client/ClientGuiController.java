package Chat_java_rush.task3008.client;


public class ClientGuiController extends Client {
    private ClientGuiModel model = new ClientGuiModel();
    private ClientGuiView view = new ClientGuiView(this);
    //В классе ClientGuiController должны быть корректно объявлены
    // и инициализированы поля перечисленные в условии задачи.
    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }
    @Override
    protected String getUserName() {
        return view.getUserName();
    }
    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }
    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }
    public ClientGuiModel getModel(){
        return this.model;
    }
    @Override
    public void run() {
        getSocketThread().run();
    }

    public static void main(String[] args) {
        ClientGuiController clientGuiController=new ClientGuiController();
        clientGuiController.run();
    }

    public class GuiSocketThread extends SocketThread {
        @Override
        protected void processIncomingMessage(String message) {
            model.setNewMessage(message);
            view.refreshMessages();
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
        }
    }


}