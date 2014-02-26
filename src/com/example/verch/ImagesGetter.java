package com.example.verch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

//import com.paresh.googlesearchexample.adapters.GoogleImageBean;
//import com.paresh.googlesearchexample.adapters.ListViewImageAdapter;

public class ImagesGetter extends AsyncTask<String, Void, String>
{
    /** Called when the activity is first created. */

	//private ListView listViewImages;
//	EditText txtSearchText;

//	private ListViewImageAdapter adapter;
 //   private ArrayList<Object> listImages;
//	private Activity activity;

	String strSearch = null,result;
	StringBuilder builder;
	JSONObject json;
	ProgressDialog dialog;

	   @Override
	   protected String doInBackground(String... params) {
		   strSearch = params[0];
		   URL url;
		   try {
			   url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
			   	"v=1.0&q="+strSearch+"&rsz=8");

		   URLConnection connection = url.openConnection();

		   String line;
		   builder = new StringBuilder();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		   while((line = reader.readLine()) != null) {
			   builder.append(line);
		   }

		   

		   json = new JSONObject(builder.toString());
		   } catch (MalformedURLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   } catch (IOException e) {
			// TODO qAuto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}result=builder.toString();
		   return result;
	   }

	 
	   protected void onPostExecute(String res) {
		   // TODO Auto-generated method stub
//		   TextView textView = (TextView) findViewById(R.id.mytext);
		   super.onPostExecute(res);
		   

		   try {
			   JSONObject responseObject = json.getJSONObject("responseData");
			   JSONArray resultArray = responseObject.getJSONArray("results");

			 //  listImages = getImageList(resultArray);
	//		   SetListViewAdapter(listImages);
		   } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
   /*

   public ArrayList<Object> getImageList(JSONArray resultArray)
   {
	   ArrayList<Object> listImages = new ArrayList<Object>();
	   GoogleImageBean bean;

		try
		{
			for(int i=0; i<resultArray.length(); i++)
			{
				JSONObject obj;
				obj = resultArray.getJSONObject(i);
				bean = new GoogleImageBean();

				bean.setTitle(obj.getString("title"));
				bean.setThumbUrl(obj.getString("tbUrl"));

				System.out.println("Thumb URL => "+obj.getString("tbUrl"));

				listImages.add(bean);

			}
			return listImages;
		 }
		 catch (JSONException e)
		 {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }

		 return null;
	}

}*/





	

