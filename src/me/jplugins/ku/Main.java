/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jplugins.ku;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    public static FileConfiguration conf;
    public static File cf;
    
    public void onEnable(){
        
        conf = getConfig();
        conf.options().copyDefaults(true);
        saveDefaultConfig();
        
        cf = new File(getDataFolder(), "config.yml");
        
        Bukkit.getLogger().info(getDescription().getName()+ getDescription().getVersion());
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new events(),this);
        manager.registerEvents((Listener) new Commands(),this);
        regCmd();
        
    }
    
    public void onDisable(){
        
        conf = YamlConfiguration.loadConfiguration(cf);
        Bukkit.getServer().getPluginManager().disablePlugins();
        
    }
    
    public void regCmd(){
        getCommand("jp").setExecutor(new Commands());
        getCommand("jpreload").setExecutor(new Commands());
    }
    
    public class events implements Listener{
        
       @EventHandler(priority=EventPriority.HIGHEST)
        public void onEntityDamageEvent(EntityDamageEvent e){
            status s = new status();
            if(s.nfd == "true"){
        if (!(e.getEntity() instanceof Player)) {
            return;
    }
        
        Player p = (Player)e.getEntity();
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
        
  }else if(s.nfd == "false"){}
            
        }
        
        public void BastBlock(BlockBreakEvent e){
            status s = new status();
            if(s.bbe == "true"){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.DARK_RED + "You can not beat the block.");
            }else if(s.bbe == "false"){}
        }
        
        public void PlaceBlock(BlockPlaceEvent e){
            status s = new status();
            if(s.bpe == "true"){}else if(s.bpe == "false"){
               e.setCancelled(true);
               e.getPlayer().sendMessage(ChatColor.DARK_RED + "You can not place the block.");
            }
      
    }
        
    }
    
    public static class status{
        
        public String nfd = conf.getString("NoFallDamage");
        public String bbe = conf.getString("BastBlockBreak");
        public String bpe = conf.getString("BlockPlace");
        
    }
    
    public class Commands implements Listener,CommandExecutor{
        
        @Override
        public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
            status s = new status();
            if(Sender instanceof Player){
                if(Sender.isOp()){
            if(cmd.getName().equalsIgnoreCase("jp")){
                Sender.sendMessage("JPlugins [Status]:" + "\n"
                + ChatColor.YELLOW + "NoFallDamage" + ChatColor.DARK_BLUE + s.nfd + "\n" + ChatColor.RESET
                        + ChatColor.DARK_PURPLE + "BastBlockBreak" + ChatColor.DARK_BLUE + s.bbe + "\n" + ChatColor.RESET
                        + ChatColor.DARK_AQUA + "BlockPlace" + ChatColor.DARK_BLUE + s.bpe + "\n" + ChatColor.RESET
                        + ChatColor.DARK_GRAY + "reload configuration: " + ChatColor.GREEN + "\n/jpreload\n"
                );
            }
            if(cmd.getName().equalsIgnoreCase("jpreload")){
                conf = YamlConfiguration.loadConfiguration(cf);
                Sender.sendMessage(ChatColor.GREEN + "reload succss");
            }
                }else{
                    Sender.sendMessage(ChatColor.DARK_RED + "You don't has admin.");
                }
            }else{
                Sender.sendMessage(ChatColor.DARK_RED + "As the only players.");
            }
            return true;
        }
        
    }
    
}