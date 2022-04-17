package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.IDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        Map<Product, Integer> countedProducts = basket.getProducts().stream()
                .collect(Collectors.toMap(p -> p,p -> 1,Integer::sum));

        List<ReceiptEntry> receiptEntries = countedProducts.entrySet().stream()
                .map(p -> new ReceiptEntry(p.getKey(),p.getValue()))
                .collect(Collectors.toList());

        return applyDiscounts(new Receipt(receiptEntries));
    }

    private Receipt applyDiscounts(Receipt receipt){
        IDiscount discount15 = new FifteenPercentDiscount();
        IDiscount discount10 = new TenPercentDiscount();

        receipt = discount15.apply(receipt);
        receipt = discount10.apply(receipt);

        return receipt;
    }
}
