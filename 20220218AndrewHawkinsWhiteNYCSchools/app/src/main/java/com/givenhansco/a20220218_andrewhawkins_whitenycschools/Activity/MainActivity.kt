package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Activity

/**
* Andrew HW
* Android coding assessment
* I normally use java but I
* have been moving to kt
* 02/20/2022
 */

/**
 * this project could be much simpler but
 * I wanted to make a real world project
 */

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Fragment.School
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.App
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Models.school_meta
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.R
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Tasks.Tasks
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.UIHelper.ViewDialog

class MainActivity : AppCompatActivity() {

    //region vars
    var _settings: SharedPreferences? = null
    var viewDialog: ViewDialog? = null
    //endregion

    //region life cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewDialog = ViewDialog(this)
        var btnLoadSchool = findViewById<Button>(R.id.btnLoadSchools)

        //could run faster by saving list and let user refresh or refresh periodically
        btnLoadSchool.setOnClickListener(View.OnClickListener {
            grabSchools();
        })

    }

    fun Settings(): SharedPreferences? {
        if (_settings == null) {
            _settings =
                PreferenceManager.getDefaultSharedPreferences(App.GetAppContext())
        }
        return _settings
    }
    //endregion

    //just a loading gif kinda neato
    fun showCustomLoadingDialog() {
        //..show gif
        viewDialog?.showDialog()
        val handler = Handler()
        handler.postDelayed({
            try {
                viewDialog?.hideDialog()
            } catch (es: Exception) {
            }
        }, 5000)
    }

    //region Task Start
    //call url to get School list
    fun grabSchools(){
        showCustomLoadingDialog();
        val getSchools: Tasks.getSchools? = Tasks.getSchools(this)
        getSchools?.execute();
    }

    //call url to get meta data
    fun grabMeta(key:String)
    {
        showCustomLoadingDialog();
        val getMeta: Tasks.getSchoolsMeta? = Tasks.getSchoolsMeta(this,key)
        getMeta?.execute();
    }
    //endregion

    //region TaskComplete
    fun GetSchoolTaskComplete(s: String, school:  Array<school>) {
        //add school list to UI
        val fileList = findViewById<View>(R.id.schoolList) as LinearLayout
        val fragMan = fragmentManager
        var counter = 0

        for (i in school.indices)
        {
            val bundle = Bundle()

            //name
            bundle.putSerializable("param1", school[i].getschool_name())

            //prime key
            bundle.putSerializable("param2", school[i].getdbn())
            val lf = School()
            lf.setArguments(bundle)
            val fragTransaction = fragMan.beginTransaction()
            fragTransaction.add(fileList.id, lf, "fragment$counter")
            fragTransaction.commit()
            counter++
        }
        try {
            viewDialog?.dialog?.dismiss();
        }catch (ep: java.lang.Exception)
        {

        }
    }

    fun GetSchoolMetaTaskComplete(s: String, school_meta: school_meta?) {

//display meta data about school
        var log = "";

        try{
            log =   "Number of sat takers: " + school_meta?.getnum_of_sat_test_takers() +
                    "\nAverage math score: " + school_meta?.getsat_math_avg_score() +
                    "\nAverage writing score: " + school_meta?.getsat_writing_avg_score();
        }catch (esp:java.lang.Exception)
        {
            log = "no data found";
        }

        if(school_meta?.getnum_of_sat_test_takers() == null)
        {
            log = "no data found";
        }
        val dialog = Dialog(this, R.style.AlertDialogThemeEngineHours)
        dialog.setContentView(R.layout.dialog_log_viewer)
        dialog.setCancelable(true)

        val tv = dialog.findViewById<TextView>(R.id.txtLogMsg)
        tv.text = log

        dialog.findViewById<View>(R.id.btnAllInCancel).visibility = View.INVISIBLE
        dialog.findViewById<View>(R.id.btnAllInSubmit).visibility = View.INVISIBLE
        dialog.setOnDismissListener {
            try {
                viewDialog!!.dialog!!.dismiss()
            } catch (ep: java.lang.Exception) {
            }
        }

        dialog.show()
    }
    //endregion
}