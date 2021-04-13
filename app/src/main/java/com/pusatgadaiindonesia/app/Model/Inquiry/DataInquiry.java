package com.pusatgadaiindonesia.app.Model.Inquiry;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiLunasLelang;

import java.util.List;

public class DataInquiry {

    @SerializedName("items")
    private List<DataItemsDetail> items;

    @SerializedName("total")
    private String total;

    @SerializedName("jatuhTempoSelanjutnya")
    private String jatuhTempoSelanjutnya;

    @SerializedName("jasaBulanBerikutnya")
    private DataJasaDetail jasaBulanBerikutnya;

    @SerializedName("adminFeeBulanBerikutnya")
    private DataAdminDetail adminFeeBulanBerikutnya;

    @SerializedName("jatuhTempo2BulanBerikutnya")
    private String jatuhTempo2BulanBerikutnya;

    @SerializedName("totalBulanBerikutnya")
    private String totalBulanBerikutnya;

    @SerializedName("harus2BulanUntukAngsur")
    private String harus2BulanUntukAngsur;

    public DataInquiry(List<DataItemsDetail> items, String total, String jatuhTempoSelanjutnya,
                       DataJasaDetail jasaBulanBerikutnya, DataAdminDetail adminFeeBulanBerikutnya,
                       String jatuhTempo2BulanBerikutnya, String totalBulanBerikutnya, String harus2BulanUntukAngsur) {
        this.items = items;
        this.total = total;
        this.jatuhTempoSelanjutnya = jatuhTempoSelanjutnya;
        this.jasaBulanBerikutnya = jasaBulanBerikutnya;
        this.adminFeeBulanBerikutnya = adminFeeBulanBerikutnya;
        this.jatuhTempo2BulanBerikutnya = jatuhTempo2BulanBerikutnya;
        this.totalBulanBerikutnya = totalBulanBerikutnya;
        this.harus2BulanUntukAngsur = harus2BulanUntukAngsur;
    }

    public List<DataItemsDetail> getitems() {
        return items;
    }

    public String gettotal() {
        return total;
    }

    public String getjatuhTempoSelanjutnya() {
        return jatuhTempoSelanjutnya;
    }

    public DataJasaDetail getjasaBulanBerikutnya() {
        return jasaBulanBerikutnya;
    }

    public DataAdminDetail getadminFeeBulanBerikutnya() {
        return adminFeeBulanBerikutnya;
    }

    public String getjatuhTempo2BulanBerikutnya() {
        return jatuhTempo2BulanBerikutnya;
    }

    public String gettotalBulanBerikutnya() {
        return totalBulanBerikutnya;
    }

    public String getharus2BulanUntukAngsur() {
        return harus2BulanUntukAngsur;
    }
}
