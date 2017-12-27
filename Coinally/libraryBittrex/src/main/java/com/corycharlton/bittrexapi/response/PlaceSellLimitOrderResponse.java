package com.corycharlton.bittrexapi.response;

import com.corycharlton.bittrexapi.model.Uuid;

@SuppressWarnings("EmptyMethod")
public class PlaceSellLimitOrderResponse extends Response<Uuid> {
    private PlaceSellLimitOrderResponse() {} // Cannot be instantiated

    @Override
    public Uuid result() {
        return super.result();
    }
}
