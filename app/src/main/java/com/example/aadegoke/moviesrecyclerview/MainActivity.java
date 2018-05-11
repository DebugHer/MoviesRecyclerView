package com.example.aadegoke.moviesrecyclerview;

        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.DividerItemDecoration;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.Adapter;
        import android.widget.ImageView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import static android.support.v7.widget.LinearLayoutManager.*;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private RecyclerView Rv;
    MyAdapter mAdapter;

    // URL to get contacts JSON
    private static String url = "http://api.themoviedb.org/3/movie/popular?api_key=87a901020f496977f9d6d508c5d186ec";

    ArrayList<Movies> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myChildToolbar =  findViewById(R.id.mytoolbar);

        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        datalist = new ArrayList<>();
       Rv = findViewById(R.id.rv);
        new GetData().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.search);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<Movies> newList = new ArrayList<>();
                for(Movies objPojo : datalist){

                    String name = objPojo.getTitle().toLowerCase();
                    if(name.contains(newText)){
                        newList.add(objPojo);
                    }
                    mAdapter.setFilter(newList);
                }
                return true;
            }
        });
        return true;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {

                    JSONObject jsonobj = new JSONObject(jsonStr);

                    JSONArray results = jsonobj.getJSONArray("results");
                    for (int i = 0; i <results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String title = c.getString("title");
                        String release_date = c.getString("release_date");
                        String overview = c.getString("overview");
                        String poster_path = c.getString("poster_path");

                        Movies mymovies = new Movies(title,release_date,overview,poster_path);

                        // adding contact to contact list
                        datalist.add(mymovies);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            mAdapter = new MyAdapter(datalist, getApplicationContext());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            Rv.setLayoutManager(mLayoutManager);
            Rv.setAdapter(mAdapter);






        }

    }
}



