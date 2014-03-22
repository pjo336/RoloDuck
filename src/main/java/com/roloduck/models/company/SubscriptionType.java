package com.roloduck.models.company;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public enum SubscriptionType {

    FREE_SUBSCRIPTION(0),
    PRO_SUBSCRIPTION(1),
    PREMIUM_SUBSCRIPTION(2);

    private int subscriptionTypeValue;

    private SubscriptionType(int type) {
        this.subscriptionTypeValue = type;
    }

    public int value() {
        return this.subscriptionTypeValue;
    }

    public static SubscriptionType getTypeByValue(int value) {
        for(SubscriptionType type: SubscriptionType.values()) {
            if(type.value() == value) {
                return type;
            }
        }
        // TODO return null?
        return null;
    }
}
