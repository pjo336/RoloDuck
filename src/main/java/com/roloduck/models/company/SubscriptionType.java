package com.roloduck.models.company;

import com.roloduck.exception.NotFoundException;

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

    public static SubscriptionType getTypeByValue(int value) throws NotFoundException {
        for(SubscriptionType type: SubscriptionType.values()) {
            if(type.value() == value) {
                return type;
            }
        }
        throw new NotFoundException("The value: " + value + " does not correspond to a Subscription Type.");
    }
}
