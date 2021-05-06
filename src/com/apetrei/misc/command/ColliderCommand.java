package com.apetrei.misc.command;

import com.apetrei.engine.objects.components.Collider2D;

@FunctionalInterface
public interface ColliderCommand {
    void execute(Collider2D collider);
}
