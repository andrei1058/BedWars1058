package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.configuration.language.Messages;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Internal implements Party {
    private static List<Party> parites = new ArrayList<>();

    @Override
    public boolean hasParty(Player p) {
        for (Party party : parites) {
            if (party.members.contains(p)) return true;
        }
        return false;
    }

    @Override
    public int partySize(Player p) {
        for (Party party : parites) {
            if (party.members.contains(p)) {
                return party.members.size();
            }
        }
        return 0;
    }

    @Override
    public boolean isOwner(Player p) {
        for (Party party : parites) {
            if (party.members.contains(p)) {
                if (party.owner == p) return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> getMembers(Player owner) {
        for (Party party : parites) {
            if (party.members.contains(owner)) {
                return party.members;
            }
        }
        return null;
    }

    @Override
    public void createParty(Player owner, Player... members) {
        Party p = new Party(owner);
        p.addMember(owner);
        for (Player mem : members) {
            p.addMember(mem);
        }
    }

    @Override
    public void addMember(Player owner, Player member) {
        getParty(owner).addMember(member);
    }

    @Override
    public void removeFromParty(Player member) {
        for (Party p : getParites()) {
            if (p.owner == member) {
                disband(member);
            } else if (p.members.contains(member)) {
                for (Player mem : p.members) {
                    mem.sendMessage(getMsg(mem, Messages.COMMAND_PARTY_LEAVE_SUCCESS).replace("{player}", member.getName()));
                }
                p.members.remove(member);
                if (p.members.isEmpty() || p.members.size() == 1) {
                    disband(p.owner);
                    getParites().remove(p);
                }
                return;
            }
        }
    }

    @Override
    public void disband(Player owner) {
        for (Player p : getParty(owner).members) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_DISBAND_SUCCESS));
        }
        getParty(owner).members.clear();
        getParites().remove(this);
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        for (Party p : parites) {
            if (p.owner == owner) {
                if (p.members.contains(check)) return true;
            }
        }
        return false;
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        Party p = getParty(owner);
        if (p != null) {
            if (p.members.contains(target)) {
                for (Player mem : p.members) {
                    mem.sendMessage(getMsg(mem, Messages.COMMAND_PARTY_REMOVE_SUCCESS).replace("{player}", target.getName()));
                }
                p.members.remove(p);
                if (p.members.isEmpty() || p.members.size() == 1) {
                    disband(p.owner);
                    getParites().remove(p);
                }
            }
        }
    }

    @Override
    public boolean isInternal() {
        return true;
    }

    private Party getParty(Player owner) {
        for (Party p : getParites()) {
            if (p.getOwner() == owner) return p;
        }
        return null;
    }

    public static List<Party> getParites() {
        return parites;
    }

    class Party {

        private List<Player> members = new ArrayList<>();
        private Player owner;

        public Party(Player p) {
            owner = p;
            Internal.parites.add(this);
        }

        public Player getOwner() {
            return owner;
        }

        void addMember(Player p) {
            members.add(p);
        }
    }
}
