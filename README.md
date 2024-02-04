# MacroManager

**MacroManager** is a versatile Discord bot designed to allow remote control of a PC through Discord chat on your mobile device or another PC. To set up the bot, follow these simple steps:

## Discord Bot Setup

1. Create a Discord bot on the [Discord Developers Portal](https://discord.com/developers/applications).
2. Give the bot administrator rights during setup.
3. Add the bot to a server temporarily, send a private message to the bot, and then remove it from the server (or leave it if it's your private server). Do not share access to this bot with others.

## Project Setup

1. Install IntelliJ and open the MacroManager project.
2. Edit the `Connections` class with your Discord bot key and client ID.
3. Run `startShadowScripts` to generate an executable Jar file.
   ![image](https://github.com/Lucvds99/MyMacroManager/assets/63397031/c99ee616-6b33-48b3-aafd-302f6cb7e325)
4. Find the generated Jar file in `build/libs`.

## AutoHotkey Setup

1. Install AutoHotkey, a program used for generating media key events.
2. Unzip the folder in `C:\Program Files` or choose a different location (update the map location in the `Connections` class accordingly).

## Usage

- This application runs without a UI. Either run and close it via Task Manager or run it in console mode.
 `java -jar MacroController-1.4.0.jar`
- Optionally, place the Jar file in `shell:startup` to start the bot automatically with every PC startup.
- Type R or restart to visualize the menu and pick a category to control ur PC or Laptop

Now, you can control your PC remotely using Discord! This setup enables features like pausing and playing media on your device using AutoHotkey commands. You will also be able to remotely change to Multiple Desktops and access shortcuts for OBS Camera. These features will be user-specific however and won't impact your OBS if you did not download a Shortcut Tool. 