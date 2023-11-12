/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package xyz.Melody.System.Managers.Skyblock.Auctions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import xyz.Melody.Client;
import xyz.Melody.module.modules.others.LbinData;

public final class AhBzManager {
    public static volatile Map auctions = new HashMap();
    private static Map semi_auctions = new HashMap();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static int totalAuctions = 0;
    public static Thread auctionTimerThread;

    public static void registerTimer() {
        auctionTimerThread = new Thread(AhBzManager::lambda$registerTimer$0);
        auctionTimerThread.setDaemon(true);
        auctionTimerThread.setName("Melody -> Auction Thread");
        auctionTimerThread.start();
    }

    public static void loadAuctions() {
        Client.instance.logger.info("[MelodySky] [A/B] Fetching Auctions.");
        long l2 = System.currentTimeMillis();
        try {
            int n = 0;
            AhBzManager.loadBazaar();
            while (n++ != 0) {
                Thread.sleep(100L);
            }
            Client.instance.logger.info("[MelodySky] [A/B] Fetched " + n + " Pages of Auctions in " + (System.currentTimeMillis() - l2) + "ms.");
            auctions = semi_auctions;
            semi_auctions = new HashMap();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void loadBazaar() throws IOException {
        URL uRL = new URL("https://api.hypixel.net/skyblock/bazaar");
        InputStreamReader inputStreamReader = new InputStreamReader(uRL.openStream());
        JsonObject jsonObject = (JsonObject)new JsonParser().parse((Reader)inputStreamReader);
        boolean bl = jsonObject.get("success").getAsBoolean();
        if (!bl) {
            return;
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("products");
        for (Map.Entry entry : jsonObject2.entrySet()) {
            boolean bl2;
            String string = (String)entry.getKey();
            AuctionData auctionData = (AuctionData)semi_auctions.get(string);
            boolean bl3 = bl2 = auctionData == null;
            if (bl2) {
                auctionData = new AuctionData(string);
            }
            AuctionData.access$002(auctionData, ((JsonElement)entry.getValue()).getAsJsonObject().getAsJsonObject("quick_status").get("sellPrice").getAsInt());
            AuctionData.access$102(auctionData, ((JsonElement)entry.getValue()).getAsJsonObject().getAsJsonObject("quick_status").get("buyPrice").getAsInt());
            if (!bl2) continue;
            semi_auctions.put(string, auctionData);
        }
    }

    private static void lambda$registerTimer$0() {
        LbinData lbinData = LbinData.getInstance();
        int n = ((Double)lbinData.delay.getValue()).intValue() * 1000 * 60;
        AhBzManager.loadAuctions();
        while (true) {
            try {
                while (true) {
                    if (!lbinData.isEnabled()) {
                        Thread.sleep(5000L);
                        continue;
                    }
                    Thread.sleep(n);
                    AhBzManager.loadAuctions();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
            break;
        }
    }

    public static class AuctionData {
        private String id;
        private SortedSet prices;
        private long sellPrice = -1L;
        private long buyPrice = -1L;

        public AuctionData(String string) {
            this.id = string;
            this.prices = new TreeSet();
        }

        public long getBuyPrice() {
            return this.buyPrice;
        }

        public long getSellPrice() {
            return this.sellPrice;
        }

        public String getId() {
            return this.id;
        }

        public SortedSet getPrices() {
            return this.prices;
        }

        static long access$002(AuctionData auctionData, long l2) {
            auctionData.sellPrice = l2;
            return auctionData.sellPrice;
        }

        static long access$102(AuctionData auctionData, long l2) {
            auctionData.buyPrice = l2;
            return auctionData.buyPrice;
        }

        static SortedSet access$200(AuctionData auctionData) {
            return auctionData.prices;
        }
    }
}

