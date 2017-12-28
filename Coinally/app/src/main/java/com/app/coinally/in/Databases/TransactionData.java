package com.app.coinally.in.Databases;

/**
 * Created by jmtec on 12/28/2017.
 */

public class TransactionData {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String coin_name;
    private String cryptoAddress;
    private String available_qty;
    private String pending_qty;
    private String reserved_qty;
    private String total_qty;

    public String getCryptoAddress() {
        return cryptoAddress;
    }

    public void setCryptoAddress(String cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }


    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public String getAvailable_qty() {
        return available_qty;
    }

    public void setAvailable_qty(String available_qty) {
        this.available_qty = available_qty;
    }

    public String getPending_qty() {
        return pending_qty;
    }

    public void setPending_qty(String pending_qty) {
        this.pending_qty = pending_qty;
    }

    public String getReserved_qty() {
        return reserved_qty;
    }

    public void setReserved_qty(String reserved_qty) {
        this.reserved_qty = reserved_qty;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }
}
