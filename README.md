# YahooNewsRssJavaSample
RSS Reader Widget App Sample Project (Java)

# アプリ概要

YahooのIT記事(RSS feed)をホームウィジェットで閲覧するアプリ。<br>
[OS8.0+ ウィジェットのショートカット配置機能も搭載](https://developer.android.com/guide/topics/appwidgets#Pinning)<br>
リストセルタップで、外部ブラウザを起動し、本家の記事を見に行ける。<br>

# Capture Pixel 4 OS:7

| requestPinAppWidget | Home Widget |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/120823869-d4bd3980-c592-11eb-8dde-401192e8a8b1.png" width=320 /> |<img src="https://user-images.githubusercontent.com/16476224/120824484-7ba1d580-c593-11eb-8751-eec8a0bc90af.gif" width=320 /> |

# Capture Pixel 4 OS:8

| requestPinAppWidget | Home Widget |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/120824723-adb33780-c593-11eb-9a3b-33a06638c2a7.png" width=320 /> |<img src="https://user-images.githubusercontent.com/16476224/120825266-2c0fd980-c594-11eb-84fe-5364a177c951.gif" width=320 /> |

# Capture Pixel 4 OS:9

| requestPinAppWidget | Home Widget |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/120825508-6c6f5780-c594-11eb-8090-f38626e00d21.png" width=320 /> |<img src="https://user-images.githubusercontent.com/16476224/120825991-ec95bd00-c594-11eb-889b-6889963d49dd.gif" width=320 /> |

# Capture Pixel 4 OS:10

| requestPinAppWidget | Home Widget |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/120826686-aa20b000-c595-11eb-8fce-5729753ca7fb.png" width=320 /> |<img src="https://user-images.githubusercontent.com/16476224/120826947-e81dd400-c595-11eb-91e7-65db16800277.gif" width=320 /> |

# Capture Pixel 4 OS:11

| requestPinAppWidget | Home Widget |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/120827208-2c10d900-c596-11eb-9680-10f2b989eaac.png" width=320 /> |<img src="https://user-images.githubusercontent.com/16476224/120827789-c3762c00-c596-11eb-9578-b50a124f0961.gif" width=320 /> |

# 使用ライブラリ
- [RSS-Parser](https://github.com/prof18/RSS-Parser)
 - [ver3.1.4](https://github.com/prof18/RSS-Parser/releases/tag/3.1.4) 
 - XMLパースが楽。Kotlin対応もしていて嬉しい。
- Gson
  - SharedPreferencesで利用。

# 実装メモ

## 最終的に参考になったコードと記事は以下
[アプリ ウィジェットを作成する](https://developer.android.com/guide/topics/appwidgets?hl=ja)<br>
[PlayStoreで公開されてるRSSWidgetアプリのコードを解析](https://github.com/LeoAndo/YahooNewsRssJavaSample/issues/4)<br>
[OS8.0+のウィジェットのショートカット配置機能](https://github.com/sigute/WidgetsDemo)<br>


## 日本の記事で参考になるのは以下<br>
https://speakerdeck.com/ymnder/widgetkai-fa-zai-fang?slide=1<br>
https://github.com/ymnder/WidgetSample<br>

## PR
https://github.com/LeoAndo/YahooNewsRssJavaSample/pulls?q=is%3Apr+is%3Aclosed

## 参考にならなかったもの
Googleのウィジェットサンプルコードを見たがコード古いので参考にならない。<br>
https://cs.android.com/android/platform/superproject/+/master:development/samples/?hl=ja
https://cs.android.com/android/platform/superproject/+/master:development/samples/StackWidget/?hl=ja
https://cs.android.com/android/platform/superproject/+/master:development/samples/WeatherListWidget/?hl=ja
`WeatherListWidget`のサンプルだと、`ContentProvider`を使って、リストデータを扱っていて実装量が多い。<br>

## For Kotlin
近日対応予定。。

