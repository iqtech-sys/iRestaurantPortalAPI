package com.irestaurant.iPortalAPI.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class AccountUtil {
    
    public static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
    
    public static double calculateTax(double subtotal, double taxRate) {
        if (subtotal <= 0.0 || taxRate < 0.0) {
            return 0.0;
        }

        // taxRate is a decimal (e.g. 0.05 = 5%) — multiply directly, no /100 needed.
        // Mirrors Flutter: return subtotal * Constants.taxRate;
        double tax = subtotal * taxRate;

        return round(tax);
    }

    public static double calculateTotal(double subtotal, double tax) {
        return round(subtotal + tax);
    }
    
    public static boolean isAddedInvoice(String invNum) {
      return invNum.contains(Constants.addInvoiceSymbol) && !invNum.contains(Constants.removeInvoiceSymbol);
    }

    public static boolean isRemovedInvoice(String invNum) {
      return invNum.contains(Constants.removeInvoiceSymbol);
    }

    public static boolean isCancelledInvoice(String invNum) {
      return invNum.contains(Constants.cancelInvoiceSymbol);
    }
}
