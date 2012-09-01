package com.smileyjoedev.datadetails;

import java.text.DecimalFormat;

import com.smileyjoedev.genLibrary.Debug;

public class File {
	
	public static final int BIT = 0;
	public static final int BYTE = 1;
	public static final int KILOBYTE = 2;
	public static final int MEGABYTE = 3;
	public static final int GIGABYTE = 4;
	public static final int TERRABYTE = 5;
	public static final int PETABYTE = 6;
	public static final int EXABYTE = 7;
	public static final int ZETTABYTE = 8;
	public static final int YOTTABYTE = 9;
	
	
	private long bit;
	private double size;
	private int type;
	
	public void setSize(double size, int type){
		this.setType(type);
		this.setSize(size);
		this.setBit();
	}
	
	public void setBit(long bit){
		this.bit = bit;
		this.setSize();
	}
	
	public void setSize(){
		this.size = this.convertSize();
	}
	
	private void setBit(){
		this.bit = this.convertBit();
	}
	
	private void setSize(double size){
		this.size = size;
	}
	
	private void setType(int type){
		this.type = type;
	}
	
	public long getBit(){
		return this.bit;
	}
	
	public int getType(){
		return this.type;
	}
	
	public double getSize(){
		return this.size;
	}
	
	private long convertBit(){
		return this.convertBit(this.getSize(), this.getType());
	}
	
	private double convertSize(double bit, int type){
		Debug.d("Bit", bit);
		double size = 0;
		
		if(bit/this.getTypeMultiplier(type) <= 1){
			size = bit;
			this.setType(type);
		} else {
			size = this.convertSize(bit/this.getTypeMultiplier(type), type+1);
		}
		
		return size;
	}
	
	public String getTypeText(){
		String name = "";
		
		switch(this.getType()){
			case 0:
				name = "b";
				break;
			case 1:
				name = "B";
				break;
			case 2:
				name = "KB";
				break;
			case 3:
				name = "MB";
				break;
			case 4:
				name = "GB";
				break;
			case 5:
				name = "TB";
				break;
			case 6:
				name = "PB";
				break;
			case 7:
				name = "EB";
				break;
			case 8:
				name = "ZB";
				break;
			case 9:
				name = "YB";
				break;
		}
		
		return name;
	}
	
	private int getTypeMultiplier(int type){
		int multi = 0;
		
		switch(type){
			case 1:
				multi = 8;
				break;
			default:
				multi = 1024;
				break;
		}
		
		return multi;
	}
	
	private double convertSize(){
		return this.convertSize(this.getBit(), 0);
	}
	
	private long convertBit(double size, int type){
		long bits = 0;
		
		switch(type){
			case 0:
				bits = (long) size;
				break;
			case 1:
				bits = this.convertBit(size*this.getTypeMultiplier(type), type-1);
				break;
			default:
				bits = this.convertBit(size*this.getTypeMultiplier(type), type-1);
				break;
		}
		return bits;
	}
	
    public static double format(Double number){
    	DecimalFormat df = new DecimalFormat("###.#");
    	
    	try{
			number = Double.parseDouble(df.format(number));
		} catch(NumberFormatException e){
			Debug.d("NumberFormatException");
		}
		
    	return number;
    }
    
    public String getSizeString(){
    	return Double.toString(File.format(this.getSize()));
    }
    
    private double toKB(double amount){
		double kb;
		
		kb = (amount/8)/1024;
		
		return kb;
	}
    
    public long get(){
    	return this.getBit();
    }

	@Override
	public String toString() {
		return "File [getBit()=" + getBit() + ", getType()=" + getType() + ", getSize()=" + getSize() + ", getSizeString()=" + getSizeString() + "]";
	}
	
}
