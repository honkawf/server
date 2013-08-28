package org.epay.citicup.bankserver;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;

import elenoteinfo.ElenoteInfo;
import elenoteinfo.ElenoteInfoDAO;
import elenoteinfo.IElenoteInfoDAO;

import personinfo.IPersonInfoDAO;
import personinfo.PersonInfo;
import personinfo.PersonInfoDAO;

import banktrade.BankTradeDAO;
import banktrade.IBankTradeDAO;
import businessinfo.BusinessInfo;
import businessinfo.BusinessInfoDAO;
import businessinfo.IBusinessInfoDAO;




public class Database_Deal {
/*
	Database_Deal()
	{
		url="jdbc:oracle:thin:@localhost:1521/";
		SID="CITICUP";
		USERNAME="SYSTEM";
		PASSWORD="citicup";
	}
	
	Database_Deal(String My_Sid,String My_Username,String My_Password)
	{
		url="jdbc:oracle:thin:@localhost:1521/";
		SID=My_Sid;
		USERNAME=My_Username;
		PASSWORD=My_Password;
	}
	
	public Connection Connect_bank_db() throws ClassNotFoundException,SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");      
		Connection conn =DriverManager.getConnection(url+SID,USERNAME,PASSWORD);     
		conn.setAutoCommit(false);
		return conn;    
	}
*/	
	public boolean Transfer_Out_Person(String cardnumber,String amount) throws ClassNotFoundException, SQLException
	{
		String query_result=null;
		/*
		Connection tempcon=Connect_bank_db();
		Statement stmt = tempcon.createStatement();    
		ResultSet rset =          stmt.executeQuery("select BALANCE from person_info where CARDNUM='"+cardnumber+"'");    
		while (rset.next()) 
		{        
			query_result=rset.getString(1);
			}    
			*/
	
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		List<PersonInfo> person_info_list=person_info_dao.findByCardnum(cardnumber);
		PersonInfo person_info=new PersonInfo();
		for(PersonInfo intor:person_info_list)
			person_info=intor;
		query_result=person_info.getBalance();
		
		if(Double.parseDouble(query_result)<Double.parseDouble(amount))
		{
			return false;
		}
		else
		{
			double newbalance=Double.parseDouble(query_result)-Double.parseDouble(amount);
			query_result=Double.toString(newbalance);
//			stmt.executeUpdate("update person_info set BALANCE='"+query_result+"' where CARDNUM='"+cardnumber+"'");
		
			person_info.setBalance(query_result);
			person_info_dao.update(person_info);
			
//			tempcon.commit();
//			stmt.close(); 
			return true;
		}
	}
	
	public boolean Transfer_Ont_Store(String cardnumber,String amount) throws ClassNotFoundException, SQLException
	{
		String query_result=null;
		/*
		Connection tempcon=Connect_bank_db();
		Statement stmt = tempcon.createStatement();    
		ResultSet rset =          stmt.executeQuery("select BALANCE from business_info where CARDNUM='"+cardnumber+"'");    
		while (rset.next()) 
		{        
			query_result=rset.getString(1);
			}    
			*/
		
		IBusinessInfoDAO business_info_dao=new BusinessInfoDAO();
		List<BusinessInfo> business_info_list=business_info_dao.findByCardnum(cardnumber);
		BusinessInfo business_info=new BusinessInfo();
		for(BusinessInfo intor:business_info_list)
			business_info=intor;
		query_result=business_info.getBalance();
		
		if(Double.parseDouble(query_result)<Double.parseDouble(amount))
		{
			return false;
		}
		else
		{
			double newbalance=Double.parseDouble(query_result)-Double.parseDouble(amount);
			query_result=Double.toString(newbalance);
//			stmt.executeUpdate("update business_info set BALANCE='"+query_result+"' where CARDNUM='"+cardnumber+"'");
			
			business_info.setBalance(query_result);
			business_info_dao.update(business_info);
			
//			tempcon.commit();
//			stmt.close(); 
			return true;
		}
	}
	
