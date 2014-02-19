package com.carljackson.simpletodo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView listViewItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		listViewItems = (ListView) findViewById(R.id.listViewItems);
		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		listViewItems.setAdapter(itemsAdapter);
		items.add("First Item");
		items.add("Second Item");
		setupListViewListener();
	}

	public void setupListViewListener() {
		listViewItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View item, int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetInvalidated();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public void addTodoItem(View view) {
		EditText editTextNewItem = (EditText) findViewById(R.id.editTextItem);
		itemsAdapter.add(editTextNewItem.getText().toString());
		editTextNewItem.setText("");		
	}
}
