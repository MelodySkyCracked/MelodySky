/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Managers.Skyblock.Dungeons;

import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonFloors;
import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonListener;
import xyz.Melody.System.Managers.Skyblock.Dungeons.MimicListener;

public class DungeonManager {
    public boolean isMMD = false;
    protected DungeonListener dungeonListener = new DungeonListener();
    protected MimicListener mimic = new MimicListener();

    public void init() {
        this.dungeonListener.init();
        this.mimic.init();
    }

    public void startThread() {
        this.dungeonListener.initDungeonThread();
    }

    public Thread getDungeonThread() {
        return this.dungeonListener.dungeonThread;
    }

    public boolean isIn(DungeonFloors dungeonFloors) {
        return this.getCurrentFloor() == dungeonFloors;
    }

    public DungeonFloors getCurrentFloor() {
        return this.dungeonListener.floor;
    }

    public String getCurrentFloorName() {
        return this.dungeonListener.floor.name();
    }

    public int getScore() {
        return this.dungeonListener.score;
    }

    public boolean isMimicFound() {
        return this.dungeonListener.foundMimic;
    }

    public int getSecretsFound() {
        return this.dungeonListener.secretsFound;
    }

    public int getTotalSecrets() {
        return this.dungeonListener.totalSecrets;
    }

    public int getCryptsFound() {
        return this.dungeonListener.cryptsFound;
    }

    public int getDeaths() {
        return this.dungeonListener.deaths;
    }

    public boolean inBoss() {
        return this.dungeonListener.inBoss;
    }
}

