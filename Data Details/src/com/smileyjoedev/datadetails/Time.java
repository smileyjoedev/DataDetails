package com.smileyjoedev.datadetails;

import com.smileyjoedev.genLibrary.Debug;

public class Time {
	
	private int seconds;
	private int startUt;
	private int endUt;
	private int secInMin;
	private int secInHour;
	private int secInDay;
	private int secInWeek;
	private int secInMonth;
	private int secInYear;
	private double hoursInDay;
	private int daysInWeek;
	
	public Time(){
		this.setDaysInWeek(7);
		this.setHoursInDay(24);
		this.setDefaultSeconds();
	}
	
	/*********************************************
	 * SETTERS
	 ********************************************/
	
	private void setDefaultSeconds(){
		this.setSecInMin(60);
		this.setSecInHour(60*this.getSecInMin());
		this.setSecInDay((int) Math.round(this.getHoursInDay()*this.getSecInHour()));
		this.setSecInWeek((int) Math.round(this.getDaysInWeek()*this.getSecInDay()));
		this.setSecInMonth((int) Math.round((365.25/12)*this.getSecInDay()));
		this.setSecInYear((int) Math.round(365.25*this.getSecInDay()));
	}
	
	public void setSeconds(double sec){
		this.seconds = (int) Math.round(sec);
	}
	
	public void setSeconds(int sec){
		this.seconds = sec;
	}
	
	public void setSeconds(){
		this.seconds = this.getEndUt() - this.getStartUt();
	}
	
	public void setStartUt(int ut){
		this.startUt = ut;
	}
	
	public void setEndUt(int ut){
		this.endUt = ut;
	}
	
	public void setSecInMin(int secInMin) {
		this.secInMin = secInMin;
	}

	public void setSecInHour(int secInHour) {
		this.secInHour = secInHour;
	}

	public void setSecInDay(int secInDay) {
		this.secInDay = secInDay;
	}

	public void setSecInWeek(int secInWeek) {
		this.secInWeek = secInWeek;
	}

	public void setSecInMonth(int secInMonth) {
		this.secInMonth = secInMonth;
	}

	public void setSecInYear(int secInYear) {
		this.secInYear = secInYear;
	}
	
	public void setHoursInDay(int hours){
		this.hoursInDay = hours;
		this.setDefaultSeconds();
	}
	
	public void setHoursInDay(double hours){
		this.hoursInDay = hours;
		this.setDefaultSeconds();
	}
	
	public void setDaysInWeek(int days){
		this.daysInWeek = days;
		this.setDefaultSeconds();
	}
	
	/**************************************************
	 * GETTERS
	 *************************************************/
	
	public int getSeconds(){
		return this.seconds;
	}
	
	public int getStartUt(){
		return this.startUt;
	}
	
	public int getEndUt(){
		return this.endUt;
	}
	
	public int getSecInMin() {
		return secInMin;
	}

	public int getSecInHour() {
		return secInHour;
	}

	public int getSecInDay() {
		return secInDay;
	}

	public int getSecInWeek() {
		return secInWeek;
	}

	public int getSecInMonth() {
		return secInMonth;
	}

	public int getSecInYear() {
		return secInYear;
	}
	
	public double getHoursInDay() {
		return hoursInDay;
	}

	public int getDaysInWeek() {
		return daysInWeek;
	}
	
	/****************************************************
	 * GENERAL
	 ***************************************************/
	
	public String getDiff(int startUt, int endUt){
		this.setStartUt(startUt);
		this.setEndUt(endUt);
		this.setSeconds();
		return this.convert();
	}
	
	public String convert(){
		String time = "";
		double seconds = this.getSeconds();
		double num;
		
		// Years
		if((num = seconds/this.getSecInYear()) >= 1){
			time += Integer.toString((int)num) + "y ";
			seconds = seconds%this.getSecInYear();
		}
		
		// Months
		if((num = seconds/this.getSecInMonth()) >= 1){
			time += Integer.toString((int)num) + "M ";
			seconds = seconds%this.getSecInMonth();
		}
		
		// Weeks 
		if((num = seconds/this.getSecInWeek()) >= 1){
			time += Integer.toString((int)num) + "w ";
			seconds = seconds%this.getSecInWeek();
		}
		
		// Days
		if((num = seconds/this.getSecInDay()) >= 1){
			time += Integer.toString((int)num) + "d ";
			seconds = seconds%this.getSecInDay();
		}
		
		// Hours
		if((num = seconds/this.getSecInHour()) >= 1){
			time += Integer.toString((int)num) + "h ";
			seconds = seconds%this.getSecInHour();
		}
		
		// Minutes
		if((num = seconds/this.getSecInMin()) >= 1){
			time += Integer.toString((int)num) + "m ";
			seconds = seconds%this.getSecInMin();
		}
		
		// Seconds
		if(seconds > 0){
			time += Integer.toString((int)seconds) + "s";
		}
		
		if(time.equals("")){
			time = "1s";
		}
		
		
		return time.trim();
	}

	@Override
	public String toString() {
		return "Time [getSeconds()=" + getSeconds() + ", getStartUt()=" + getStartUt() + ", getEndUt()=" + getEndUt() + ", getSecInMin()=" + getSecInMin() + ", getSecInHour()=" + getSecInHour() + ", getSecInDay()=" + getSecInDay() + ", getSecInWeek()=" + getSecInWeek() + ", getSecInMonth()=" + getSecInMonth() + ", getSecInYear()=" + getSecInYear() + ", getHoursInDay()=" + getHoursInDay() + ", getDaysInWeek()=" + getDaysInWeek() + "]";
	}

}
