package com.prembros.oliveforecast.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Day implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@SerializedName("maxtemp_c") private double maxtemp_c;
	@SerializedName("maxtemp_f") private double maxtemp_f;
	@SerializedName("mintemp_c") private double mintemp_c;
	@SerializedName("mintemp_f") private double mintemp_f;
	@SerializedName("avgtemp_c") private double avgtemp_c;
	@SerializedName("avgtemp_f") private double avgtemp_f;
	@SerializedName("maxwind_mph") private double maxwind_mph;
	@SerializedName("maxwind_kph") private double maxwind_kph;
	@SerializedName("totalprecip_mm") private double totalprecip_mm;
	@SerializedName("totalprecip_in") private double totalprecip_in;
	@SerializedName("avghumidity") private double avghumidity;
	@SerializedName("condition") private Condition condition;

    public double getMaxTempC() {
        return maxtemp_c;
    }

    public void setMaxTempC(double mMaxtemp_c) {
        this.maxtemp_c = mMaxtemp_c;
    }

    public double getMaxTempF() {
        return maxtemp_f;
    }

    public void setMaxTempF(double mMaxtemp_f) {
        this.maxtemp_f = mMaxtemp_f;
    }

    public double getMinTempC() {
        return mintemp_c;
    }

    public void setMinTempC(double mMintemp_c) {
        this.mintemp_c = mMintemp_c;
    }

    public double getMinTempF() {
        return mintemp_f;
    }

    public void setMinTempF(double mMintemp_f) {
        this.mintemp_f = mMintemp_f;
    }

    public double getAvgTempC() {
        return avgtemp_c;
    }

    public void setAvgTempC(double mAvgtemp_c) {
        this.avgtemp_c = mAvgtemp_c;
    }

    public double getAvgTempF() {
        return avgtemp_f;
    }

    public void setAvgTempF(double mAvgtemp_f) {
        this.avgtemp_f = mAvgtemp_f;
    }

    public double getMaxWindMph() {
        return maxwind_mph;
    }

    public void setMaxWindMph(double mMaxWind_mph) {
        this.maxwind_mph = mMaxWind_mph;
    }

    public double getMaxWindKph() {
        return maxwind_kph;
    }

    public void setMaxWindKph(double mMaxWind_kph) {
        this.maxwind_kph = mMaxWind_kph;
    }

    public double getTotalPrecipMm() {
        return totalprecip_mm;
    }

    public void setTotalPrecipMm(double mTotalPrecip_mm) {
        this.totalprecip_mm = mTotalPrecip_mm;
    }

    public double getTotalPrecipIn() {
        return totalprecip_in;
    }

    public void setTotalprecipIn(double mTotalPrecip_in) {
        this.totalprecip_in = mTotalPrecip_in;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition mCondition) {
        this.condition = mCondition;
    }

    public double getAvgHumidity() {
        return avghumidity;
    }

    public void setAvgHumidity(double avgHumidity) {
        this.avghumidity = avgHumidity;
    }
}