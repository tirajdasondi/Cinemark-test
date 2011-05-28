package com.myapp;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Films extends ListActivity {

	ConnectivityManager connectivity;
	NetworkInfo wifiInfo, mobileInfo;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Context context = getApplicationContext();
	        final String[] filmlist1 = new String[]{"A","B","C"};
	      //To check the connection of the Internet.
	        connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    mobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	        
	        if(mobileInfo.isConnected()==true)
	        {
	        	ImageView img =new ImageView(this);
	        	//Bitmap drawable = getRemoteImage("http://www.cinemark.com/media/402997/090122h1.jpg");
	        	Drawable drawable = getResources().getDrawable(R.drawable.netimage);
	        	//Drawable draw = new BitmapDrawable(drawable);
	        	img.setImageDrawable(drawable);
	        	
	        	CharSequence text = "Welcome to CINEMARK!";
	        	int duration = Toast.LENGTH_LONG;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
	        	
	        	//setContentView(img);
	        	Top10();
	        	
	        	 
	        }
	        
	        else if(mobileInfo.isConnected()==false)
	        {
	        	ImageView img =new ImageView(this);
	        	Drawable drawable = getResources().getDrawable(R.drawable.defaulthome);
	        	img.setImageDrawable(drawable);
	        	
	        	CharSequence text = "Catch the connection to see the feature film!";
	        	int duration = Toast.LENGTH_LONG;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
	        	
	        	//setContentView(img);
	        	 this.setListAdapter(new ArrayAdapter<String>(this,
		 	 				android.R.layout.simple_list_item_1, filmlist1));
	        	
	        	
	        }
	 }
	 
	//Method to create menu in the activity.
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.film_menu, menu);
	        return true;
	    }
	    
	    //Method to respond to Menu click events.
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	        case R.id.WeekRelease:
	        	// Intent intent1 = new Intent(this, WeekRelease.class);
	             //this.startActivity(intent1);
	        	WeekReleases();
	             break;
	        case R.id.NowShowing:
	        	 //Intent intent2 = new Intent(this, NowShowing.class);
	             //this.startActivity(intent2);
	        	NowShowing();
	             break;
	             
	        case R.id.UpcomingRelease:
	       	 //Intent intent3 = new Intent(this, UpcomingRelease.class);
	            //this.startActivity(intent3);
	        	UpcomingReleases();
	            break;
	             
	        case R.id.Top10:
	       	 //Intent intent4 = new Intent(this, Top10.class);
	         //   this.startActivity(intent4);
	        	Top10();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }
	    
	    public void Top10()
	    {
	    	 LinearLayout layout = new LinearLayout(this);
	         layout.setOrientation(1);

	         /** Create a new textview array to display the results*/ 
	         TextView theatername[];
	         String theaterlist[];
	         //TextView website[];
	         TextView category[];

	         try {

	         URL url = new URL(
	         "http://www.cinemark.com.br/mobile/xml/films/?top=10");
	         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new InputSource(url.openStream()));
	         doc.getDocumentElement().normalize();

	         NodeList nodeList = doc.getElementsByTagName("film");

	         /** Assign textview array lenght by arraylist size */
	         theatername = new TextView[nodeList.getLength()];
	         //website = new TextView[nodeList.getLength()];
	         category = new TextView[nodeList.getLength()];
	         theaterlist = new String[nodeList.getLength()];
	         for (int i = 0; i < nodeList.getLength(); i++) {

	         Node node = nodeList.item(i);

	         theatername[i] = new TextView(this);
	         
	         //website[i] = new TextView(this);
	         category[i] = new TextView(this);

	         Element fstElmnt = (Element) node;
	         NodeList nameList = fstElmnt.getElementsByTagName("film");
	         Element nameElement = (Element) nameList.item(0);
	         nameList = nameElement.getChildNodes();
	         theatername[i].setText(""
	         + ((Node) nameList.item(0)).getNodeValue());
	         theaterlist[i] = theatername[i].getText().toString();
	         NodeList websiteList = fstElmnt.getElementsByTagName("website");
	         Element websiteElement = (Element) websiteList.item(0);
	         //websiteList = websiteElement.getChildNodes();
	         //website[i].setText("Website = "
	         //+ ((Node) websiteList.item(0)).getNodeValue());

	         //category[i].setText("Website Category = "
	         //+ websiteElement.getAttribute("category"));
	         
	         
	         
	         layout.addView(theatername[i]);
	         //layout.addView(website[i]);
	         //layout.addView(category[i]);
	         
	         }
	        
	         this.setListAdapter(new ArrayAdapter<String>(this,
	 				android.R.layout.simple_list_item_1, theaterlist));
	         
	         ListView lv = getListView();
	         lv.setTextFilterEnabled(true);
	         
	         

	         
	         
	         } catch (Exception e) {
	         System.out.println("XML Pasing Exception = " + e);
	         }

	         /** Set the layout view to display */
	    }
	    
	    public void NowShowing()
	    {
	    	 LinearLayout layout = new LinearLayout(this);
	         layout.setOrientation(1);

	         /** Create a new textview array to display the results*/ 
	         TextView theatername[];
	         String theaterlist[];
	         //TextView website[];
	         TextView category[];

	         try {

	         URL url = new URL(
	         "http://www.cinemark.com.br/mobile/xml/films/");
	         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new InputSource(url.openStream()));
	         doc.getDocumentElement().normalize();

	         NodeList nodeList = doc.getElementsByTagName("film");

	         /** Assign textview array lenght by arraylist size */
	         theatername = new TextView[nodeList.getLength()];
	         //website = new TextView[nodeList.getLength()];
	         category = new TextView[nodeList.getLength()];
	         theaterlist = new String[nodeList.getLength()];
	         for (int i = 0; i < nodeList.getLength(); i++) {

	         Node node = nodeList.item(i);

	         theatername[i] = new TextView(this);
	         
	         //website[i] = new TextView(this);
	         category[i] = new TextView(this);

	         Element fstElmnt = (Element) node;
	         NodeList nameList = fstElmnt.getElementsByTagName("film");
	         Element nameElement = (Element) nameList.item(0);
	         nameList = nameElement.getChildNodes();
	         theatername[i].setText(""
	         + ((Node) nameList.item(0)).getNodeValue());
	         theaterlist[i] = theatername[i].getText().toString();
	         NodeList websiteList = fstElmnt.getElementsByTagName("website");
	         Element websiteElement = (Element) websiteList.item(0);
	         //websiteList = websiteElement.getChildNodes();
	         //website[i].setText("Website = "
	         //+ ((Node) websiteList.item(0)).getNodeValue());

	         //category[i].setText("Website Category = "
	         //+ websiteElement.getAttribute("category"));
	         
	         
	         
	         layout.addView(theatername[i]);
	         //layout.addView(website[i]);
	         //layout.addView(category[i]);
	         
	         }
	        
	         this.setListAdapter(new ArrayAdapter<String>(this,
	 				android.R.layout.simple_list_item_1, theaterlist));
	         
	         ListView lv = getListView();
	         lv.setTextFilterEnabled(true);
	         
	         

	         
	         
	         } catch (Exception e) {
	         System.out.println("XML Pasing Exception = " + e);
	         }

	         /** Set the layout view to display */
	    }
	    
	    public void WeekReleases()
	    {
	    	 LinearLayout layout = new LinearLayout(this);
	         layout.setOrientation(1);

	         /** Create a new textview array to display the results*/ 
	         TextView theatername[];
	         String theaterlist[];
	         //TextView website[];
	         TextView category[];

	         try {

	         URL url = new URL(
	         "http://www.cinemark.com.br/mobile/xml/films/");
	         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new InputSource(url.openStream()));
	         doc.getDocumentElement().normalize();

	         NodeList nodeList = doc.getElementsByTagName("film");

	         /** Assign textview array lenght by arraylist size */
	         theatername = new TextView[nodeList.getLength()];
	         //website = new TextView[nodeList.getLength()];
	         category = new TextView[nodeList.getLength()];
	         theaterlist = new String[nodeList.getLength()];
	         for (int i = 0; i < nodeList.getLength(); i++) {

	         Node node = nodeList.item(i);

	         theatername[i] = new TextView(this);
	         
	         //website[i] = new TextView(this);
	         category[i] = new TextView(this);

	         Element fstElmnt = (Element) node;
	         NodeList nameList = fstElmnt.getElementsByTagName("film");
	         Element nameElement = (Element) nameList.item(0);
	         nameList = nameElement.getChildNodes();
	         theatername[i].setText(""
	         + ((Node) nameList.item(0)).getNodeValue());
	         theaterlist[i] = theatername[i].getText().toString();
	         NodeList websiteList = fstElmnt.getElementsByTagName("website");
	         Element websiteElement = (Element) websiteList.item(0);
	         //websiteList = websiteElement.getChildNodes();
	         //website[i].setText("Website = "
	         //+ ((Node) websiteList.item(0)).getNodeValue());

	         //category[i].setText("Website Category = "
	         //+ websiteElement.getAttribute("category"));
	         
	         
	         
	         layout.addView(theatername[i]);
	         //layout.addView(website[i]);
	         //layout.addView(category[i]);
	         
	         }
	        
	         this.setListAdapter(new ArrayAdapter<String>(this,
	 				android.R.layout.simple_list_item_1, theaterlist));
	         
	         ListView lv = getListView();
	         lv.setTextFilterEnabled(true);
	         
	         

	         
	         
	         } catch (Exception e) {
	         System.out.println("XML Pasing Exception = " + e);
	         }

	         /** Set the layout view to display */
	    }
	    
	    public void UpcomingReleases()
	    {
	    	 LinearLayout layout = new LinearLayout(this);
	         layout.setOrientation(1);

	         /** Create a new textview array to display the results*/ 
	         TextView theatername[];
	         String theaterlist[];
	         //TextView website[];
	         TextView category[];

	         try {

	         URL url = new URL(
	         "http://www.cinemark.com.br/mobile/xml/upcoming/");
	         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new InputSource(url.openStream()));
	         doc.getDocumentElement().normalize();

	         NodeList nodeList = doc.getElementsByTagName("film");

	         /** Assign textview array lenght by arraylist size */
	         theatername = new TextView[nodeList.getLength()];
	         //website = new TextView[nodeList.getLength()];
	         category = new TextView[nodeList.getLength()];
	         theaterlist = new String[nodeList.getLength()];
	         for (int i = 0; i < nodeList.getLength(); i++) {

	         Node node = nodeList.item(i);

	         theatername[i] = new TextView(this);
	         
	         //website[i] = new TextView(this);
	         category[i] = new TextView(this);

	         Element fstElmnt = (Element) node;
	         NodeList nameList = fstElmnt.getElementsByTagName("original-title");
	         Element nameElement = (Element) nameList.item(0);
	         nameList = nameElement.getChildNodes();
	         theatername[i].setText(""
	         + ((Node) nameList.item(0)).getNodeValue());
	         theaterlist[i] = theatername[i].getText().toString();
	         NodeList websiteList = fstElmnt.getElementsByTagName("website");
	         Element websiteElement = (Element) websiteList.item(0);
	         //websiteList = websiteElement.getChildNodes();
	         //website[i].setText("Website = "
	         //+ ((Node) websiteList.item(0)).getNodeValue());

	         //category[i].setText("Website Category = "
	         //+ websiteElement.getAttribute("category"));
	         
	         
	         
	         layout.addView(theatername[i]);
	         //layout.addView(website[i]);
	         //layout.addView(category[i]);
	         
	         }
	        
	         this.setListAdapter(new ArrayAdapter<String>(this,
	 				android.R.layout.simple_list_item_1, theaterlist));
	         
	         ListView lv = getListView();
	         lv.setTextFilterEnabled(true);
	         
	         

	         
	         
	         } catch (Exception e) {
	         System.out.println("XML Pasing Exception = " + e);
	         }

	         /** Set the layout view to display */
	    }
}
