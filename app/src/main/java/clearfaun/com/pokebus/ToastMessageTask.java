package clearfaun.com.pokebus;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by spencer on 2/17/2015.
 */
class ToastMessageTask extends AsyncTask<String, String, String> {
    String toastMessage;

    @Override
    protected String doInBackground(String... params) {
        toastMessage = params[0];
        return toastMessage;
    }

    protected void OnProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    // This is executed in the context of the main GUI thread
    protected void onPostExecute(String result){
        Toast toast = Toast.makeText(MainActivity.mContext, result, Toast.LENGTH_SHORT);
        toast.show();
    }
}