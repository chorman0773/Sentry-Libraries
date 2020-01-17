package github.chorman0773.sentry.launcher.search;

import github.chorman0773.sentry.launch.LoadingPhase;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import java.text.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GameAnnotationParser extends AnnotationVisitor {
    private String uuid;
    private String gameId;
    private String version;
    private List<LoaderParser> loaders;

    static class LoadingHookParser extends AnnotationVisitor{
        private LoadingPhase phase;
        private String hookCl;
        public LoadingHookParser(int api) {
            super(api);
        }

        public LoadingHookParser(int api, AnnotationVisitor annotationVisitor) {
            super(api, annotationVisitor);
        }

        @Override
        public void visit(String name, Object value) {
            super.visit(name, value);
            if(name.equals("hook"))
                hookCl = ((Type)value).getClassName();
        }

        @Override
        public void visitEnum(String name, String descriptor, String value) {
            super.visitEnum(name,descriptor,value);
            if(name.equals("phase")&&descriptor.equals("github/chorman0773/sentry/launch/LoadingPhase"))
                phase = LoadingPhase.valueOf(value);
        }
    }

    static class ClassListVisitor extends AnnotationVisitor{
        private List<String> list;
        public ClassListVisitor(int api) {
            super(api);
            this.list = new ArrayList<>();
        }

        public ClassListVisitor(int api, AnnotationVisitor annotationVisitor) {
            super(api, annotationVisitor);
            this.list = new ArrayList<>();
        }
    }

    static class LoaderParser extends AnnotationVisitor{
        private String clName;
        private AnnotationVisitor successHandlers;
        private AnnotationVisitor failureHandlers;

        public LoaderParser(int api) {
            super(api);
        }

        public LoaderParser(int api, AnnotationVisitor annotationVisitor) {
            super(api, annotationVisitor);
        }

        @Override
        public void visit(String name, Object value) {
            super.visit(name,value);
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            return super.visitArray(name);
        }
    }


    public GameAnnotationParser(int api) {
        super(api);
    }

    public GameAnnotationParser(int api, AnnotationVisitor annotationVisitor) {
        super(api, annotationVisitor);
    }
}
