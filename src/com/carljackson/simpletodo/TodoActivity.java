package com.carljackson.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView listViewItems;
	private final int EDIT_ITEM_REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		readItems();
		listViewItems = (ListView) findViewById(R.id.listViewItems);
		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		listViewItems.setAdapter(itemsAdapter);
		setupListViewListeners();
	}

	public void setupListViewListeners() {
		listViewItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View item, int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}
		});

		listViewItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> view, View item, int position, long id) {
				Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
				TextView textView = (TextView) item;
				intent.putExtra("value", textView.getText());
				intent.putExtra("position", position);
				startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public void addTodoItem(View view) {
		EditText editTextNewItem = (EditText) findViewById(R.id.editTextItem);
		itemsAdapter.add(editTextNewItem.getText().toString());
		editTextNewItem.setText("");
		saveItems();
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launchEditItemActivity() {
		Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
			String value = data.getExtras().getString("value");
			int position = data.getExtras().getInt("position", 0);
			items.set(position, value);
			itemsAdapter.notifyDataSetInvalidated();
			saveItems();
		}
	}
}
