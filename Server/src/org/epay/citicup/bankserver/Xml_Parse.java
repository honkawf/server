package org.epay.citicup.bankserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.xml.stream.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;


import org.jdom2.*;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.StAXStreamBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import businessinfo.BusinessInfo;

import personinfo.PersonInfo;

public class Xml_Parse {
	public Xml_Parse(byte [] xml_file) throws JDOMException, IOException, XMLStreamException, TransformerException {
		DOMResult docResult=new DOMResult();
		Transformer tran=TransformerFactory.newInstance().newTransformer();
		tran.transform(new StreamSource(new ByteArrayInputStream(xml_file)), docResult);
		  DOMBuilder builder = new DOMBuilder();
		org.w3c.dom.Document Oxml_doc=(org.w3c.dom.Document)docResult.getNode();
		xml_doc=builder.build(Oxml_doc);
		price_xml=new Document();
		XMLOutputter out = new XMLOutputter();
		Format format=out.getFormat();
		format.setEncoding("utf-8");
		out.setFormat(format);
		String Xml_Str=out.outputString(xml_doc);
		System.out.println("收到"+Xml_Str);
		 }
	
	public void Xml_deal(OutputStream out_result) throws IOException, JDOMException, ClassNotFoundException, SQLException
	{
		if(xml_doc.hasRootElement()==true)
		{
			Element root = xml_doc.getRootElement();//��ø�ڵ�
			String event=root.getAttributeValue("event");
			if(event.equals("confirmTrade"))
			{
				if(Transfer_Account())
				{
					Send_Sentence_Xml("付款成功",out_result);
				}
				else
				{
					Send_Sentence_Xml("付款失败",out_result);
				}
			}
			else if(event.equals("downloadPassword"))
			{
				if(Download_Password())
				{
					Download_Success(out_result);
				}
				else
				{
					Send_Sentence_Xml(sentence,out_result);
				}
			}
			else if(event.equals("register"))
			{
				if(registerAccount())
					Send_Sentence_Xml("注册成功",out_result);
				else
					Send_Sentence_Xml("注册失败",out_result);
			}
			else if(event.equals("checkAccount"))
			{
				if(checkAccountCorrect())
					Send_Sentence_Xml("可以使用",out_result);
				else
					Send_Sentence_Xml("不可以使用",out_result);
			}
			else if(event.equals("linkBankCard"))
			{
				if(linkBankCard())
					Send_Sentence_Xml("绑定成功",out_result);
				else
					Send_Sentence_Xml("绑定失败",out_result);
			}
			else if(event.equals("transfer"))
			{
				if(Ele_Note())
					Notepay_Success(out_result);
				else
					Send_Sentence_Xml("电子支票兑现失败",out_result);
			}
			else if(event.equals("supermarketlogin"))
			{
				if(Store_Randcode())
					Store_Search_Success(out_result);
				else
					Send_Sentence_Xml("随机码错误",out_result);
			}
			else if(event.equals("modifyPassword"))
			{
				if(Modify_Password())
					Send_Sentence_Xml("修改成功",out_result);
				else
					Send_Sentence_Xml("修改失败",out_result);
			}
			else if(event.equals("modifyPhonenum"))
			{
				if(Modify_Phonenumber())
					Send_Sentence_Xml("修改成功",out_result);
				else
					Send_Sentence_Xml("修改失败",out_result);
			}
			else
			{
				Xml_Error(out_result);
			}
		}
		else
		{
			Xml_Error(out_result);
		}
	}
	public void Send_Sentence_Xml(String Sentence,OutputStream out_result) throws IOException
	{
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent(Sentence);
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}
	
	private boolean Modify_Phonenumber() {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		Element root = xml_doc.getRootElement();
		if(databaseOP.Modify_Phonenumber(root.getChildText("username"),root.getChildText("phonenum")))
			return true;
		else
			return false;
	}

