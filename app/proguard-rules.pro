# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ethens/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-ignorewarnings
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep public class com.google.android.gms.**
-dontwarn com.google.android.gms.**

-keepclassmembers enum * { *; }

-keep class ch.qos.** { *; }
-keep class org.slf4j.** { *; }

-dontwarn ch.qos.logback.core.net.*

-keepclassmembers class ** {
    public void onEvent*(***);
}

-keep class **.R$* {
     <fields>;
     }

-keep public class medtrakr.cricbuzz.ethens.R$drawable.**

-keepattributes SourceFile,LineNumberTable,*Annotation*

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions