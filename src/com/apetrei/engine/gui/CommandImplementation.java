package com.apetrei.engine.gui;

import com.apetrei.misc.command.Command;

public class CommandImplementation implements Command {

    @Override
    public void execute() {
        System.out.println("Test sucsesfull.");
    }
}
