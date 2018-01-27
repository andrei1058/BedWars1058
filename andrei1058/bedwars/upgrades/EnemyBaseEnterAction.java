package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.arena.BedWarsTeam;

import static com.andrei1058.bedwars.Main.plugin;

public class EnemyBaseEnterAction extends UpgradeAction {

    private String name;
    boolean chat = false, action = false, title = false, subtitle = false;

    public EnemyBaseEnterAction(String name){
        this.name = name;
        plugin.debug("loading new EnemyBaseEnterAction: "+getName());
    }

    @Override
    public void execute(BedWarsTeam bwt) {

    }

    public String getName() {
        return name;
    }

    public boolean isChat() {
        return chat;
    }

    public boolean isAction() {
        return action;
    }

    public boolean isSubtitle() {
        return subtitle;
    }

    public boolean isTitle() {
        return title;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setSubtitle(boolean subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isSomethingTrue(){
        return isChat() || isAction() || isSubtitle() || isTitle();
    }
}
