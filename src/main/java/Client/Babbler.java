package Client;

import StreamSocket.StreamSocket;

import java.io.IOException;

public class Babbler {
    private ClientIO ci;
    private StreamSocket ss;

    public Babbler(StreamSocket ss, ClientIO ci) {
        this.ss = ss;
        this.ci = ci;
    }

    public void introduceYourself() throws IOException {
        ci.inform("Introduce the name of your server:");
        ss.printToSocket(ci.getInput());
        receive(); //receive welcome message
        this.run();
    }

    public void run() {
        while (true) {
            try {
                String message = ci.getInput();
                if (!wantsToStop(message)) {
                    send(message);
                    receive();
                } else {
                    ss.closeConnection();
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        ss.printToSocket(message);
    }

    public void receive() throws IOException {
        ci.inform(ss.readFromSocket());
    }

    private boolean wantsToStop(String message) {
        return ((message == null) || message.equalsIgnoreCase("QUIT"));
    }

}
