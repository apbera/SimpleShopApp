package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllDiscountsTest {

    @Test
    void shouldApplyAllDiscounts(){
        var productDb = new ProductDb();

        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var discount15 = new FifteenPercentDiscount();
        var discount10 = new TenPercentDiscount();
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2))).add(steak.price()).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscounts = discount15.apply(receipt);
        receiptAfterDiscounts = discount10.apply(receiptAfterDiscounts);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(2, receiptAfterDiscounts.discounts().size());
    }
}
