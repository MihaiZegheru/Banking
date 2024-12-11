package org.poo.banking.user.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellerTrackingNode {
    private String commerciant;
    private double total;
}
