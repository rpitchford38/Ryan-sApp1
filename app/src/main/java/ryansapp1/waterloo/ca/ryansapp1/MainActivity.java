package ryansapp1.waterloo.ca.ryansapp1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;

//My MainActivity is following a predefined template called "AppCompactActivity" by Android OS.
public class MainActivity extends AppCompatActivity
{
    LineGraphView graph;
    //float array[][] = new float[3][100];
    Button myButton;
    int counter = 0;

    private void writeToFile( /*LinkedList<Float> values1, LinkedList<Float> values2, LinkedList<Float> values3*/)
    {
        File myFile = null;
        PrintWriter myPTR = null;
        counter++;
        try{
            //Open or create "myOutput.txt" in the folders "Lecture 4 Demo"
            myFile = new File(getExternalFilesDir("Files are Located Here"), String.format("myOutput%d.csv", counter));

            myPTR = new PrintWriter(myFile);
            myPTR.println("Hello World");
        }
        catch (IOException e){
            Log.d("Lecture 4 Demo: ", "File access failed!!!");
        }
        finally{
            if(myPTR != null)
            {
                //myPTR calls myFIle.close()
                //end myPTR.flush()
                myPTR.close();
            }
            Log.d("Lecture 4 Demo: ", "File write ended");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Tell OS to run this predefined initialization sequence.
        super.onCreate(savedInstanceState);

        //My activity is focusing on working with the Activity_Main Layout.
        setContentView(R.layout.activity_main);

        LinearLayout layout = ((LinearLayout) findViewById(R.id.myDisplay2));
        graph = new LineGraphView( getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
        layout.addView(graph);
        graph.setVisibility(View.VISIBLE);

        //Please get me the actual reference of myDisplay on XML int omy Java program.
        LinearLayout rl = (LinearLayout)findViewById(R.id.myDisplay2);
        rl.setOrientation(LinearLayout.VERTICAL);
        TextView tv2 = new TextView(getApplicationContext());
        rl.addView(tv2);

        LinearLayout al = (LinearLayout)findViewById(R.id.myDisplay2);
        al.setOrientation(LinearLayout.VERTICAL);
        TextView tv3 = new TextView(getApplicationContext());
        al.addView(tv3);

        LinearLayout gl = (LinearLayout)findViewById(R.id.myDisplay2);
        gl.setOrientation(LinearLayout.VERTICAL);
        TextView tv4 = new TextView(getApplicationContext());
        gl.addView(tv4);

        //Go to OS, Sign out the reference of Sensor Event Manager.
        //Sensor Event Manager is a SINGLETON (creation) pattern.
        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor LightSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        LightSensorHandler LightHandler = new LightSensorHandler(tv2);
        sensorMgr.registerListener(LightHandler, LightSensor, sensorMgr.SENSOR_DELAY_NORMAL);

        Sensor AccelerometerSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final AccelerometerSensorHandler AccelerometerHandler = new AccelerometerSensorHandler(graph);
        sensorMgr.registerListener(AccelerometerHandler, AccelerometerSensor, sensorMgr.SENSOR_DELAY_FASTEST);
        AccelerometerHandler.createview(tv3);

        //Please get me the actual reference of myDisplay on XML int omy Java program.
        LinearLayout fl = (LinearLayout)findViewById(R.id.myDisplay2);
        fl.setOrientation(LinearLayout.VERTICAL);
        TextView tv6 = new TextView(getApplicationContext());
        fl.addView(tv6);

        Button myButton = new Button(getApplicationContext());
        myButton.setText("Create Excel CVS File");
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Execute these statements when the button is Clicked
                writeToFile();

            }

        });
        fl.addView(myButton);
    }
}

class LightSensorHandler implements SensorEventListener
{
    public final float INTENSITY_REFERENCE = 50; //final = const in C++
    private TextView mylocalTV;
    float High = 0;

    //My Handler hands two types of events from light intensity Sensor
    //1. Sensor Accuracy change during run time.
    //2. Sensor Reading change during run time.
    public LightSensorHandler(TextView myTV)
    {
        mylocalTV = myTV;
    }

    //Accuracy Change
    public void onAccuracyChanged(Sensor s, int i)
    {
        //BLANK: I don't care.
    }

    //READING Change.
    public void onSensorChanged(SensorEvent eventInfo)
    {

        float reading = eventInfo.values[0]; //Float Array, containing Light intensity Readings.

        if(reading < INTENSITY_REFERENCE)
        {
            if(reading > High)
            {
                High = reading;
            }
            //Set the message on the Display for warning.
            mylocalTV.setText("The Light Sensor Reading is:" + "\n" + reading + "\n"
                    + "The Record-High Light Sensor Reading is:" + "\n" + High);
        }
        else
        {
            if(reading > High)
            {
                High = reading;
            }
            //Set the message on the Display for A-OK.
            mylocalTV.setText("The Light Sensor Reading is:" + "\n" + reading + "\n"
                    + "The Record-High Light Sensor Reading is:" + "\n" + High);
        }
    }
}

class AccelerometerSensorHandler implements SensorEventListener
{
    LineGraphView graphy;
    float string[][] = new float[3][100];
    int counter = 0;
    private TextView mylocalTV;
    float Highx = 0;
    float Highy = 0;
    float Highz = 0;

    //My Handler hands two types of events from light intensity Sensor
    //1. Sensor Accuracy change during run time.
    //2. Sensor Reading change during run time.
    public AccelerometerSensorHandler(LineGraphView draftview)
    {
        graphy = draftview;
    }

    public void createview(TextView myTV) {
        mylocalTV = myTV;
    }

    //Accuracy Change
    public void onAccuracyChanged(Sensor s, int i)
    {
        //BLANK: I don't care.
    }

    //READING Change.
    public void onSensorChanged(SensorEvent eventInfo)
    {
        float x = eventInfo.values[0]; //Float Array, containing Accelerometer Readings.
        float y = eventInfo.values[1]; //Float Array, containing Accelerometer Readings.
        float z = eventInfo.values[2]; //Float Array, containing Accelerometer Readings.

        if (x > Highx)
        {
            Highx = x;
        }
        if(y > Highy)
        {
            Highy = y;
        }
        if(z > Highz)
        {
            Highz = z;
        }
        //Set the message on the Display for warning.
        mylocalTV.setText("The Accelerometer Sensor Reading is:" + "\n" + "(" + x + "," + y + "," + z + ")"
                + "\n" + "The Record-High Accelerometer Reading is:" + "\n" + "(" + Highx + "," + Highy + ","
                + Highz + ")" );

        if(eventInfo.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            graphy.addPoint(eventInfo.values);
        }
    }
}

