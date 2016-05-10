package com.github.leonardpieper.ceciVPlanLV;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.widget.TimePicker;

import com.example.leonard.ceciVPlan.R;

import java.util.Calendar;

public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment theFragment;
    public SettingsFragment(){
        theFragment = this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //Login-Button
        final Preference loginButton = (Preference)findPreference("prefLogin");
        loginButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
//                SettingsActivity.theActivity.prefLogin();
//                Toast toast = Toast.makeText(getActivity(), "Anmeldung eingegangen\nBitte warten..", Toast.LENGTH_LONG);
//                toast.show();
                Intent logIntent = new Intent(getActivity(), LogginActivity.class);
                startActivity(logIntent);
                return true;
            }
        });
//        Button um den Dialog für Tage und Zeiten zu starten
        /*Preference daysButton = (Preference)findPreference("prefDays");
        daysButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new SetLessonsDayDialog();
                dialogFragment.show(getFragmentManager(), "SetLessonsDayDialog");
                return true;
            }
        });*/

//        Preference subButton = (Preference)findPreference("prefSubmit");
//        subButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                String id = sharedPref.getString("prefId", "");
//                sharedPref.edit().remove("prefId").apply();
//
//                ArrayList<String> detaills = new ArrayList<String>();
//                detaills.add("Wochentag");
//                detaills.add("Stunde 1");
//                detaills.add("Stunde 2");
//                detaills.add("Stunde 3");
//                detaills.add("Raum");
//
//                AddNewLesson.addTheLesson(null, null, id, null, detaills);
//
//                Toast toast = Toast.makeText(getActivity(), "Fach hinzugefügt", Toast.LENGTH_SHORT);
//                toast.show();
//                return true;
//            }
//        });

        Preference addButton = (Preference)findPreference("prefAddFach");
        addButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent prefDayIntent = new Intent(getActivity(), PrefDayActivity.class);
                startActivity(prefDayIntent);
                return true;
            }
        });

        Preference editButton = (Preference)findPreference("prefEditFach");
        editButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent prefEditIntent = new Intent(getActivity(), ChangeTTActivity.class);
                startActivity(prefEditIntent);
                return true;
            }
        });

        Preference delButton = (Preference)findPreference("prefDel");
        delButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MainActivity.theActivity.deleteStorageFile("kurskrzl");
                MainActivity.theActivity.deleteStorageFile("facher.json");
                Snackbar snackbar = Snackbar.make(getView(), "Alle Fächer gelöscht", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return true;
            }
        });

        Preference timeButton = (Preference)findPreference("prefTime");
        timeButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDateDialog();
                return true;
            }
        });
    }


    protected String getValueFromList(String listName){
        ListPreference listPreference = (ListPreference) findPreference(listName);
        String value = listPreference.getValue();
        return value;
    }

    private void showDateDialog(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar changedCal = Calendar.getInstance();
                changedCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                changedCal.set(Calendar.MINUTE, minute);

                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt("prefNotifyHourOfDay", hourOfDay);
                editor.putInt("prefNotifyMinute", minute);
                editor.commit();

                MainActivity.theActivity.setAlarmSchedule();

            }
        }, sharedPref.getInt("prefNotifyHourOfDay", 7), sharedPref.getInt("prefNotifyMinute", 00), true).show();
    }
}
