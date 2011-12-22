package com.gucas;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SMSHelperActivity extends ListActivity {
    /** Called when the activity is first created. */
	private Button btnClear = null;
	private HistoryDB histo_db = null;
	public final static String TAG = "SMSHelperActivity";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        histo_db = new HistoryDB(this);
        Cursor cursor = histo_db.query();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,R.layout.iterm, cursor,new String[]{"_id","time","command","in_number"},
        		         new int[]{R.id.num,R.id.time,R.id.command,R.id.phone});
        setListAdapter(cursorAdapter);
        
        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				histo_db.clear();
		        Cursor new_cursor = histo_db.query();
		        SimpleCursorAdapter newListAdapter = new SimpleCursorAdapter(SMSHelperActivity.this,R.layout.iterm, new_cursor,new String[]{"_id","time","command","in_number"},new int[]{R.id.num,R.id.time,R.id.command,R.id.phone});
		        setListAdapter(newListAdapter);
		        new_cursor.close();
			}
		});
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 1, "Settings");
		menu.add(0, 1, 2, "Close");
		menu.add(0, 2, 3, "Exit");
		menu.add(0, 3, 4, "About");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()== 0){
			Intent intent = new Intent();
			intent.setClass(SMSHelperActivity.this, SettingActivity.class);
			SMSHelperActivity.this.startActivity(intent);
			finish();
		}
		if(item.getItemId()== 1){
			finish();
		}
		if(item.getItemId()== 2){
			int flag = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
			ComponentName component = new ComponentName(this, SMSReceiver.class);
			getPackageManager().setComponentEnabledSetting(component, flag, PackageManager.DONT_KILL_APP);
			Log.v(TAG, "Disable Service");
			finish();
		}
		if(item.getItemId()== 3){
			Toast.makeText(SMSHelperActivity.this, "V1.1" +
					"All rights reserved.", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		this.histo_db.close();
		finish();
	}

}