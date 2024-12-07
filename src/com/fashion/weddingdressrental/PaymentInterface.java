package com.fashion.weddingdressrental;

public interface PaymentInterface {
    public boolean checkoutDebitCard(int CardId);
    public boolean checkoutStoreCredits();
    public boolean checkoutCash();

}
