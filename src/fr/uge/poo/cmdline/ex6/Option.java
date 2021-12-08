package fr.uge.poo.cmdline.ex6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Option {
    private final String name;
    private final List<String> aliases;
    private final int nbParams;
    private final boolean isRequired;
    private final Consumer<List<String>> action;
    private final String notice;
    private final List<String> conflicts;


    private Option(OptionBuilder builder) {
        this.name = builder.name;
        this.aliases = List.copyOf(builder.aliases); // copy defensive
        this.nbParams = builder.nbParams;
        this.isRequired = builder.isRequired;
        this.action = builder.action;
        this.notice = builder.notice;
        this.conflicts = List.copyOf(builder.conflicts);
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String name() {
        return name;
    }

    public String notice() {
        return notice;
    }

    public List<String> conflicts() {
        return List.copyOf(conflicts);
    }

    public List<String> aliases() {
        return List.copyOf(aliases);
    }

    public int nbParams() {
        return nbParams;
    }

    public Consumer<List<String>> action() {
        return action;
    }

    public static class OptionBuilder {
        private final String name;
        private final List<String> aliases = new ArrayList<>(); // Default, no aliases
        private final int nbParams;
        private boolean isRequired = false;
        private final Consumer<List<String>> action;
        private String notice;
        private final List<String> conflicts = new ArrayList<>();

        public OptionBuilder(String name, int nbParams, Consumer<List<String>> action) {
            this.name = name;
            this.nbParams = nbParams;
            this.action = action;
        }

        public OptionBuilder required(){
            this.isRequired = true;
            return this;
        }

        public OptionBuilder setNotice(String notice){
            this.notice = notice;
            return this;
        }

        public OptionBuilder conflictWith(String... names){
            this.conflicts.addAll(Arrays.asList(names));
            return this;
        }

        public OptionBuilder alias(String... aliases){
            this.aliases.addAll(Arrays.asList(aliases));
            return this;
        }

        public Option build(){
            return new Option(this);
        }
    }
}