	public boolean Transfer_In_Person(String cardnumber,String amount) throws ClassNotFoundException, SQLException
	{
		String query_result=null;
		/*
		Connection tempcon=Connect_bank_db();
		Statement stmt = tempcon.createStatement();    
		ResultSet rset =          stmt.executeQuery("select BALANCE from person_info where CARDNUM='"+cardnumber+"'");    
		while (rset.next()) 
		{        
			query_result=rset.getString(1);
			}    
			*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		List<PersonInfo> person_info_list=person_info_dao.findByCardnum(cardnumber);
		PersonInfo person_info=new PersonInfo();
		for(PersonInfo intor:person_info_list)
			person_info=intor;
		query_result=person_info.getBalance();
		
		if(query_result.isEmpty())
		{
//			stmt.close(); 
			return false;
		}
		else
		{
			double newbalance=Double.parseDouble(query_result)+Double.parseDouble(amount);
			query_result=Double.toString(newbalance);
//			stmt.executeUpdate("update person_info set BALANCE='"+query_result+"' where CARDNUM='"+cardnumber+"'");

			person_info.setBalance(query_result);
			person_info_dao.update(person_info);
			
//			tempcon.commit();
//			stmt.close(); 
			return true;
		}
	}
	
	public boolean Transfer_In_Store(String cardnumber,String amount) throws ClassNotFoundException, SQLException
	{
		String query_result=null;
		/*
		Connection tempcon=Connect_bank_db();
		Statement stmt = tempcon.createStatement();    
		ResultSet rset =          stmt.executeQuery("select BALANCE from business_info where CARDNUM='"+cardnumber+"'");    
		while (rset.next()) 
		{        
			query_result=rset.getString(1);
			} 
			*/
		IBusinessInfoDAO business_info_dao=new BusinessInfoDAO();
		List<BusinessInfo> business_info_list=business_info_dao.findByCardnum(cardnumber);
		BusinessInfo business_info=new BusinessInfo();
		for(BusinessInfo intor:business_info_list)
			business_info=intor;
		query_result=business_info.getBalance();
		
		if(query_result.isEmpty())
		{
//			stmt.close(); 
			return false;
		}
		else
		{
			double newbalance=Double.parseDouble(query_result)+Double.parseDouble(amount);
			query_result=Double.toString(newbalance);
//			stmt.executeUpdate("update business_info set BALANCE='"+query_result+"' where CARDNUM='"+cardnumber+"'");

			business_info.setBalance(query_result);
			business_info_dao.update(business_info);
			
//			tempcon.commit();
//			stmt.close(); 
			return true;
		}
	}
	
	public Rsa Query_Rsa(String cardnumber) throws ClassNotFoundException, SQLException
	{
//		System.out.println("cardnum="+cardnumber);
		String query_result=null;
		BigInteger n=new BigInteger("0");
		BigInteger d=new BigInteger("0");
		/*
		Connection tempcon=Connect_bank_db();
		Statement stmt = tempcon.createStatement();    
		ResultSet rsetn =          stmt.executeQuery("select commonkeyofn from person_info where CARDNUM='"+cardnumber+"'");    
		while (rsetn.next()) 
		{        
			query_result=rsetn.getString(1);
			n=new BigInteger(query_result);
			}    
		ResultSet rsetd =          stmt.executeQuery("select commonkeyofd from person_info where CARDNUM='"+cardnumber+"'");    
		while (rsetd.next()) 
		{        
			query_result=rsetd.getString(1);
			d=new BigInteger(query_result);
			}    
			*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		List<PersonInfo> person_info_list=person_info_dao.findByCardnum(cardnumber);
		PersonInfo person_info=new PersonInfo();
		for(PersonInfo intor:person_info_list)
			person_info=intor;
		query_result=person_info.getPublickeyd();
		d=new BigInteger(query_result);
		query_result=person_info.getPublickeyn();
		n=new BigInteger(query_result);
		
		Rsa rsa=new Rsa(n,d);
//			stmt.close(); 
			return rsa;
	}
	
	public boolean checkAccountCorrect(String userName) throws ClassNotFoundException, SQLException{
		/*
		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		ResultSet rsetn = stmt.executeQuery("select userName from person_info where userName ='" + userName + "'");
		if(rsetn.next())
			return false;
		else
			return true;
			*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		PersonInfo person_info=person_info_dao.findById(userName);
		if(person_info.getUsername().isEmpty())
			return true;
		else
			return false;
	}
	
	public boolean registerNewAccount(String userName, String password, String customerName, String bluetoothMac) throws ClassNotFoundException, SQLException{
/*
		String dynamicPasswordNum="",commonKeyOFN="",commonKeyOFD="";
		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		System.out.println("1");
		stmt.executeUpdate("insert into person_info "
						+"values ('" + userName + "', '" + customerName +"', '', '" +bluetoothMac + "', '" + dynamicPasswordNum + "', '', '', '', '" + commonKeyOFN + "', '" + commonKeyOFD +"', '" + password + "')");
		tempcon.commit();
		tempcon.close();
		stmt.close();
		
		Connection tempcon2 = Connect_bank_db();
		Statement stmt2 = tempcon2.createStatement();
		ResultSet rsetn2 = stmt2.executeQuery("select userName from person_info where userName ='" + userName + "'");
		if(rsetn2.next())
			return true;
		else
			return false;
		*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		PersonInfo person_info=new PersonInfo();
		person_info.setUsername(userName);
		person_info.setPassword(password);
		person_info.setCustomername(customerName);
		person_info.setBluetoothmac(bluetoothMac);
		person_info_dao.save(person_info);
		PersonInfo exam_person_info=person_info_dao.findById(userName);
		if(exam_person_info.getUsername().isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean linkBankCard(String userName, String cardNum, String phoneNum, String idCardNum) throws ClassNotFoundException, SQLException{
/*		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		stmt.executeUpdate("update person_info set cardNum = '" + cardNum +"', phoneNum = '" + phoneNum + "', identificationCardNum = '" + idCardNum + "'"
				+ "where userName ='" + userName + "'");
		tempcon.commit();
		tempcon.close();
		stmt.close();
		
		Connection tempcon2 = Connect_bank_db();
		Statement stmt2 = tempcon2.createStatement();
		ResultSet rsetn2 = stmt2.executeQuery("select cardNum from person_info where userName ='" + userName + "'");
		if(rsetn2.next())
			return true;
		else
			return false;
	*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();	
		PersonInfo person_info=person_info_dao.findById(userName);
		if(person_info.getUsername().isEmpty())
			return false;
		else
		{
			person_info.setUsername(userName);
			person_info.setCardnum(cardNum);
			person_info.setPhonenum(phoneNum);
			person_info.setIdentificationcardnum(idCardNum);
			person_info_dao.save(person_info);
			PersonInfo exam_person_info=person_info_dao.findById(userName);
			if(exam_person_info.getUsername().isEmpty())
				return false;
			else
				return true;
		}
	}
	
	
	public boolean resetPassword(String cardNumber , String idCardNumber) throws SQLException, ClassNotFoundException{
/*
		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		String query = "select * from person_info where cardNum = '" + c + "' and identificationCardNum = '" + i + "'";
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()){
			tempcon.commit();
			tempcon.close();
			stmt.close();
			return true;
		}
		else{
			tempcon.commit();
			tempcon.close();
			stmt.close();
			return false;
		}
		*/
		
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		List<PersonInfo> person_info_list=person_info_dao.findByCardnum(cardNumber);
		PersonInfo person_info=new PersonInfo();
		for(PersonInfo intor:person_info_list)
			{
				if(idCardNumber.equals(intor.getIdentificationcardnum()))
					return true;
				else
					return false;
			}
		return false;
	}
	
