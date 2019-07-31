# sweetseekbar
Modern Seekbar like ios volume controller

![alt text](https://user-images.githubusercontent.com/24703179/62169132-05494200-b330-11e9-8759-fe1a7ceeb531.gif)

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.vahidctt:sweetseekbar:v1.0'
	}
```

# USAGE in layout XML

``` xml
<org.dakik.sweetseekbar.SweetSeekbarView
    android:id="@+id/ss"
    app:radius="20"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
</org.dakik.sweetseekbar.SweetSeekbarView>

<org.dakik.sweetseekbar.HorizontalSweetSeekbarView
        android:id="@+id/ssH"
        app:radius="20"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
</org.dakik.sweetseekbar.HorizontalSweetSeekbarView>
```

# USAGE in Code
``` java
 SweetSeekbarView ss=new SweetSeekbarView(context);
        ss.setRadius(topLeft,topRight,bottomRight,bottomLeft);
        ss.setListener(new SweetSeekbarListener() {
            @Override
            public void onStart(int value) {

            }

            @Override
            public void onMove(int value) {

            }

            @Override
            public void onEnd(int value) {

            }
        });
        //You can set percentage use "setMaxValue" function.
        ss.setMaxValue(100);
        //You can set value programmatically
        ss.setValue(50);
```