	private boolean Modify_Password() {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		Element root = xml_doc.getRootElement();
		if(databaseOP.Modify_Password(root.getChildText("username"),root.getChildText("password")))
			return true;
		else
			return false;
	}
	/*
	private void Store_Search_Failure(OutputStream out_result) throws IOException {
		// TODO Auto-generated method stub
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent("随机码错误");
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}
*/
	private void Store_Search_Success(OutputStream out_result) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		Element root = xml_doc.getRootElement();
		Element business_info_node=root.getChild("businessinfo");
		String randcode=business_info_node.getChildText("randcode");
		BusinessInfo business_info=databaseOP.getBusinessInfo(randcode);
		Element username=new Element("username");
		username.setText(business_info.getUsername());
		Element storename=new Element("storename");
		storename.setText(business_info.getStorename());
		Element cardnum=new Element("cardnum");
		cardnum.setText(business_info.getCardnum());
		Element bluetoothpwd=new Element("bluetoothpwd");
		bluetoothpwd.setText(business_info.getBluetoothpwd());
		Element balance=new Element("balance");
		balance.setText(business_info.getBalance());
		business_info_node.addContent(username);
		business_info_node.addContent(storename);
		business_info_node.addContent(cardnum);
		business_info_node.addContent(bluetoothpwd);
		business_info_node.addContent(balance);
		Xml_Send(out_result,xml_doc);
	}
	
	private boolean Store_Randcode() {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		Element root = xml_doc.getRootElement();
		String randcode=root.getChild("businessinfo").getChildText("randcode");
		if(databaseOP.Store_Randcode_Search(randcode))
			return true;
		else
			return false;
	}
