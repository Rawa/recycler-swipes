# Recycler Swipes

Simple library to make it easily implement swiping gestures on a recycler view by using xml.

## Demo

![horizontal](showcase/horizontal.gif)
![vertical](showcase/vertical.gif)

## Usage

    // Define swipe configuration
    val swipes = RecyclerSwipes(
        SwipeDirection.LEFT to R.layout.swipe_left_block,
        SwipeDirection.RIGHT to R.layout.swipe_right_delete
    )
    
    // Attach it to the recycler view
    swipes.attachTo(recyclerView)
    
    // Listen for changes
    swipes.setOnSwipeListener { vH: RecyclerView.ViewHolder, dir: SwipeDirection ->
        ...
    }

For complete example clone the repository and run the sample app.

Prefer to use AppCompat/MaterialComponents Views due to some elements such as `TextView` are more prone to have bugs in older versions. E.g TextView can in some cases not be rendered if using `android:singleLine=true` while AppCompatTextView will work fine.

## Installation

Add jitpack to your repositories in your project's `build.gradle`

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' } // Add this line
		}
	}
    
Add the dependencies to your app's `app/build.gradle`

    // Get version from releases
    implementation 'com.github.rawa.recycler-swipes:recyclerswipes:x.y.z'
    
    // And if you want RxBinding support
    implementation 'com.github.rawa.recycler-swipes:recyclerswipes-rxbinding:x.y.z'

