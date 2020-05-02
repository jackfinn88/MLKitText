package com.jfinn.mlkittext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextActivity extends AppCompatActivity {
    private EditText editText;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        getSupportActionBar().setTitle("Edit Text");

        editText = findViewById(R.id.edit_text);

        // extract string
        Intent intent = getIntent();
        text = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // show text from processed image
        showExtractedText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_text_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = editText.getText().toString();
        switch (item.getItemId()) {
            case R.id.button_share:
                shareExtractedText(text);
                return true;
            case R.id.button_copy:
                copyToClipboard(text);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void shareExtractedText(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void showExtractedText(String text) {
        editText.setText(text);
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copied text", text);

        clipboard.setPrimaryClip(clip);

        showToast("Text copied to clipboard");
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
