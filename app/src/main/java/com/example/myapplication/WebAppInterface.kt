import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity

class WebAppInterface(private val context: Context) {

    // This method can be called from JavaScript
       var response="";
    @JavascriptInterface
    fun sendDataToAndroid(data: String) {
        println("heeeeeeeeeeeeeeeeeeloooo")
        val intent = Intent("session_message")
        intent.putExtra("session_message", "ready")
        context.sendBroadcast(intent);
        response=data;
    }
    @JavascriptInterface
    fun missingAnimations(word: String) {
        println("heeeeeeeeeeeeeeeeeeloooo")
        val intent = Intent("missingAnimations")
        intent.putExtra("missingAnimations", "ready")
        context.sendBroadcast(intent);
        response=word;
    }
}
