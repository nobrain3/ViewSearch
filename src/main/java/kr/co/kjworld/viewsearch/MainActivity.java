package kr.co.kjworld.viewsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import kr.co.kjworld.viewsearch.view.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchText = toolbar.findViewById(R.id.actionbar_search);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                searchText();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchText()
    {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        searchFragment.searchStart(mSearchText.getText().toString(), this);
    }
}
