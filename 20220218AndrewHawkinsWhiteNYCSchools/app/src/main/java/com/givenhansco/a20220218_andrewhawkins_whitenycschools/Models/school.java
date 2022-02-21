package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * model for school
 * easy to update
 * easy to support multiple
 * different api versions
 */

public class school {

    //name of the school
    @SerializedName("school_name")
    @Expose
    String school_name;

    public String getschool_name() {
        return school_name;
    }

    public void setschool_name(String school_name) {
        this.school_name = school_name;
    }

    //I think this is being used as a prime key
    @SerializedName("dbn")
    @Expose
    String  dbn;

    public String getdbn() {
        return dbn;
    }

    public void setdbn(String dbn) {
        this.dbn = dbn;
    }
}
