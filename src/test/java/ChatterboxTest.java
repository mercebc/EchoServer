import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class ChatterboxTest {

    private Socket clientSocket;
    private ServerSocketStub ssStub;
    private Chatterbox server;
    private PrintStream output;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() throws IOException {
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);

        String mockInput = "Hello";
        input = new ByteArrayInputStream(mockInput.getBytes());

        clientSocket = new SocketStub(input, output);
        ssStub = new ServerSocketStub();

        ssStub.createSocket(clientSocket);
        server = new Chatterbox(ssStub);
    }

    @Test
    public void acceptsASocket() throws IOException{
        server.connect();
        assertThat(server.getSocket(), is(clientSocket));
    }

    @Test
    public void acceptsHasBeenCalled() throws IOException{
        server.connect();
        assertThat(ssStub.hasAcceptBeenCalled(), is(true));
    }

    @Test
    public void sendsAMessageBack() throws IOException {
        server.connect();
        server.echo();
        assertThat(out.toString().trim(), is("Hello"));
    }



}