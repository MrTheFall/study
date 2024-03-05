package common.network.responses;

import common.utils.Commands;

import java.util.Map;

public class HelpResponse extends Response {
    public final Map<String, String> commands;
    public HelpResponse(Map<String, String> commands, String error) {
        super(Commands.HELP, error);
        this.commands = commands;
    }
}