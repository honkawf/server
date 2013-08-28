package org.epay.citicup.bankserver;

import java.math.*;
import java.util.*;
public class Rsa{
 /**
  * @param args
 * @return 
  */
	Rsa()
	{
		 n=new BigInteger("552242638982356744711324281679");
		 e=new BigInteger("2309916916024662023");
		 d=new BigInteger("435982084263274353850676993687");
	}
	
	Rsa(BigInteger o_n,BigInteger o_d)
	{
		 n=o_n;
		 e=new BigInteger("0");
		 d=o_d;
	}
	
 public String Setrsa(String code) {
  // TODO Auto-generated method stub
	
  BigInteger mescode=new BigInteger(code);
  BigInteger sec=mescode.modPow(e, n);
  return sec.toString();
 }
 
 public Boolean Checkrsa(String money,String cipher)
 {
	 BigInteger sec=new BigInteger(cipher);
	  BigInteger seco=sec.modPow(d, n);
	  String checkcode=seco.toString();
	  if(checkcode.endsWith(money))
	  	return true;
	  else
		 return false;
 }
 
 public String Rersa(String cipher)
 {
  BigInteger sec=new BigInteger(cipher);
//  System.out.println("cip="+cipher);
//  System.out.println("d="+d);
//  System.out.println("n="+n);
  BigInteger seco=sec.modPow(d, n);
  return seco.toString();
 }
 
 private BigInteger n;
 private BigInteger e;
 private BigInteger d;
}