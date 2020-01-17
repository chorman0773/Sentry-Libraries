package github.chorman0773.sentry.authentication.fs;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.DestroyFailedException;
import java.io.Closeable;
import java.io.IOException;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class FileHandle implements SeekableByteChannel, Closeable {
    private WebSocket socket;
    private SecretKey key;
    private Cipher enc;
    private Cipher dec;

    public FileHandle(WebSocket sock,SecretKey key,byte[] encIv,byte[] decIv){
        this.socket = sock;
        this.key = key;
        try {
            this.enc = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.enc.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(encIv));
            this.dec = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.dec.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(decIv));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        return 0;
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        return 0;
    }

    @Override
    public long position() throws IOException {
        return 0;
    }

    @Override
    public SeekableByteChannel position(long l) throws IOException {
        return null;
    }

    @Override
    public long size() throws IOException {
        return 0;
    }

    @Override
    public SeekableByteChannel truncate(long l) throws IOException {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(1);
        buff.put((byte)5);
        socket.sendBinary(buff,true).join().sendClose(1000,"closehandle");
        try {
            key.destroy();
        } catch (DestroyFailedException e) {
            throw new IOException(e);
        }
    }
}
