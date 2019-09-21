package edu.unapec.shoppingorders.enums;

public enum ShoppingOrderCell {
    ID(0),
    CLIENT_ID(1),
    CLIENT_NAME(2),
    PRODUCT_ID(3),
    PRODUCT_NAME(4),
    ORDER_DATE(5),
    PRICE(6),
    QUANTITY(7),
    SUBTOTAL(8),
    GENERATE_ID(9);

    private int cellIndex;

    private ShoppingOrderCell(int index) {
        this.cellIndex = index;
   }

   public int getIntValue() {
        return this.cellIndex;
   }
}
