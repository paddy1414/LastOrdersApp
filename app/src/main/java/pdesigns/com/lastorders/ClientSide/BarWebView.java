package pdesigns.com.lastorders.ClientSide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pdesigns.com.lastorders.R;


/**
 * The type Bar web view.
 */
public class BarWebView extends AppCompatActivity {


    /**
     * The Web url.
     */
    String WebUrl;

    private WebView webView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_barwebview);

        webView = (WebView) findViewById(R.id.webviewxml);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        Intent i = getIntent();
        WebUrl = i.getStringExtra("facebookPage");
        webView.loadUrl(WebUrl);
    }


    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_barwebview, container, false);

        WebView webView = (WebView) mainView.findViewById(R.id.weview1ToMe); // ERROR 1
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(WebUrl);

        return mainView;


    }

    */


    /**
     * The type My web view client.
     */
    public class MyWebViewClient extends WebViewClient {
        /* (non-Java doc)
         * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
         */

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".mp4")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/*");

                view.getContext().startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

    }
}
