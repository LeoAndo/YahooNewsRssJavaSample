# YahooNewsRssJavaSample
RSS Reader Widget App Sample Project (Java)

# アプリ概要

YahooのIT記事(RSS feed)をホームウィジェットで閲覧するアプリ。<br>
[OS8.0+のウィジェットのショートカット配置機能も搭載。](https://developer.android.com/guide/topics/appwidgets#Pinning)<br>
リストセルタップで、外部ブラウザを起動し、記事を見に行ける。<br>

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

