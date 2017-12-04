# FaceDetection
This project detects faces in the image (if present) using hadoop.

## Getting Started
This project involves 2 processes-

	- creation of sequence file.
	
	- detection of faces in the image using the sequence file.

### Prerequisites

1. Hadoop-1.2.1
	- Link to the Tar file can be found [here](http://www-us.apache.org/dist/hadoop/common/). Steps for installation can be found [here](http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster/).

2. opencv-3.3.0 
	- Follow this [link](https://advancedweb.hu/2016/03/01/opencv_ubuntu/) for installation of opencv-3.3.0.

3. Java-1.8.0_151

4. intellij Idea

### Setup

1. Go to *opencv-3.3.0 -> build -> lib* and copy all files (except .so files) to *$HADOOP_INSTALL -> lib -> native -> Linux-amd64-64* if system is 64-bit otherwise copy them to *$HADOOP_INSTALL -> lib -> native -> Linux-i386-32*. 

2. Clone this repository and open it in Intellij Idea.

3. Go to *File -> Project Structure -> Modules*. Open the *Dependencies* tab. Select the "+" button on the right and include all jar files at-
	- *$HADOOP_INSTALL*
	- *$HADOOP_INSTALL -> lib*

4. Include the opencv-3.3.0 jar file present at *opencv-3.3.0 ->build -> bin*.

5. Find the opencv-3.3.0 jar file in the dependencies list and double click it. Click on the "+" button on top-left. Go to *opencv-3.3.0 -> build -> lib* and select **libopencv_java330.so** file.

6. Click ok and apply all changes.

7. Edit the path of **libopencv_java330.so** and **haarcascade_frontalface_alt.xml** at line 24 and 25 in **ImageMapper.java**.

8. Build the project. It should build without any errors.

9. Create 2 jar files. One having **Main.java** as the main class([this](https://github.com/vgoyal1996/FaceDetection/tree/master/out/artifacts/FaceDetectionSequence_jar)) and the other having **Image.java** as the main class ([this](https://github.com/vgoyal1996/FaceDetection/tree/master/out/artifacts/FaceDetectionImage_jar)).

### Running

1. Transfer the folder containing the frames to HDFS.

	command - **bin/hadoop fs -copyFromLocal \<Absolute path of the folder in local system> <Destination folder name>**

2. Edit the input file containing absolute path of each frame in HDFS as needed and transfer the file to HDFS.

3. Run the first job using the jar file having **Main.java** as the main class and using the file with absolute paths as input.
	
	command - **bin/hadoop jar \<absolute path of jar file> <input file> <output folder name>**

4. Run the second job using the jar file having **Image.java** as the main class and input as the output file of the previous job.
