package nl.michielgraat.adventofcode2022.day07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory {
    private final String name;
    private Directory parent = null;
    private final List<Directory> subDirectories = new ArrayList<>();
    private final Map<String, Integer> files = new HashMap<>();

    public Directory(final String name, final Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public int getSize() {
        return files.values().stream().mapToInt(f -> f).sum()
                + subDirectories.stream().mapToInt(Directory::getSize).sum();
    }

    public Directory getRoot() {
        Directory current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    public Directory moveOut() {
        return this.getParent() == null ? this : this.getParent();
    }

    public Directory moveIn(final String name) {
        final Directory subDirectory = new Directory(name, this);
        if (!subDirectories.contains(subDirectory)) {
            subDirectories.add(subDirectory);
            updateParent();
        }
        return subDirectory;
    }

    public void addFile(final String name, final int size) {
        files.put(name, size);
        updateParent();
    }

    public void addDirectory(final String name) {
        final Directory subDirectory = new Directory(name, this);
        if (!this.subDirectories.contains(subDirectory)) {
            subDirectories.add(new Directory(name, this));
            updateParent();
        }
    }

    private void updateParent() {
        if (this.getParent() != null) {
            this.getParent().replaceDirectory(this);
        }
    }

    private void replaceDirectory(final Directory directory) {
        this.subDirectories.remove(directory);
        this.subDirectories.add(directory);
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(final Directory parent) {
        this.parent = parent;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }

    public Map<String, Integer> getFiles() {
        return files;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Directory other = (Directory) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }
}