/*
	private void Notepay_Failure(OutputStream out_result) throws IOException {
		// TODO Auto-generated method stub
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent("电子支票兑现失败");
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}*/

	private void Notepay_Success(OutputStream out_result) throws IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("transfer");
		List<Element> list = gl.getChildren();
		String hash=gl.getChildText("cipher");
		String payer_cardnum=gl.getChildText("payercardnumber");
		String amount=gl.getChildText("totalprice");
		String recer_cardnum=gl.getChildText("receivercardnumber");
		databaseOP.Insert_Elenote(hash, payer_cardnum, recer_cardnum, amount);
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent("电子支票兑现成功");
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}
/*
	private void Reset_Failed(OutputStream out_result) throws IOException {
		// TODO Auto-generated method stub
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent(sentence);
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}*/

	private void Download_Success(OutputStream out_result) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Database_Deal databaseOP =new Database_Deal();
		person=databaseOP.getPersonInfo(publicvariable);
		Element root = new Element("information");
		root.setAttribute("event", "returnPassword");
		root.addContent("密码文件下载成功");
		Element usename=new Element("username");
		usename.setText(person.getUsername());
		Element password=new Element("password");
		password.setText(person.getPassword());
		Element cardnumber=new Element("cardnumber");
		cardnumber.setText(person.getCardnum());
		Element privatekey=new Element("privatekey");
		privatekey.setText(person.getPrivatekey());
		Element balance=new Element("balance");
		balance.setText(person.getBalance());
		Element publickeyn=new Element("publickeyn");
		publickeyn.setText(person.getPublickeyn());
		root.addContent(usename);
		root.addContent(password);
		root.addContent(cardnumber);
		root.addContent(privatekey);
		root.addContent(balance);
		root.addContent(publickeyn);
		price_xml.setRootElement(root);
		Xml_Send(out_result,price_xml);
	}

	private boolean Download_Password() {
		// TODO Auto-generated method stub
		//解析xml，获得银行卡号，身份证号，若正确，则成功，否则失败
		Element root = xml_doc.getRootElement();
		String cardnumber = root.getChildText("cardnumber");
		String identificationnumber = root.getChildText("identificationnumber");
		String imei = root.getChildText("imei");
		Database_Deal databaseOP =new Database_Deal();
		try {
			if(databaseOP.resetPassword(cardnumber,identificationnumber))
			{
			if(databaseOP.compareImei(cardnumber, imei))
			{
				publicvariable=cardnumber;
				return true;
			}
			else
			{
				sentence="请重新注册";
				return false;
			}
			}
			else
			{
				sentence="密码文件下载失败";
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void Xml_Send(OutputStream out_result,Document xml_to_send) throws IOException
	{
		XMLOutputter out = new XMLOutputter();
		Format format=out.getFormat();
		format.setEncoding("utf-8");
		out.setFormat(format);
		String Xml_Str=out.outputString(xml_to_send);
		System.out.println("test"+Xml_Str);
		int length=Xml_Str.getBytes().length;
		String len=Integer.toString(length);
		byte[] leng=len.getBytes();
		byte[] sendlen=new byte[16];
		int lit=16-leng.length;
		for(int counter=0;counter<leng.length;counter++)
			sendlen[lit+counter]=leng[counter];
		out_result.write(sendlen);
		out_result.flush();		
		out_result.write(Xml_Str.getBytes());
		out_result.flush();
	}
	
	public void Xml_Error(OutputStream out_result) throws IOException
	{
		String error="Not standard Xml doc.";
		int length=error.getBytes().length;
		String len=Integer.toString(length);
		byte[] leng=len.getBytes();
		byte[] sendlen=new byte[16];
		int lit=16-leng.length;
		for(int counter=0;counter<leng.length;counter++)
			sendlen[lit+counter]=leng[counter];
		System.out.println(new String(sendlen));
		System.out.println(error);
		out_result.write(sendlen);
		out_result.flush();		
		out_result.write(error.getBytes());
	}

	public Boolean Transfer_Account() throws JDOMException, FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("trade");
		List<Element> list = gl.getChildren();
		Database_Deal Qurry_Db =new Database_Deal();
		String hash=gl.getChildText("cipher");
		String num=gl.getChildText("cardnumber");
		String buyer_cardnum=num;
		String store_cardnum=gl.getChildText("storenum");
		System.out.println(hash);
		Rsa user_rsa=Qurry_Db.Query_Rsa(num);
		num=user_rsa.Rersa(hash);
		System.out.println("明文："+num);
		String time=gl.getChildText("tradetime");
		String price=gl.getChildText("totalprice");
		double temp=Double.valueOf(price);
		temp=temp*100;
		int temp_int=(int)temp;
		String tprice=String.format("%08d", temp_int);
		if(time.equals(num.substring(0, 10))&&tprice.equals(num.substring(20, 28)))
		{
			System.out.println("es");
			if(Qurry_Db.Transfer_Out_Person(buyer_cardnum, price)&&Qurry_Db.Transfer_In_Store(store_cardnum, price))
			{
					System.out.println("ts");
					return true;
			}
			else
			{
				System.out.println("tf");
				return false;
			}
		}
		else
		{
			System.out.println("time="+time+"   "+num.substring(0,10));
			System.out.println("price="+price+"   "+num.substring(20, 28));
			System.out.println("ef");
			return false;
		}
		/*
		for(Element e:list) {
			   if(e.getName()=="cipher")
			   {
				   Goods goods=new Goods(Qurry_Db.Query_goods(e.getChildText("barcode")));
				   if(goods.getName().equals("")||goods.getName().isEmpty())
				   {
					   Non_Goods(out_result);
					   return false;
				   }
				   System.out.println(e.getChildText("barcode")+goods.getName()+" "+goods.getPrice());
				   e.getChild("name").setText(goods.getName());
				   e.getChild("price").setText(goods.getPrice());
			   }
			  }
			  */
//		Xml_Send(out_result,xml_doc);
	}
	
	public Boolean Ele_Note() throws JDOMException, FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("transfer");
		List<Element> list = gl.getChildren();
		Database_Deal Qurry_Db =new Database_Deal();
		String hash=gl.getChildText("cipher");
		String num=gl.getChildText("payercardnumber");
		String payer_cardnum=num;
		String receiver_cardnum=gl.getChildText("receivercardnumber");
		System.out.println(hash);
		Rsa user_rsa=Qurry_Db.Query_Rsa(num);
		num=user_rsa.Rersa(hash);
		System.out.println("明文："+num);
		String time=gl.getChildText("transfertime");
		String price=gl.getChildText("totalprice");
		double temp=Double.valueOf(price);
		temp=temp*100;
		int temp_int=(int)temp;
		String tprice=String.format("%08d", temp_int);
		if(time.equals(num.substring(0, 10))&&tprice.equals(num.substring(15, 23)))
		{
			System.out.println("es");
			if(Qurry_Db.Transfer_Out_Person(payer_cardnum, price)&&Qurry_Db.Transfer_In_Person(receiver_cardnum, price))
			{
				if(Qurry_Db.Elenote_Exist(hash))
				{
					return false;
				}
				else
				{
					System.out.println("ts");
					return true;
				}
			}
			else
			{
				System.out.println("tf");
				return false;
			}
		}
		else
		{
			System.out.println("time="+time+"   "+num.substring(0,10));
			System.out.println("price="+price+"   "+num.substring(20, 28));
			System.out.println("ef");
			return false;
		}
		/*
		for(Element e:list) {
			   if(e.getName()=="cipher")
			   {
				   Goods goods=new Goods(Qurry_Db.Query_goods(e.getChildText("barcode")));
				   if(goods.getName().equals("")||goods.getName().isEmpty())
				   {
					   Non_Goods(out_result);
					   return false;
				   }
				   System.out.println(e.getChildText("barcode")+goods.getName()+" "+goods.getPrice());
				   e.getChild("name").setText(goods.getName());
				   e.getChild("price").setText(goods.getPrice());
			   }
			  }
			  */
//		Xml_Send(out_result,xml_doc);
	}
	/*
	public void Transfer_Success(OutputStream out_result) throws IOException
	{
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent("付款成功");
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}
	*/
	
	/*
	public void Transfer_Failed(OutputStream out_result) throws IOException
	{
		Element price_root = new Element("information");
		price_root.setAttribute("event", "sentence");
		price_root.addContent("付款失败");
		price_xml.setRootElement(price_root);
		Xml_Send(out_result,price_xml);
	}*/
	
	public boolean checkAccountCorrect() throws JDOMException, FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("personInfo");
		Database_Deal datebaseOP =new Database_Deal();
		
		String userName = gl.getChildText("userName");
		return datebaseOP.checkAccountCorrect(userName);
	}
	/*
	public void checkAccountSuccess(OutputStream out_result) throws IOException
	{
		Element checkAccount_root = new Element("information");
		checkAccount_root.setAttribute("event", "sentence");
		checkAccount_root.addContent("可以使用");
		price_xml.setRootElement(checkAccount_root);
		Xml_Send(out_result,price_xml);
	}*/
	/*
	public void checkAccountFailure(OutputStream out_result) throws IOException
	{
		Element checkAccount_root = new Element("information");
		checkAccount_root.setAttribute("event", "sentence");
		checkAccount_root.addContent("不可以使用");
		price_xml.setRootElement(checkAccount_root);
		Xml_Send(out_result,price_xml);
	}*/

	
	public boolean registerAccount() throws JDOMException, FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("personInfo");
		Database_Deal datebaseOP =new Database_Deal();
		
		String userName = gl.getChildText("userName");
		String password = gl.getChildText("password");
		String customerName = gl.getChildText("customerName");
		String bluetoothMac = gl.getChildText("bluetoothMac");
		System.out.println(userName+password+ customerName+ bluetoothMac);
		return datebaseOP.registerNewAccount(userName, password, customerName, bluetoothMac);
	}
	/*
	public void registerAccountSuccess(OutputStream out_result) throws IOException
	{
		Element registerAccount_root = new Element("information");
		registerAccount_root.setAttribute("event", "sentence");
		registerAccount_root.addContent("注册成功");
		price_xml.setRootElement(registerAccount_root);
		Xml_Send(out_result,price_xml);
	}*/
	/*
	public void registerAccountFailure(OutputStream out_result) throws IOException
	{
		Element registerAccount_root = new Element("information");
		registerAccount_root.setAttribute("event", "sentence");
		registerAccount_root.addContent("注册失败");
		price_xml.setRootElement(registerAccount_root);
		Xml_Send(out_result,price_xml);
	}*/
	
	public boolean linkBankCard() throws JDOMException, FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		Element root = xml_doc.getRootElement();
		Element gl=root.getChild("personInfo");
		Database_Deal datebaseOP =new Database_Deal();
		
		String userName = gl.getChildText("userName");
		String cardNum = gl.getChildText("cardNum");
		String phoneNum = gl.getChildText("phoneNum");
		String identificationCardNum = gl.getChildText("identificationCardNum");
		System.out.println(userName+cardNum+ phoneNum+ identificationCardNum);
		return datebaseOP.linkBankCard(userName, cardNum, phoneNum, identificationCardNum);
	}
	/*
	public void linkBankCardSuccess(OutputStream out_result) throws IOException
	{
		Element linkBankCard_root = new Element("information");
		linkBankCard_root.setAttribute("event", "sentence");
		linkBankCard_root.addContent("绑定成功");
		price_xml.setRootElement(linkBankCard_root);
		Xml_Send(out_result,price_xml);
	}*/
	/*
	public void linkBankCardFailure(OutputStream out_result) throws IOException
	{
		Element linkBankCard_root = new Element("information");
		linkBankCard_root.setAttribute("event", "sentence");
		linkBankCard_root.addContent("绑定失败");
		price_xml.setRootElement(linkBankCard_root);
		Xml_Send(out_result,price_xml);
	}*/
	
	private Document xml_doc;
	private Document price_xml;
	private PersonInfo person;
	private String publicvariable;
	private String sentence;
}
