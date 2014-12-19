package cl.telematica.tareamultimedios;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;

public class SingleItemView extends Activity{

	
	String title;
	String image;
	String points;
	String link;
	
	private UiLifecycleHelper uiHelper;
	
	
	ImageLoader imageLoader = new ImageLoader(this);
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		final Button share;


		Intent i = getIntent();
		// Get the result of rank
		title = i.getStringExtra("title");
		// Get the result of country
		image = i.getStringExtra("image");
		// Get the result of population
		points = i.getStringExtra("points");
		// Get the result of flag
		link = i.getStringExtra("link");
			
		// Locate the TextViews in singleitemview.xml
		TextView txttitle = (TextView) findViewById(R.id.title);
		TextView txtpoints = (TextView) findViewById(R.id.points);
		TextView txtlink = (TextView) findViewById(R.id.link);
		
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    
		// Locate the ImageView in singleitemview.xml
		ImageView imgflag = (ImageView) findViewById(R.id.image);
		
		share = (Button) findViewById(R.id.share);

		// Set results to the TextViews
		txttitle.setText(title);
		txtpoints.setText(points);
		txtlink.setText(link);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(image, imgflag);
		
		
		//if (FacebookDialog.canPresentShareDialog(getApplicationContext(), 
        //    FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			//Publish the post using the Share Dialog
		share.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v){
				
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(SingleItemView.this)
				.setLink(link)
				.build();
				uiHelper.trackPendingDialogCall(shareDialog.present());
				
			}
		});
					
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    //uiHelperLike.onActivityResult(requestCode, resultCode, data, null);
	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	
}
