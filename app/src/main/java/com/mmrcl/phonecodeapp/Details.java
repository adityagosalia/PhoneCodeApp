package com.mmrcl.phonecodeapp;


import java.io.Serializable;

public class Details implements Serializable {


	String deptName, deptDetails;
	String mobileNo;
	String landline;
	String extCode;


	@Override
	public String toString() {
		return "Details{" +
				"deptName='" + deptName + '\'' +
				", deptDetails='" + deptDetails + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", landline='" + landline + '\'' +
				", extCode='" + extCode + '\'' +
				'}';
	}

	/**
	 * @return the extCode
	 */
	public String getExtCode() {
		return extCode;
	}
	/**
	 * @param extCode the extCode to set
	 */
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptDetails() {
		return deptDetails;
	}
	public void setDeptDetails(String deptDetails) {
		this.deptDetails = deptDetails;
	}
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public boolean valid(){
		if(mobileNo.equals("") || mobileNo==null){
			return false;
		}
		else if(deptName.equals("") || deptName==null){

			return false;
		}
		else if(deptDetails.equals("")||deptDetails==null){
			//deptDetails="no details available";
			return true;
		}
		else{
			return true;
		}
	}
	public boolean isPresent(String value)
	{
		if(deptName.contains(value)||deptDetails.contains(value)||extCode.contains(value))
		{
			return true;
		}
		else{
			return false;
		}
	}

}
