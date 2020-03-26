# recycler-swipes

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

Prefer to use AppCompat/MaterialComponents due to some elements such as `TextView` having bugs in older versions and does not always behave correctly with `android:singleLine=true`.

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

