package com.andrei1058.bedwars.api.arena.constraints;

import java.util.List;

@SuppressWarnings("unused")
public class ArenaConstraintViolation {

    private final ArenaConstraint constraint;
    private final String template;
    private final List<String> messages;

    public ArenaConstraintViolation(String template, ArenaConstraint constraint, List<String> messages) {
        this.constraint = constraint;
        this.template = template;
        this.messages = messages;
    }

    public ArenaConstraint getConstraint() {
        return constraint;
    }

    public String getTemplate() {
        return template;
    }

    public List<String> getMessages() {
        return messages;
    }
}
