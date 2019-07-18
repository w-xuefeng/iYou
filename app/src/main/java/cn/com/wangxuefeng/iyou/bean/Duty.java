package cn.com.wangxuefeng.iyou.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Duty {
    private boolean state;
    private List<Integer> dutyWeek = new ArrayList<>();
    private List<Integer> dutyClass = new ArrayList<>();
    private List<String> dutyDate = new ArrayList<>();
    public Duty(){}
    public Duty(String duty){
        if(duty.equals("null") || duty.equals("")){
            this.state = false;
            return;
        }
        try {
            JSONObject dutyJson = new JSONObject(duty);
            int dutyState = dutyJson.getInt("state");
            this.state = dutyState == 1;
            JSONArray week = dutyJson.getJSONArray("week");
            for (int i = 0; i < week.length(); i++) {
                this.dutyWeek.add(week.getInt(i));
            }
            JSONArray classs = dutyJson.getJSONArray("class");
            for (int i = 0; i < classs.length(); i++) {
                this.dutyClass.add(classs.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.state = false;
        }
    }

    public String toString() {
        String res = "{\"week\": [";
        for (int i = 0; i < this.dutyWeek.size(); i++) {
            if (this.dutyWeek.size() - 1 == i) {
                res += this.dutyWeek.get(i);
            } else {
                res += this.dutyWeek.get(i) + ", ";
            }
        }
        res += "], \"class\": [";
        for (int i = 0; i < this.dutyClass.size(); i++) {
            if (this.dutyClass.size() - 1 == i) {
                res += this.dutyClass.get(i);
            } else {
                res += this.dutyClass.get(i) + ", ";
            }
        }
        res += "], \"state\": 0, \"dutydate\": [";
        for (int i = 0; i < this.dutyDate.size(); i++) {
            if (this.dutyDate.size() - 1 == i) {
                res += this.dutyDate.get(i);
            } else {
                res += this.dutyDate.get(i) + ", ";
            }
        }
        res += "]}";
        return res;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Integer> getDutyWeek() {
        return dutyWeek;
    }

    public void setDutyWeek(List<Integer> dutyWeek) {
        this.dutyWeek = dutyWeek;
    }

    public List<Integer> getDutyClass() {
        return dutyClass;
    }

    public void setDutyClass(List<Integer> dutyClass) {
        this.dutyClass = dutyClass;
    }

    public List<String> getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(List<String> dutyDate) {
        this.dutyDate = dutyDate;
    }
}
