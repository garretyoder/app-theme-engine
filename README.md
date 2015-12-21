# App Theme Engine (BETA)

App Theme Engine is a library that makes it easy for developers to implement a theme system in 
their apps, similar to what's seen in [Cabinet](https://play.google.com/store/apps/details?id=com.afollestad.cabinet) 
and [Impression](https://github.com/afollestad/impression).

Download the [latest sample APK](https://github.com/afollestad/app-theme-engine/raw/master/sample/Sample.apk) to check it out!

# TODO

1. Move the code over from Cabinet/Impression which tints controls inside a actionbar/toolbar's overflow menu. 

---

# Table of Contents

1. [Gradle Dependency](https://github.com/afollestad/app-theme-engine#gradle-dependency)
    1. [Repository](https://github.com/afollestad/app-theme-engine#repository)
    2. [Dependency](https://github.com/afollestad/app-theme-engine#dependency)
2. [Applying](https://github.com/afollestad/app-theme-engine#applying)
    1. [ATEActivity](https://github.com/afollestad/app-theme-engine#ateactivity)
    2. [Custom Activities and Fragments](https://github.com/afollestad/app-theme-engine#custom-activities-and-fragments)
    3. [Lists](https://github.com/afollestad/app-theme-engine#lists)
    4. [Navigation Drawers](https://github.com/afollestad/app-theme-engine#navigation-drawers)
2. [Config](https://github.com/afollestad/app-theme-engine#config)
    1. [Modifiers](https://github.com/afollestad/app-theme-engine#modifiers)
    2. [Default Configuration](https://github.com/afollestad/app-theme-engine#default-configuration)
    3. [Value Retrieval](https://github.com/afollestad/app-theme-engine#value-retrieval)
    4. [ATEStatusBarCustomizer](https://github.com/afollestad/app-theme-engine#atestatusbarcustomizer)
3. [Tags](https://github.com/afollestad/app-theme-engine#tags)
    1. [Background Colors](https://github.com/afollestad/app-theme-engine#background-colors) 
    2. [Text Colors](https://github.com/afollestad/app-theme-engine#text-colors)
    3. [Text Link Colors](https://github.com/afollestad/app-theme-engine#text-link-colors)
    3. [Tint Colors](https://github.com/afollestad/app-theme-engine#tint-colors)
4. [Pre-made Views](https://github.com/afollestad/app-theme-engine#pre-made-views)

---

# Gradle Dependency

[ ![JitPack](https://img.shields.io/github/release/afollestad/app-theme-engine.svg?label=jitpack) ](https://jitpack.io/#afollestad/app-theme-engine)
[![Build Status](https://travis-ci.org/afollestad/app-theme-engine.svg)](https://travis-ci.org/afollestad/app-theme-engine)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

### Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	compile('com.github.afollestad:app-theme-engine:0.2.1') {
		transitive = true
	}
}
```

---

# Applying

Before we go into details of how you can configure theme colors, you need to know how the theme engine is applied.

### ATEActivity

As seen in the sample project, you can have all Activities in your app extends `ATEActivity`. This will do
all the heavy lifting for you, all that you have to worry about is theme configuration.

```java
public class MyActivity extends ATEActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // setContentView() triggers the theme engine
        setContentView(R.layout.my_layout);
    }
}
```

If you were to change theme colors from a visible `ATEActivity`, the changes are reflected automatically
if you use the `apply()` methods discussed in the next two sections.

### Custom Activities and Fragments

If you don't use `ATEActivity`, there's a few things you have to do:

```java
public class MyActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ATE.preApply(this); // apply primary color to status bar and nav bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout); // call BEFORE apply()
        updateTime = System.currentTimeMillis();
        ATE.apply(this);    // apply colors to other views in the Activity
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // If values were applied/committed (from Config) since the Activity was created, recreate it now
        if (ATE.didValuesChange(this, updateTime))
            recreate();
    }
}
```

You can also apply theming to views in a Fragment:

```java
public class MyFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ATE.apply(this);
    }
}
```

### Lists

When working with lists, you have to apply the theme engine to individual views through your adapter.

For **RecyclerViews**:

```java
public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public MyAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View list = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(list);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Setup views
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            ATE.apply(itemView.getContext(), itemView);
        }
    }
}
```

For **ListViews**:

```java
public static class MyAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        ATE.apply(convertView.getContext(), convertView);
        return convertView;
    }
}
```

### Navigation Drawers

ATE will automatically adapt when your Activity has a `DrawerLayout` at its root. When `coloredStatusBar()` 
is set to true, the primary dark theme color will be applied to the `DrawerLayout` rather than directly to 
the Window status bar. Thus, the status bar will be transparent when the drawer is open, and your theme
color when it's closed. You don't have to manually do anything.

If you use `NavigationView` from the design support library, ATE will by default theme it. There are 
navigation view theming configuration methods discussed in the next section. If your drawer uses a `Fragment`
or plain `ListView`/`RecyclerView`, you have to do what's discussed in the previous section.

---

# Config

By default, Android app themes are static. They cannot be changed dynamically after an APK is built. This 
library allows you to dynamically change theme colors at runtime.

All configuration options are persisted using SharedPreferences, meaning once you set them, you don't have 
to set them again unless you want the value to be changed from what it was previously.

### Modifiers

Here are a few configuration methods that can be used:

```java
ATE.config(this) // context
    .primaryColor(color)
    .primaryColorDark(color)
    .accentColor(color)
    .statusBarColor(color) // by default, is equal to whatever primaryColorDark is set to
    .textColorPrimary(color)
    .textColorSecondary(color)
    .coloredStatusBar(true)
    .coloredActionBar(true)
    .coloredNavigationBar(false)
    .autoGeneratePrimaryDark(true)
    .navigationViewThemed(true)
    .navigationViewSelectedIcon(color)
    .navigationViewSelectedText(color)
    .navigationViewNormalIcon(color)
    .navigationViewNormalText(color)
    .apply(this); // activity, fragment, or view
```

There's also color resource and color attribute variations of the color modifiers. For an example: 
rather than using `primaryColor(int)`, you could use `primaryColorRes(int)` or `primaryColorAttr(int)` 
in order to pass a value in the format `R.color.resourceValue` or `R.attr.attributeValue`.

### Default Configuration

If you want to setup a default configuration the first time your app is run, you can use code like this:

```java
if (!ATE.config(this).isConfigured()) {
    // Setup default options
}
```

### Value Retrieval

Using the `Config` class, you can retrieve your theme values (if you need to for any reason). For an example:

```java
int primaryColor = Config.primaryColor(this);
```

### ATEStatusBarCustomizer

If you want individual Activities to have different status bar colors, e.g. in an app that extracts
colors from an image using Palette to get theme colors, you can implement `ATEStatusBarCustomizer` 
in the Activities which require it.

```java
public class MyActivity extends AppCompatActivity implements ATEStatusBarCustomizer {
    
    @ColorInt
    @Override
    public int getStatusBarColor() {
        return Color.RED; // return whatever you want here
    }
}
```

---

# Tags

If you haven't used tags before, they can be applied to views directly from your XML layouts:

```xml
<View
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:tag="tag-value-here"
    />
```

The theme engine allows you to apply theme colors to any view using tags. **You can even use multiple tags, separated by commas**:

```xml
<View
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:tag="tag-one,tag-two,tag-three"
    />
```

Here's a list of available tag values:

### Background Colors

You can change the background of any type of view.

1. `bg_primary_color` - sets the background to the primary color.
2. `bg_primary_color_dark` - sets the background to the primary dark color.
3. `bg_accent_color` - sets the background to the accent color.
4. `bg_text_primary` - sets the background to the primary text color.
5. `bg_text_secondary` - sets the background to the secondary text color.

### Text Colors

You can only change the text color of a view that extends `TextView`, which includes `Button`'s.

1. `text_primary_color` - sets the text color to the primary color.
2. `text_primary_color_dark` - sets the text color to the primary dark color.
3. `text_accent_color` - sets the text color to the accent color.
4. `text_primary` - sets the text color to the primary text color.
5. `text_secondary` - sets the text color to the secondary text color.

### Text Link Colors

This should only really be needed on `TextView'`s, it changes the color of links when TextViews are linkable.

1. `text_link_primary_color` - sets the link text color to the primary color.
2. `text_link_primary_color_dark` - sets the link text color to the primary dark color.
3. `text_link_accent_color` - sets the link text color to the accent color.
4. `text_link_primary` - sets the link text color to the primary text color.
5. `text_link_secondary` - sets the link text color to the secondary text color. 

### Tint Colors

You can tint `CheckBox`'s, `RadioButton`'s, `ProgressBar`'s, `EditText`'s, `SeekBar`'s, and `ImageView`'s. 

1. `tint_primary_color` - tints the view with the primary color.
2. `tint_primary_color_dark` - tints the view with the primary dark color.
3. `tint_accent_color` - tints the view with the accent color.
4. `tint_text_primary` - tints the view with the primary text color.
5. `tint_text_secondary` - tints the view with the secondary text color.

---

# Pre-made Views

Seven views come stock with this library:

1. `ATECheckBox` - tints itself to the accent color.
2. `ATERadioButton` - tints itself to the accent color.
3. `ATEEditText` - tints itself to the accent color
4. `ATEProgressBar` - tints itself to the accent color.
5. `ATESeekBar` - tints itself to the accent color.
6. `ATEPrimaryTextView` - sets its text color to the primary text color.
7. `ATESecondaryTextView` - sets its text color to the secondary text color.

All that they really do is set their own tag to one of the tag values in the previous section,
and then apply theming to themselves using the individual view `apply()` method.
