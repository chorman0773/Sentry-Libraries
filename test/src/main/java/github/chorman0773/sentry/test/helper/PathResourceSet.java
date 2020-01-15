package github.chorman0773.sentry.test.helper;

import github.lightningcreations.lcjei.resources.Resource;
import github.lightningcreations.lcjei.resources.ResourceSet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathResourceSet implements ResourceSet<Path> {
    private final Path root;

    public PathResourceSet(Path root) {
        this.root = root;
    }

    private class PathResource implements Resource<Path>{
        private Path path;
        PathResource(Path path){
            this.path = path;
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
            return path.relativize(root);
        }
    }
    @Override
    public Optional<Resource<Path>> getResource(Path path) {
        path = root.resolve(path);
        if(Files.exists(path))
            return Optional.of(new PathResource(path));
        else
            return Optional.empty();
    }

    private Stream<Path> entriesUnder(Path p){
        try {
            if(Files.isDirectory(p))
                return StreamSupport.stream(Files.newDirectoryStream(p).spliterator(),false).flatMap(this::entriesUnder);
            else
                return Stream.of(p);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    @Override
    public Stream<Path> keys() {
        return entriesUnder(root);
    }

    @Override
    public void reload() {}
}
