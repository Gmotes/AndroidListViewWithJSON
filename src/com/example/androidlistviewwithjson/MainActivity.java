package com.example.androidlistviewwithjson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import com.example.androidlistviewwithjson.model.Match;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	CustomListAdapter adapter;
	   
	
	ListView list;

	
	List<Match> matches = new ArrayList<Match>();
 	ListView listView;
	
	HttpAsyncTask task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		task = new HttpAsyncTask(this); 
		task.execute("fixtures.json");
		
		
		listView = (ListView) findViewById(R.id.list);
	
		final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
            handler.post(new Runnable() {
                    public void run() {
                    	
                    	task = new HttpAsyncTask();
                    	task.execute("fixtures.json");	
                    	
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(doAsynchronousTask, 40000, 40000);
		
	}
	
	
	
	
	   private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	      
		   List<Match> matches = new ArrayList<Match>();
		   Activity act;
		   
		    public HttpAsyncTask(Activity act) {
			// TODO Auto-generated constructor stub
		    	this.act = act; 
	
		    }
		    
		    public HttpAsyncTask() {
			}
		
		   
		   @Override
	        protected String doInBackground(String... urls) {
	 
	            return GET(urls[0]);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
	           
	            getJSON(result);
	            
	            
	            
	           if (adapter == null) {  
	           adapter=new CustomListAdapter(act, matches);
	    		list=(ListView)findViewById(R.id.list);
	    		list.setAdapter(adapter);
	    		
	    		list.setOnItemClickListener(new OnItemClickListener() {

	    			@Override
	    			public void onItemClick(AdapterView<?> parent, View view,
	    					int position, long id) {
	    				
	                      
	                      // ListView Clicked item value
	                      Match  match    = (Match) listView.getItemAtPosition(position);
	                         
	    				
	    		
	    				Toast.makeText(getApplicationContext(), match.getDescription(), Toast.LENGTH_SHORT).show();
	    				
	    			}
	    		});
	           }
	           else {
	        	   
	        	   adapter.clear();
	        	   adapter.addAll(matches);
	        	   
                   //notify that the model changed
                   adapter.notifyDataSetChanged();
	        	   
	           }
	           
	        
	            //  etResponse.setText(result);
	       }
	        
	        
	        private void getJSON(String result) {
	         	  
	         	  try {
	         	      
	         	     JSONObject jsonObj = new JSONObject(result);
	                  
	                  // Getting JSON Array node
	                  JSONArray contacts = jsonObj.getJSONArray("fixtures");

	                  List<String> values = new ArrayList<String>();
	                  // looping through All Contacts
	                  for (int i = 0; i < contacts.length(); i++) {
	                      JSONObject c = contacts.getJSONObject(i);
	                       
	                      String homeTeam = c.getString("homeTeam");
	                      String awayTeam = c.getString("awayTeam");
	                      String goalsHomeTeam = c.getString("goalsHomeTeam");
	                      String goalsAwayTeam = c.getString("goalsAwayTeam");
	                      String matchDescription = c.getString("description");
	                      
	                      Match m = new Match();
	                      m.setHomeTeam(homeTeam);
	                      m.setAwayTeam(awayTeam);
	                      m.setHomeGoalTeam(goalsHomeTeam);
	                      m.setAwayGoalTeam(goalsAwayTeam);
	                      m.setDescription(matchDescription);
	                      
	                      matches.add(m);
	                       
	                     
	                      
	                      
	                       }
	                  
	           
	                   
	             
	                  
	                  
	         	  } catch (JSONException e) {
	         		  
	         		Log.e("Exception", e.getMessage()); 
	         	  }
	         	  
	           }
	      	
	      	
	      	 public String GET(String url){
	      	        InputStream inputStream = null;
	      	        String result = "";
	      	        try {
	      	 
	      	            // create HttpClient
	      	            HttpClient httpclient = new DefaultHttpClient();
	      	 
	      	            // make GET request to the given URL
	      	            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
	      	 
	      	            // receive response as inputStream
	      	            inputStream = httpResponse.getEntity().getContent();
	      	 
	      	            // convert inputstream to string
	      	            if(inputStream != null)
	      	                result = convertInputStreamToString(inputStream);
	      	            else
	      	                result = "Did not work!";
	      	 
	      	        } catch (Exception e) {
	      	            Log.d("InputStream", e.getLocalizedMessage());
	      	        }
	      	 
	      	        return result;
	      	    }
	      	 
	      	 private  String convertInputStreamToString(InputStream inputStream) throws IOException{
	      	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	      	        String line = "";
	      	        String result = "";
	      	        while((line = bufferedReader.readLine()) != null)
	      	            result += line;
	      	 
	      	        inputStream.close();
	      	        return result;
	      	 
	      	    }
	    }
}
