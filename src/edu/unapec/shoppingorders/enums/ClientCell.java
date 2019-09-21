package edu.unapec.shoppingorders.enums;

public enum ClientCell {
    ID(0),
    NAME(1),
    LAST_NAME(2),
    IDENTIFICATION_CARD(3),
    GENERATED_ID(4);

    private final int cell;

    private ClientCell(int cell) {
        this.cell = cell;
    }

    public int getIntValue() {
        return this.cell;
    }
}
