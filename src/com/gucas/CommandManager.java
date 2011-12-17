package com.gucas;

//Factory Pattern to produce commands from String[]
public class CommandManager {
	public CommandManager() {
		
	}

	public static BaseCommand BuildCommand(String command_name, String args){
		if(command_name.equals(FetchContactCommand.mCommandName)){
			return new FetchContactCommand(args);
		}
		//TODO: more commands to be added
		//Maybe throw a exception if the corresponding Command is undefined?
		return null;
	}
	public static BaseCommand[] BuildCommands(String[] command_names, String[] args){
		BaseCommand[] commands = new BaseCommand[command_names.length];
		//TODO:how to initialize?
		for (int i = 0; i < command_names.length; i++) {
			commands[i] = BuildCommand(command_names[i], args[i]);
		}
		return commands;
	}
}
