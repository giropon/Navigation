# 徒歩ナビゲーションアプリ

OpenStreetMapを使用したAndroid向け徒歩ナビゲーションアプリケーションです。

## 機能

- OpenStreetMapによる地図表示
- 現在地の表示と追跡
- 目的地の設定
- 現在地から目的地までのルート表示（OpenRouteService API使用）
- 画面中央の十字マーカーによる目的地設定位置の表示
- 目的地の再設定時に過去の目的地とルートの自動クリア

## 技術スタック

- Kotlin
- Jetpack Compose
- OpenStreetMap (osmdroid)
- Google Play Services Location
- AndroidX
- OpenRouteService API
- Retrofit2
- OkHttp3

## 必要条件

- Android Studio Hedgehog | 2023.1.1 以上
- Android SDK 23以上
- Kotlin 1.9.0以上
- Gradle 8.0以上
- OpenRouteService APIキー

## セットアップ

1. リポジトリをクローン
```bash
git clone https://github.com/giropon/Navigation.git
```

2. OpenRouteService APIキーの取得
   - https://openrouteservice.org/ にアクセス
   - アカウントを作成
   - APIキーを取得
   - `app/src/main/res/values/api_keys.xml`にAPIキーを設定
   ```xml
   <string name="openroute_api_key">YOUR_API_KEY</string>
   ```

3. Android Studioでプロジェクトを開く

4. 必要な権限の確認
アプリは以下の権限を必要とします：
- 位置情報（ACCESS_FINE_LOCATION）
- 粗い位置情報（ACCESS_COARSE_LOCATION）
- インターネット接続

5. ビルドと実行
```bash
./gradlew build
```

## 使用方法

1. アプリを起動すると、現在地が地図上に表示されます
2. 「現在地」ボタンをタップすると、現在地に戻ります
3. 地図上で目的地をタップし、「目的地設定」ボタンをタップすると、現在地から目的地までのルートが表示されます
4. 画面中央の十字マーカーが目的地の設定位置を示します
5. 新しい目的地を設定すると、過去の目的地とルートは自動的にクリアされます

## プロジェクト構造

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/navigation/
│   │   │   ├── MainActivity.kt          # メインアクティビティ
│   │   │   ├── api/
│   │   │   │   └── OpenRouteService.kt  # OpenRouteService APIクライアント
│   │   │   ├── ui/
│   │   │   │   ├── components/
│   │   │   │   │   └── MapView.kt      # 地図表示コンポーネント
│   │   │   │   └── theme/              # UIテーマ
│   │   │   └── utils/
│   │   │       ├── LocationUtils.kt    # 位置情報ユーティリティ
│   │   │       └── RouteUtils.kt       # ルーティングユーティリティ
│   │   └── res/                        # リソースファイル
│   │       ├── drawable/
│   │       │   └── crosshair.xml       # 目的地設定用十字マーカー
│   │       └── values/
│   │           └── api_keys.xml        # APIキー設定
│   └── test/                           # テストファイル
└── build.gradle.kts                    # ビルド設定
```

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。詳細は[LICENSE](LICENSE)ファイルを参照してください。

## 貢献

1. このリポジトリをフォーク
2. 新しいブランチを作成 (`git checkout -b feature/amazing-feature`)
3. 変更をコミット (`git commit -m 'Add some amazing feature'`)
4. ブランチにプッシュ (`git push origin feature/amazing-feature`)
5. プルリクエストを作成 