package com.example.verch;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class ResultActivity extends Activity implements OnInitListener {
	InputStream is = null;
	private int MY_DATA_CHECK_CODE = 0;
	String imageurls[];
	private TextView textView, textView2;
	XmlPullParserFactory pullParserFactory;
	private static final String DEBUG_TAG = "HttpExample";
	String query, result;
	private TextToSpeech myTTS;
	String imageurl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	String TAG_URL = "url";
	ViewFlipper flipper;
	ImageView img;
	String query2;
	boolean speaksuccess = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myTTS = new TextToSpeech(this, this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, // Code for full screen Activity
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		imageurls = new String[10];
		setContentView(R.layout.activity_result);
		Intent i = getIntent();
		query = i.getStringExtra("query");
		query2 = query;
		textView = (TextView) findViewById(R.id.mytext);
		textView2 = (TextView) findViewById(R.id.mytext2);
		try {
			startExecution();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
		

		// ig.ParseJSON(json);
	}

	public void onInit(int initStatus) {
		if (initStatus == TextToSpeech.SUCCESS) {
			myTTS.setLanguage(Locale.UK);
			speaksuccess = true;
		} else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...",
					Toast.LENGTH_LONG).show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {

			} else {
				Intent installTTSIntent = new Intent();
				installTTSIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}

	@Override
	public void onDestroy() {
		if (myTTS != null) {
			myTTS.stop();
			myTTS.shutdown();
		}
		super.onDestroy();
	}

	public void startExecution() throws InterruptedException,
			ExecutionException, JSONException, IOException {
		query = query.replaceAll(" ", "_");
		String stringUrl = "http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString="+ query;

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {

			Log.i("summaryurl", stringUrl);

			new DownloadWebpageTask().execute(stringUrl);
			textView2.setText(query2);
			//ImagesGetter im = new ImagesGetter();

			//JSONObject json = new JSONObject(im.execute(query).get());
			//ParseJSON(json);
			// flipper =(ViewFlipper)findViewById(R.id.slideshow);
			/*
			 * try { // int waited = 0; // int duration = millis[0].intValue();
			 * 
			 * for (int i = 0; i < 4; i++) { flipnext(i); // Thread.sleep(2000);
			 * // waited += 1000; }
			 * 
			 * } catch (InterruptedException e) { // do nothing }
			 */// new ImageCycle().execute();
				// textView.setText(result);
		} else {
			textView.setText("No network connection available.");
		}
	}

	public void ParseJSON(JSONObject json) throws IOException {

		try {
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray resultArray = responseObject.getJSONArray("results");
			int i;
			for (i = 0; i < resultArray.length(); i++) {
				JSONObject c = resultArray.getJSONObject(i);
				imageurls[i] = c.getString(TAG_URL);
			}
			imageurls[i] = "endofarray";

		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	//	ImageView img = (ImageView) findViewById(R.id.image);
		URL newurl = new URL(imageurls[0]); 
		Object mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
		img.setImageBitmap((Bitmap) mIcon_val);

	}
	
	

	/*
	  public void flipnext(int i) { img = new ImageView(this); // Bitmap bitmap
	  new GetMyBitmap().execute(i, img); // img.setImageBitmap(bitmap);
	  flipper.addView(img);
	  
	  }
	 */
	// BitmapFactory.decodeStream((InputStream)new
	// URL(imageurls[i]).getContent());
	// private class ImageCycle extends AsyncTask<Integer, Void, Integer> {
	/**
	  The system calls this to perform work in a worker thread and delivers it
	  the parameters given to AsyncTask.execute()
	  
	 * protected Integer doInBackground(Integer... millis) {
	 * 
	 * return 1; }
	 * 
	 * }
	 * 
	 * 
	 * /*NEW SUB CLASS
	 */
	/*
	 * void setImage(Bitmap b) { img.setImageBitmap(b); }
	 * 
	 * class GetMyBitmap extends AsyncTask<Object, Void, Bitmap> { // ImageView
	 * // img; ProgressDialog dialog;
	 * 
	 * protected Bitmap doInBackground(Object... params) { // this.img =
	 * (ImageView)params[1]; Bitmap b = null; try { b =
	 * BitmapFactory.decodeStream((InputStream) new URL( imageurls[(Integer)
	 * params[0]]).getContent()); } catch (MalformedURLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } //
	 * img.setImageBitmap(b); return b; }
	 * 
	 * @Override protected void onPreExecute() {
	 * 
	 * dialog = new ProgressDialog(ResultActivity.this);
	 * dialog.setMessage("Loading..."); dialog.setCancelable(false);
	 * dialog.show();
	 * 
	 * }
	 * 
	 * protected void onPostExecute(Bitmap image) { dialog.dismiss();
	 * setImage(image); }
	 * 
	 * }
	 */
	class DownloadWebpageTask extends AsyncTask<String, Void, String> {

		String ns = null;
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ResultActivity.this);
			dialog.setMessage("Loading...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			String x = null;
			try {
				 ImagesGetter ig = new ImagesGetter();
				// JSONObject json = ig.getJSONFromUrl(imageurl + query);

				
			//	 textView2.setText(json.toString());
				x=downloadUrl(urls[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return x;
		}

		private String downloadUrl(String myurl) throws IOException, InterruptedException {
			try {
				URL url = new URL(myurl);
				HttpURLConnection.setFollowRedirects(true);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				// Starts the query
				conn.connect();
				int response = conn.getResponseCode();

				is = conn.getInputStream();

				pullParserFactory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = pullParserFactory.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
						false);
				parser.setInput(is, null);

				result = parseXML(parser);

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}
			}
			return result;
		}

		// onPostExecute displays the results of the AsyncTask.

		// @Override

		protected void onPostExecute(String result) {

			dialog.dismiss();
			textView.setText(result);
			myTTS.setPitch((float) 1.3);
			myTTS.setSpeechRate((float) 0.8);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myTTS.speak(result, TextToSpeech.QUEUE_FLUSH, null);
		}

		
		// FINAL TEXT VIEW WIDGET DISPLAY

		private String parseXML(XmlPullParser xpp) throws XmlPullParserException, IOException {
			String summary = null;
			int eventType = xpp.getEventType();
			// Continue until the end of the document is reached.
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Check for a start tag of the results tag.
				if (eventType == XmlPullParser.START_TAG
						&& xpp.getName().equals("Result")) {
					eventType = xpp.next();
					summary = "";
					// Process each result within the result tag.
					while (!(eventType == XmlPullParser.END_TAG && xpp
							.getName().equals("Result"))) {
						// Check for the name tag within the results tag.
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("Description")) {
							// Extract the POI name.
							summary = xpp.nextText();
							// Move on to the next tag.
							// eventType = xpp.next();
							return summary;
						}
						eventType = xpp.next();
					}
					// Do something with each POI name.
				}
				// Move on to the next result tag.
				eventType = xpp.next();
			}
			return summary;

		}

	}
}
