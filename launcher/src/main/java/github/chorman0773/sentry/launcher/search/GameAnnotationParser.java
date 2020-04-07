package github.chorman0773.sentry.launcher.search;

import github.chorman0773.sentry.GameCrash;
import github.chorman0773.sentry.annotation.LoadingHook;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LoadingPhase;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

        public LoadingHook parse(ClassLoader in){
            return new LoadingHook(){

                @Override
                public Class<? extends Annotation> annotationType() {
                    return LoadingHook.class;
                }

                @Override
                public LoadingPhase phase() {
                    return phase;
                }

                @Override
                public Class<? extends Consumer<LauncherInterface>> hook() {
                    try {
                        var cl =Class.forName(hookCl,false,in);

                        return (Class<? extends Consumer<LauncherInterface>>) cl;
                    } catch (ClassNotFoundException e) {
                        throw new GameCrash();
                    }
                }
            };
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
