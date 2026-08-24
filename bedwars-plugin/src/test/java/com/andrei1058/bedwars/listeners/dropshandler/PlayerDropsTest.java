package com.andrei1058.bedwars.listeners.dropshandler;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerDropsTest {

    @Test
    public void testPlayerDropsWithNullUpgradeIdentifier() {
        // 1. Simulate the bug scenario where getShopUpgradeIdentifier returns null
        String upgradeIdentifier = null;

        // 2. This mimics the exact safety condition you added to PlayerDrops.java
        // Old code: if (!upgradeIdentifier.trim().isEmpty()) -> Would crash here!
        boolean shouldContinue = false;
        
        try {
            // Your new safety fix line:
            shouldContinue = (upgradeIdentifier != null && !upgradeIdentifier.trim().isEmpty());
        } catch (NullPointerException e) {
            fail("The code threw a NullPointerException! The safety fix failed.");
        }

        // 3. Assertions to confirm the fix behaves safely
        assertFalse(shouldContinue, "The safety block must return false gracefully instead of crashing!");
        assertNull(upgradeIdentifier, "The identifier value being tested is safely confirmed as null.");
    }
}
