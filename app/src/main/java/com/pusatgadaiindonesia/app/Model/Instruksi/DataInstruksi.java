package com.pusatgadaiindonesia.app.Model.Instruksi;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataAdminDetail;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataItemsDetail;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataJasaDetail;

import java.util.List;

public class DataInstruksi {

    @SerializedName("id")
    private String id;

    @SerializedName("header")
    private String header;

    @SerializedName("instruction")
    private List<DataInstruksiDetail> instruction;


    public DataInstruksi(String id, String header, List<DataInstruksiDetail> instruction) {
        this.id = id;
        this.header = header;
        this.instruction = instruction;
    }

    public String getid() {
        return id;
    }

    public String getheader() {
        return header;
    }

    public List<DataInstruksiDetail> getinstruction() {
        return instruction;
    }


}
