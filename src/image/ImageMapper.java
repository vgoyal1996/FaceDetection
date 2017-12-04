package image;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImageMapper extends Mapper<Text, BytesWritable, Text, Text> {
    public static CascadeClassifier faceDetector = null;
    public static MatOfRect faceDetections = null;


    public void map(Text key, BytesWritable value, Context context) throws IOException,InterruptedException {
        System.load("/home/vipul/opencv-3.3.0/build/lib/libopencv_java330.so");
        faceDetector = new CascadeClassifier("/home/vipul/opencv-3.3.0/data/haarcascades/haarcascade_frontalface_alt.xml");
        //faceDetector.load("/home/vipul/opencv-3.3.0/data/haarcascades/haarcascade_frontalface_alt.xml");
        faceDetections = new MatOfRect();

        Rect rect = new Rect();
        //int i=0;
        try {
            rect = faceDetection(value.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String dateFormatted = new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
        Text rectText = new Text(rect.toString()+" * "+dateFormatted);
        //String text = String.format("Detected %s faces", String.valueOf(i));
        context.write(rectText, key);
    }


    public static Rect faceDetection(byte[] filename) throws NoSuchAlgorithmException {
        Mat image = Imgcodecs.imdecode(new MatOfByte(filename), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        //faceDetector.detectMultiScale(image, faceDetections, 1.4, 1, 0, new Size(128, 128), new Size(256, 256));
        faceDetector.detectMultiScale(image,faceDetections);
        //return faceDetections.toArray().length;
        if(faceDetections.toArray().length>0) {
            return faceDetections.toArray()[0];
        }
        return new Rect();
    }
}
