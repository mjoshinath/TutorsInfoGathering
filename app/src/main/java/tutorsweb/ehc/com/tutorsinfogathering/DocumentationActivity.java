package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class DocumentationActivity extends Activity implements View.OnClickListener {

    private TextView firstLink;
    private TextView secondLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        firstLink = (TextView) findViewById(R.id.first_link);
        secondLink = (TextView) findViewById(R.id.second_link);

        firstLink.setOnClickListener(this);
        secondLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_link:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    Uri uri=Uri.fromFile(new File(String.valueOf(getAssets().open("temp.ppt"))));
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.second_link:
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                intent1.setDataAndType(path2, "application/vnd.ms-powerpoint");
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
    }
}
