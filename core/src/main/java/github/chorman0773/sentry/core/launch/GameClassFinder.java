package github.chorman0773.sentry.core.launch;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;

public class GameClassFinder extends ClassVisitor {
    public GameClassFinder(int api) {
        super(api);
    }

    public GameClassFinder(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    static class GatherLaunchArtifacts extends AnnotationVisitor {


         public GatherLaunchArtifacts(int api) {
             super(api);
         }

         public GatherLaunchArtifacts(int api, AnnotationVisitor annotationVisitor) {
             super(api, annotationVisitor);
         }
     }
}
