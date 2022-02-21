package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rnallapareddy on 6/26/2017.
 */

public class school_meta {

    /*
    "sat_critical_reading_avg_score": "411",
        "sat_math_avg_score": "369",
        "sat_writing_avg_score": "373"
     */
    @SerializedName("num_of_sat_test_takers")
    @Expose
    String num_of_sat_test_takers;

    public String getnum_of_sat_test_takers() {
        return num_of_sat_test_takers;
    }

    public void setnum_of_sat_test_takers(String num_of_sat_test_takers) {
        this.num_of_sat_test_takers = num_of_sat_test_takers;
    }

    @SerializedName("sat_math_avg_score")
    @Expose
    String sat_math_avg_score;

    public String getsat_math_avg_score() {
        return sat_math_avg_score;
    }

    public void setsat_math_avg_score(String sat_math_avg_score) {
        this.sat_math_avg_score = sat_math_avg_score;
    }

    @SerializedName("sat_writing_avg_score")
    @Expose
    String sat_writing_avg_score;

    public String getsat_writing_avg_score() {
        return sat_writing_avg_score;
    }

    public void setsat_writing_avg_score(String sat_writing_avg_score) {
        this.sat_writing_avg_score = sat_writing_avg_score;
    }
}
