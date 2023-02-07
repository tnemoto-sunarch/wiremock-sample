## RestTemplateを使用したファイルダウンロード機能とWireMockを使ったテスト
随所のロジックは割と適当・・・

### 見るべきところ
- jp.co.sunarch.sample.service.FileDownloader
    - 中身を確認する部分は結構適当
- jp.co.sunarch.sample.service.FileDownloaderTest
    - 中身を確認する部分は結構適当
- jp.co.sunarch.sample.service.RestServiceTest
    - test1が正常、test2はモックの影響確認のためNGとなる
- jp.co.sunarch.sample.service.RestServiceTest2
    - モックの影響確認のためNGとなる

## 免責事項
ただのサンプルなので自己責任で。
何が起きても当方は責任を持ちません。