# sweetseekbar
Modern Seekbar like ios volume controller

![alt text](https://user-images.githubusercontent.com/24703179/62212409-58131000-b3a9-11e9-9c00-d4c4e2699ea3.gif)

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
	        implementation 'com.github.vahidctt:sweetseekbar:v1.1'
	}
```

# USAGE in layout XML

``` xml
<org.dakik.sweetseekbar.SweetSeekbarView
    android:id="@+id/ss"
    android:layout_margin="20dp"
    app:frontTint="@color/colorPrimary"
    app:backTint="#ffa"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
</org.dakik.sweetseekbar.SweetSeekbarView>

<org.dakik.sweetseekbar.SweetSeekbarView
        android:id="@+id/ssH"
        app:orientation="horizontal"
        android:layout_margin="20dp"
        app:enableBounceAnim="false"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
    </org.dakik.sweetseekbar.SweetSeekbarView>
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
