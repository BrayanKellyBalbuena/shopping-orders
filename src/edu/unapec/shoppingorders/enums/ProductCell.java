package edu.unapec.shoppingorders.enums;

public enum ProductCell {
    ID(0),
    NAME(1),
    PRICE(2),
    CREATED_DATE(3),
    GENERATED_ID(4);

    private final int cellIndex;

    private ProductCell(int index) {
        this.cellIndex = index;
    }

    public int getIntValue() {
        return this.cellIndex;
    }
}
