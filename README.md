# VL-WhiteList

A player whitelist plugin ignoring UUID for Minecraft (JE) servers

## Usage

Just put the ```WhiteList-[version].jar``` into the ```plugins``` folder.

If you have a configuration previously, you can put it in ```plugins/WhiteList``` as ```config.yml```.

The list would display with cases, but the whitelist is effective ignoring the case. To illustrate, a player ```abcd123``` can enter if there is ```aBcD123``` on the list.

## Commands

*(: all commands require **OP** permission)*

- ```/wl enable```: Enable VL-WhiteList
- ```/wl disable```: Disable VL-WhiteList
- ```/wl reload```: Load latest configuration from ```config.yml```
- ```/wl add [playername]```: Add a player into the whitelist
- ```/wl remove [playername]```: Remove a player from the whitelist
- ```/wl list```: List all players in the whitelist
