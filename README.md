# 徒歩ナビゲーションアプリ

OpenStreetMapを使用したAndroid向け徒歩ナビゲーションアプリケーションです。

## 機能

- OpenStreetMapによる地図表示
- 現在地の表示と追跡
- 目的地の設定
- 現在地から目的地までのルート表示

## 技術スタック

- Kotlin
- Jetpack Compose
- OpenStreetMap (osmdroid)
- Google Play Services Location
- AndroidX

## 必要条件

- Android Studio Hedgehog | 2023.1.1 以上
- Android SDK 23以上
- Kotlin 1.9.0以上
- Gradle 8.0以上

## セットアップ

1. リポジトリをクローン
```bash
git clone https://github.com/giropon/Navigation.git
```

2. Android Studioでプロジェクトを開く

3. 必要な権限の確認
アプリは以下の権限を必要とします：
- 位置情報（ACCESS_FINE_LOCATION）
- 粗い位置情報（ACCESS_COARSE_LOCATION）
- インターネット接続

4. ビルドと実行
```bash
./gradlew build
```

## 使用方法

1. アプリを起動すると、現在地が地図上に表示されます
2. 「現在地」ボタンをタップすると、現在地に戻ります
3. 地図上で目的地をタップし、「目的地設定」ボタンをタップすると、現在地から目的地までのルートが表示されます

## プロジェクト構造

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/navigation/
│   │   │   ├── MainActivity.kt          # メインアクティビティ
│   │   │   ├── ui/
│   │   │   │   ├── components/
│   │   │   │   │   └── MapView.kt      # 地図表示コンポーネント
│   │   │   │   └── theme/              # UIテーマ
│   │   │   └── utils/
│   │   │       └── LocationUtils.kt    # 位置情報ユーティリティ
│   │   └── res/                        # リソースファイル
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