	public boolean Insert_Elenote(String paynum,String payernum,String recernum,String amount) throws SQLException, ClassNotFoundException
	{
		/*
		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		stmt.executeUpdate("insert into elenote_info values ('" + paynum +"', '" + payernum + "', '" + recernum +"', '" +amount+"')");
		tempcon.commit();
		tempcon.close();
		stmt.close();
		return true;
		*/
		
		IElenoteInfoDAO elenote_info_dao=new ElenoteInfoDAO();
		ElenoteInfo elenote_info=new ElenoteInfo();
		elenote_info.setPaynum(paynum);
		elenote_info.setPayernum(payernum);
		elenote_info.setRecernum(recernum);
		elenote_info.setAmount(amount);
		elenote_info_dao.save(elenote_info);
		return true;
	}
	
	public boolean Elenote_Exist(String elenote_num) throws ClassNotFoundException, SQLException
	{
	/*	Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		String query = "select amount from elenote_info where paynum = '" + elenote_num + "'";
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		if(rs.getString(1)==null)
			return false;
		else
			return true;
			*/
		
		IElenoteInfoDAO elenote_info_dao=new ElenoteInfoDAO();
		ElenoteInfo elenote_info=elenote_info_dao.findById(elenote_num);
		if(elenote_info.getAmount().isEmpty())
			return  false;
		else
			return true;
	}
	
	public PersonInfo getPersonInfo(String cardnumber) throws ClassNotFoundException, SQLException
	{
		IPersonInfoDAO person_info_dao=new PersonInfoDAO();
		List<PersonInfo> person_info_list=person_info_dao.findByCardnum(cardnumber);
		PersonInfo person_info=new PersonInfo();
		for(PersonInfo intor:person_info_list)
			person_info=intor;
		/*
		Connection tempcon = Connect_bank_db();
		Statement stmt = tempcon.createStatement();
		String query = "select * from person_info where cardNum = '" + cardnumber + "'";
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		person_info.setUsername(rs.getString("username"));
		person_info.setPassword(rs.getString("password"));
		person_info.setCardnum(rs.getString("cardNum"));
		person_info.setPrivatekey(rs.getString("dynamicPasswordNum"));
		person_info.setBalance(rs.getString("balance"));
		tempcon.commit();
		tempcon.close();
		stmt.close();
		*/
		return person_info;
	}
	/*
	private String url;
	private String SID;
	private String USERNAME;
	private String PASSWORD;
*/
	
}
