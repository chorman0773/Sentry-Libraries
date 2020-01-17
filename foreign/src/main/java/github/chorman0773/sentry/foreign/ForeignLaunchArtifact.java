package github.chorman0773.sentry.foreign;

import github.chorman0773.sentry.foreign.wrapper.ForeignContainer;
import github.chorman0773.sentry.launch.LaunchArtifact;

import java.awt.*;

/**
 * Special LaunchArtifact class which enables a game to request a custom graphics context
 *  rather than a simple AWT Container context.
 * Note that this is simply a request. A Sentry Launcher is not required to honor such a request.
 *  Games that implement ForeignLaunchArtifact must be prepared to receive simply AWT Containers,
 *  and function without problems in these cases (note that they are permitted to crash with a meaningful error).
 * If the appropriate wrapper container is obtained, it may be unwrapped to access the underlying custom context,
 *  or may be used naturally as an AWT Container.
 *
 *  <br/>
 *  Appropriate use of this class, for example for SDL:
 *  <code>
 *  public class SDLExampleGame implements ForeignLaunchArtifact&lt;SDLSurface&gt;{<br/>
 *      public Class&lt;SDLSurface&gt; contextType(){<br/>
 *          return SDLSurface.class;<br/>
 *      }<br/>
 *      public void initialise(LauncherInterface lint,String[] args){<br/>
 *          Container c = lint.getCurrentDrawContainer();<br/>
 *          if(c instanceof ForeignContainer&lt;?&gt;){<br/>
 *              Optional&lt;ForeignContainer&lt;SDLSurface&gt;&gt; wrapped = ((ForeignContainer&lt?&gt;)c).checkContextType(SDLSurface.class);<br/>
 *              if(wrapped.isPresent()){<br/>
 *                  //Unwrap and do stuff with the SDLSurface handle<br/>
 *                  return;
 *              }<br/>
 *          }<br/>
 *          // Rather than support AWT, you can crash the game here<br/>
 *          throw new GameCrash("Launcher doesn't support SDL, which this game requires");<br/>
 *      }<br/>
 *      // Other LaunchArtifact methods here <br/>
 *  }<br/>
 *  </code>
 */
public interface ForeignLaunchArtifact<Context> extends LaunchArtifact {
    /**
     * Obtains the type of the wrapped context.
     *
     * Support for any given type of context is launcher-specific, and unspecified.
     */
    public Class<Context> contextType();
}
