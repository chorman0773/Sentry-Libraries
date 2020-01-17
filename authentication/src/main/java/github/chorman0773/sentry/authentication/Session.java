package github.chorman0773.sentry.authentication;

import com.google.gson.*;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

/**
 * A session is a handle to a Sentry Session Key and the underlying state necessary to make requests.
 *  Games should not produce sessions themselves,
 *  rather should use the SessionManagerService to obtain new Sessions.
 *
 * In order to create new Sessions, the AuthorizationPermission("create") is necessary for the requested scopes.
 *  This Permission is considered Critically Dangerous, and requires a signed game.
 *
 * The methods destroy() and close() are considered identical,
 *  except that any sessions derived sessions are also closed() on a call to close().
 *  With destroy() the sessions are not closed,
 *   though the server will close them automatically.
 */
public class Session implements Destroyable, Closeable {
    private KeyPair keys;
    private HttpClient manager;
    private byte[] t1;
    private byte[] t2;
    private boolean destroyed;

    private static final Gson gson = new GsonBuilder().create();
    private static final URI uri = URI.create("https://sentry-auth.glitch.me");
    private static final KeyPairGenerator generator;
    private static final Signature signature;
    private static final MessageDigest digest;
    private static final Base64.Encoder mimeEncoder = Base64.getMimeEncoder();
    private static final Base64.Encoder jsonEncoder = Base64.getEncoder();

    private static void handleError(JsonObject o) throws AuthenticationException{
        int code = o.getAsJsonPrimitive("code").getAsInt();
        String message = o.getAsJsonPrimitive("msg").getAsString();
        throw new AuthenticationException(message,code);
    }

    static{
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            signature = Signature.getInstance("SHA256withRSA");
            digest = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Session(byte[] token, String... scopes) throws IOException, InterruptedException, DestroyFailedException, AuthenticationException {
        System.getSecurityManager().checkPermission(new AuthorizationPermission("create",Set.of(scopes)));
        manager = HttpClient.newHttpClient();
        keys = generator.generateKeyPair();
        String publicKeyEncoded = Base64.getEncoder().encodeToString(keys.getPublic().getEncoded());
        try {
            JsonObject reqObj = new JsonObject();
            reqObj.addProperty("key", publicKeyEncoded);
            reqObj.add("scopes", Arrays.stream(scopes).collect(JsonArray::new, JsonArray::add, JsonArray::addAll));
            HttpResponse<byte[]> res = manager.send(HttpRequest.newBuilder(uri.resolve("/session/new"))
                    .header("Authentication", Base64.getMimeEncoder().encodeToString(token))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(reqObj)))
                    .build(), HttpResponse.BodyHandlers.ofByteArray());
            if (res.statusCode() == 200) {
                byte[] challenge = res.body();
                try {
                    signature.initSign(keys.getPrivate());
                    signature.update(challenge);
                    var validateRes = manager.send(HttpRequest.newBuilder(uri.resolve("/session/validate"))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(signature.sign()))
                    .header("Authentication",publicKeyEncoded)
                    .build(), HttpResponse.BodyHandlers.ofInputStream());
                    if(validateRes.statusCode()!=204)
                        handleError(JsonParser.parseReader(new InputStreamReader(validateRes.body(),StandardCharsets.UTF_8)).getAsJsonObject());
                    t1 = digest.digest(challenge);
                    t2 = digest.digest(t1);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException("Err", e);
                }
            }
        }catch(RuntimeException | Error | AuthenticationException e){
            keys.getPrivate().destroy();
            throw e;
        }catch(Exception e){
            keys.getPrivate().destroy();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() throws IOException {
        if(destroyed)
            return;
        try {
            signature.initSign(keys.getPrivate());
            signature.update(t2);
            String sig = mimeEncoder.encodeToString(signature.sign());
            manager.send(HttpRequest.newBuilder(uri.resolve("/session/current"))
            .DELETE()
            .header("Authorization",mimeEncoder.encodeToString(keys.getPublic().getEncoded())+';'+sig)
            .build(), HttpResponse.BodyHandlers.discarding());
            keys.getPrivate().destroy();
            Arrays.fill(t1,(byte)0);
            Arrays.fill(t2,(byte)0);
            this.destroyed = true;
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        } catch (InvalidKeyException | DestroyFailedException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() throws DestroyFailedException {
        if(destroyed)
            return;
        try{
            try {
                close();
            } catch (IOException e) {
                keys.getPrivate().destroy();
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | Error | DestroyFailedException e){
            Arrays.fill(t1,(byte)0);
            Arrays.fill(t2,(byte)0);
            this.destroyed = true;
            throw e;
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
