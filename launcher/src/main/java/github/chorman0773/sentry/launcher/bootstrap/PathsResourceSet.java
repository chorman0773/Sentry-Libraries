package github.chorman0773.sentry.launcher.bootstrap;

import github.lightningcreations.lcjei.resources.Resource;
import github.lightningcreations.lcjei.resources.ResourceSet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathsResourceSet implements ResourceSet<Path> {
    private List<Path> roots;
    public PathsResourceSet(List<Path> roots){
        this.roots = roots;
    }

    private static class PathResource implements Resource<Path>{
        private final Path path;
        private final Path root;
        PathResource(Path root,Path path){
            this.path = path;
            this.root = root;
        }

        @Override
        public InputStream getReadStream() {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        public SeekableByteChannel getReadChannel() {
            try {
                return Files.newByteChannel(path, StandardOpenOption.READ);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        public Path getKey() {
            return root.relativize(path);
        }
    }

    public static Optional<Resource<Path>> findResource(Path root,Path p){
        if(Files.isReadable(root.resolve(p)))
            return Optional.of(new PathResource(root,root.resolve(p)));
        else
            return Optional.empty();
    }

    @Override
    public Optional<Resource<Path>> getResource(Path path) {

        return roots
                .stream()
                .flatMap(root->findResource(root,path).stream())
                .findFirst();
    }

    private static Stream<Path> recurseChildren(Path p){
        if(Files.isRegularFile(p))
            return Stream.of(p);
        else if(Files.isDirectory(p)){
            try {
                return StreamSupport.stream(Files.newDirectoryStream(p).spliterator(),false)
                        .flatMap(PathsResourceSet::recurseChildren);
            } catch (IOException e) {
                return Stream.empty();
            }
        }else
            return Stream.empty();
    }

    @Override
    public Stream<Path> keys() {
        return roots.parallelStream()
                .flatMap(root->recurseChildren(root).map(p->p.relativize(root)));
    }

    @Override
    public void reload() { }
}
