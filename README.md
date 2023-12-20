## INSTALLATION

- Download **velocity-server-manager.jar** from [here](https://github.com/ThatsKai/velocity-server-manager/releases/tag/1.0) and put it in your velocity plugins folder.

## HOW TO USE

First of all, all servers specified in **velocity.toml** will not be touched by the plugin. All servers added by this plugin will not be added into **velocity.toml** and you dont have to add them in the file.

## Where i can find, and if necessary modify, servers added with this plugin?

You can find all servers in the **database.yml** situated into **/plugins/velocity-server-manager/**

## PREVIEW

### SERVER LIST

![image](https://github.com/ThatsKai/velocity-server-manager/assets/108898782/9a3ae681-ab98-4fed-a858-e5dd113b5796)


### HELP PAGE

![image](https://github.com/ThatsKai/velocity-server-manager/assets/108898782/f5572b6c-01ba-4cc9-adb2-0baf6a3f9591)


## COMMANDS

- **/sm reload** | Reload plugin configuration and all servers added with it.
- **/sm list** | View list of all servers added with this plugin and it port.
- **/sm add <server-name> <server-host> <server-host>** | Add a server, (Example: /sm add Lobby-2 localhost 25567).
- **/sm remove <server-name>** | Remove a server.
- **/sm info <server-name>** | View some informations of a server.

## PERMISSIONS

There is only one permission: **smanager.admin**

## HELP & SUGGESTIONS

You want leave a suggestion or just ask for help? Contact me! Telegram: **@thatskai**, Discord: **thatskai.**
