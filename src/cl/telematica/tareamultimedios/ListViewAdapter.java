package cl.telematica.tareamultimedios;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;

public class ListViewAdapter extends BaseAdapter{

	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
	LikesSQLiteHelper dbInstance;
	SQLiteDatabase db;
	
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
		dbInstance = new LikesSQLiteHelper(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView title;
		TextView link;
		TextView points;
		ImageView image;
		final Button like;
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.list_view, parent, false);
		
		// Get the position
		resultp = data.get(position);
		
		title = (TextView) itemView.findViewById(R.id.title);
		link = (TextView) itemView.findViewById(R.id.link);
		points = (TextView) itemView.findViewById(R.id.points);

		// Locate the ImageView in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.image);
		
		like = (Button) itemView.findViewById(R.id.button);
		
		db = dbInstance.getWritableDatabase();
		
		if(db.rawQuery("SELECT name from Likes where id="+position, null).moveToFirst()){
			like.setText("Unlike");
		}
		
		title.setText(resultp.get(ActivityParsing.TITLE));
		link.setText(resultp.get(ActivityParsing.LINK));
		points.setText(resultp.get(ActivityParsing.POINTS));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(ActivityParsing.IMAGE), image);
		// Capture ListView item click
		
		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(db !=null){
					if(db.rawQuery("SELECT name from Likes WHERE id="+position, null).moveToFirst()){
						db.execSQL("DELETE FROM Likes WHERE id="+position);
						like.setText("Like");
					}
					else{
						db.execSQL("INSERT INTO Likes (id, name) VALUES ("+position+", 'TRUE')");
						like.setText("Unlike");
					}
				}
				
			}
			
		});
		
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("title", resultp.get(ActivityParsing.TITLE));
				// Pass all data country
				intent.putExtra("points", resultp.get(ActivityParsing.POINTS));
				// Pass all data population
				intent.putExtra("link",resultp.get(ActivityParsing.LINK));
				// Pass all data flag
				intent.putExtra("image", resultp.get(ActivityParsing.IMAGE));
				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});

		
		return itemView;
	}

}
