package com.fashion.weddingdressrental;

import java.util.ArrayList;

public class Store {
    
    private ArrayList<GiftCard> listGift = new ArrayList<>();
    private String name;

    public Store(String name) {
        this.name = name;
    }


    public ArrayList<GiftCard> getListGift() {
        return listGift;
    }

    public void setListGift(ArrayList<GiftCard> listGift) {
        this.listGift = listGift;
    }
    
    
}